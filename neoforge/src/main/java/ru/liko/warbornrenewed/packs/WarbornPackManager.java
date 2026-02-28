package ru.liko.warbornrenewed.packs;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.liko.warbornrenewed.platform.Services;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class WarbornPackManager {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Map<String, ArmorDef> ARMOR_DEFS = new HashMap<>();
    private static final Map<String, List<ArmorDef>> PACK_DEFS = new LinkedHashMap<>();

    public static void loadPacks() {
        ARMOR_DEFS.clear();
        PACK_DEFS.clear();

        Path gameDir = Services.PLATFORM.getGameDir();
        File packsDir = new File(gameDir.toFile(), "warbornrenewed/packs");

        if (!packsDir.exists()) {
            if (packsDir.mkdirs()) {
                System.out.println("[WarbornPacks] Created packs directory: " + packsDir.getAbsolutePath());
            } else {
                System.err.println("[WarbornPacks] Failed to create packs directory: " + packsDir.getAbsolutePath());
            }
            return;
        }

        if (!packsDir.isDirectory()) {
            return;
        }

        File[] packDirs = packsDir.listFiles(File::isDirectory);
        if (packDirs == null) return;

        for (File packDir : packDirs) {
            loadPack(packDir);
        }
    }

    private static void loadPack(File packDir) {
        String packName = packDir.getName();
        List<ArmorDef> packArmorDefs = new ArrayList<>();

        File armorDir = new File(packDir, "json/armor");
        if (!armorDir.exists() || !armorDir.isDirectory()) return;

        File[] jsonFiles = armorDir.listFiles((dir, name) -> name.endsWith(".json"));
        if (jsonFiles == null) return;

        for (File file : jsonFiles) {
            try (Reader reader = new FileReader(file)) {
                ArmorDef def = GSON.fromJson(reader, ArmorDef.class);
                if (def != null && def.getId() != null) {
                    ARMOR_DEFS.put(def.getId(), def);
                    packArmorDefs.add(def);
                }
            } catch (Exception e) {
                System.err.println("Failed to load armor def from file: " + file.getAbsolutePath());
                e.printStackTrace();
            }
        }

        if (!packArmorDefs.isEmpty()) {
            PACK_DEFS.put(packName, packArmorDefs);
            System.out.println("[WarbornPacks] Loaded pack '" + packName + "' with " + packArmorDefs.size() + " armor def(s)");
        }
    }

    public static ArmorDef getArmorDef(String id) {
        return ARMOR_DEFS.get(id);
    }

    public static Map<String, ArmorDef> getAllArmorDefs() {
        return ARMOR_DEFS;
    }

    public static Set<String> getPackNames() {
        return PACK_DEFS.keySet();
    }

    public static List<ArmorDef> getPackDefs(String packName) {
        return PACK_DEFS.getOrDefault(packName, List.of());
    }
}
