package ru.liko.warbornrenewed.packs;

import net.minecraft.SharedConstants;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.MetadataSectionSerializer;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.server.packs.resources.IoSupplier;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLPaths;
import org.jetbrains.annotations.Nullable;
import ru.liko.warbornrenewed.WarbornRenewed;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Mod.EventBusSubscriber(modid = WarbornRenewed.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class PackResourceInjector {

    @SubscribeEvent
    public static void onAddPackFinders(AddPackFindersEvent event) {
        if (event.getPackType() == PackType.CLIENT_RESOURCES) {
            Path gameDir = FMLPaths.GAMEDIR.get();
            Path packsDir = gameDir.resolve("warbornrenewed/packs");

            if (!Files.isDirectory(packsDir)) {
                return;
            }

            event.addRepositorySource(packConsumer -> {
                Pack pack = Pack.readMetaAndCreate(
                        "warborn_dynamic_models",
                        Component.literal("Warborn Dynamic Models"),
                        true,
                        id -> new WarbornDynamicPackResources(id, packsDir),
                        PackType.CLIENT_RESOURCES,
                        Pack.Position.TOP,
                        PackSource.BUILT_IN
                );

                if (pack != null) {
                    packConsumer.accept(pack);
                }
            });
        }
    }

    public static class WarbornDynamicPackResources implements PackResources {
        private final String packId;
        private final Path packsDir;

        public WarbornDynamicPackResources(String packId, Path packsDir) {
            this.packId = packId;
            this.packsDir = packsDir;
        }

        @Nullable
        @Override
        public IoSupplier<InputStream> getRootResource(String... paths) {
            return null; // We fake metadata below
        }

        @Nullable
        @Override
        public IoSupplier<InputStream> getResource(PackType type, ResourceLocation location) {
            if (type != PackType.CLIENT_RESOURCES) return null;

            String namespace = location.getNamespace();
            String path = location.getPath();

            // Intercept assets/<pack_id>/geo/... -> models/...
            if (path.startsWith("geo/")) {
                Path physicalFile = packsDir.resolve(namespace).resolve(path.replaceFirst("geo/", "models/"));
                if (Files.isRegularFile(physicalFile)) {
                    return IoSupplier.create(physicalFile);
                }
            } else if (path.startsWith("textures/")) {
                Path physicalFile = packsDir.resolve(namespace).resolve(path);
                if (Files.isRegularFile(physicalFile)) {
                    return IoSupplier.create(physicalFile);
                }
            } else if (path.startsWith("animations/")) {
                Path physicalFile = packsDir.resolve(namespace).resolve(path);
                if (Files.isRegularFile(physicalFile)) {
                    return IoSupplier.create(physicalFile);
                }
            }

            return null;
        }

        @Override
        public void listResources(PackType type, String namespace, String path, ResourceOutput resourceOutput) {
            if (type != PackType.CLIENT_RESOURCES) return;

            Path namespaceDir = packsDir.resolve(namespace);
            if (!Files.isDirectory(namespaceDir)) return;

            // Optional: Implement full resource listing if needed by GeckoLib or rendering.
            // GeckoLib typically requests models natively, so direct access works. 
            // However, it doesn't hurt to search if requested.
            Path targetDir = namespaceDir.resolve(path);
            if (path.equals("geo")) {
                targetDir = namespaceDir.resolve("models");
            }
            if (Files.isDirectory(targetDir)) {
                try (Stream<Path> files = Files.walk(targetDir)) {
                    files.filter(Files::isRegularFile).forEach(file -> {
                        String relPath = namespaceDir.relativize(file).toString().replace('\\', '/');
                        if (relPath.startsWith("models/")) {
                            relPath = "geo" + relPath.substring("models".length());
                        }
                        resourceOutput.accept(new ResourceLocation(namespace, relPath), IoSupplier.create(file));
                    });
                } catch (Exception ignored) { }
            }
        }

        @Override
        public Set<String> getNamespaces(PackType type) {
            if (type != PackType.CLIENT_RESOURCES) return Set.of();
            if (!Files.isDirectory(packsDir)) return Set.of();
            try (Stream<Path> stream = Files.list(packsDir)) {
                return stream.filter(Files::isDirectory)
                             .map(p -> p.getFileName().toString())
                             .collect(Collectors.toSet());
            } catch (Exception e) {
                return Set.of();
            }
        }

        @Nullable
        @Override
        public <T> T getMetadataSection(MetadataSectionSerializer<T> deserializer) {
            if (deserializer.getMetadataSectionName().equals("pack")) {
                return (T) new PackMetadataSection(Component.literal("Warborn Dynamic Models"), SharedConstants.getCurrentVersion().getPackVersion(PackType.CLIENT_RESOURCES));
            }
            return null;
        }

        @Override
        public String packId() {
            return this.packId;
        }

        @Override
        public boolean isBuiltin() {
            return true;
        }

        @Override
        public void close() {
        }
    }
}
