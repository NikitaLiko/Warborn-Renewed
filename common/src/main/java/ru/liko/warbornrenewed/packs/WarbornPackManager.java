package ru.liko.warbornrenewed.packs;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.liko.warbornrenewed.platform.Services;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class WarbornPackManager {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Map<String, ArmorDef> ARMOR_DEFS = new HashMap<>();

    public static void loadPacks() {
        ARMOR_DEFS.clear();

        Path gameDir = Services.PLATFORM.getGameDir();
        File packsDir = new File(gameDir.toFile(), "warbornrenewed/packs");

        if (!packsDir.exists() || !packsDir.isDirectory()) {
            return;
        }

        File[] packDirs = packsDir.listFiles(File::isDirectory);
        if (packDirs == null) return;

        for (File packDir : packDirs) {
            loadPack(packDir);
        }
    }

    private static void loadPack(File packDir) {
        File armorDir = new File(packDir, "json/armor");
        if (!armorDir.exists() || !armorDir.isDirectory()) return;

        File[] jsonFiles = armorDir.listFiles((dir, name) -> name.endsWith(".json"));
        if (jsonFiles == null) return;

        for (File file : jsonFiles) {
            try (Reader reader = new FileReader(file)) {
                ArmorDef def = GSON.fromJson(reader, ArmorDef.class);
                if (def != null && def.getId() != null) {
                    ARMOR_DEFS.put(def.getId(), def);
                }
            } catch (Exception e) {
                System.err.println("Failed to load armor def from file: " + file.getAbsolutePath());
                e.printStackTrace();
            }
        }
    }

    public static ArmorDef getArmorDef(String id) {
        return ARMOR_DEFS.get(id);
    }

    public static Map<String, ArmorDef> getAllArmorDefs() {
        return ARMOR_DEFS;
    }
}
