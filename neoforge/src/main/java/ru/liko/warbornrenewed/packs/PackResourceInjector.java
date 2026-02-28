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
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforge.event.AddPackFindersEvent;
import org.jetbrains.annotations.Nullable;
import ru.liko.warbornrenewed.Warbornrenewed;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@EventBusSubscriber(modid = Warbornrenewed.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class PackResourceInjector {

    @SubscribeEvent
    public static void onAddPackFinders(AddPackFindersEvent event) {
        if (event.getPackType() == PackType.CLIENT_RESOURCES) {
            Path gameDir = FMLPaths.GAMEDIR.get();
            Path packsDir = gameDir.resolve("warbornrenewed/packs");

            if (!Files.isDirectory(packsDir)) {
                try {
                    Files.createDirectories(packsDir);
                } catch (Exception e) {
                    System.err.println("[WarbornPacks] Failed to create packs directory: " + packsDir);
                    return;
                }
            }

            event.addRepositorySource(packConsumer -> {
                // Testing 1.21 signature
                Pack pack = Pack.readMetaAndCreate(
                        new net.minecraft.server.packs.PackLocationInfo("warborn_dynamic_models", Component.literal("Warborn Dynamic Models"), PackSource.BUILT_IN, Optional.empty()),
                        new Pack.ResourcesSupplier() {
                            @Override
                            public PackResources openPrimary(net.minecraft.server.packs.PackLocationInfo location) {
                                return new WarbornDynamicPackResources(location.id(), packsDir);
                            }
                            @Override
                            public PackResources openFull(net.minecraft.server.packs.PackLocationInfo location, Pack.Metadata metadata) {
                                return new WarbornDynamicPackResources(location.id(), packsDir);
                            }
                        },
                        PackType.CLIENT_RESOURCES,
                        new net.minecraft.server.packs.PackSelectionConfig(true, Pack.Position.TOP, false)
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
            return null;
        }

        @Nullable
        @Override
        public IoSupplier<InputStream> getResource(PackType type, ResourceLocation location) {
            if (type != PackType.CLIENT_RESOURCES) return null;

            String namespace = location.getNamespace();
            String path = location.getPath();

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
                        resourceOutput.accept(ResourceLocation.fromNamespaceAndPath(namespace, relPath), IoSupplier.create(file));
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
                return (T) new PackMetadataSection(Component.literal("Warborn Dynamic Models"), SharedConstants.getCurrentVersion().getPackVersion(PackType.CLIENT_RESOURCES), Optional.empty());
            }
            return null;
        }

        @Override
        public net.minecraft.server.packs.PackLocationInfo location() {
            return new net.minecraft.server.packs.PackLocationInfo(this.packId, Component.literal(""), PackSource.BUILT_IN, Optional.empty());
        }

        @Override
        public void close() {
        }
    }
}
