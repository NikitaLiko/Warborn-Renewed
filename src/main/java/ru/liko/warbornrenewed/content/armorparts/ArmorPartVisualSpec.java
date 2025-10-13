package ru.liko.warbornrenewed.content.armorparts;

import net.minecraft.resources.ResourceLocation;

import java.util.Objects;

/**
 * Specification for visual properties of armor parts (models, textures)
 */
public final class ArmorPartVisualSpec {
    private final ResourceLocation model;
    private final ResourceLocation texture;
    private final ResourceLocation textureDown; // For NVG down state

    private ArmorPartVisualSpec(ResourceLocation model, ResourceLocation texture, ResourceLocation textureDown) {
        this.model = Objects.requireNonNull(model, "model");
        this.texture = Objects.requireNonNull(texture, "texture");
        this.textureDown = textureDown; // Can be null if not needed
    }

    public ResourceLocation model() {
        return model;
    }

    public ResourceLocation texture() {
        return texture;
    }

    public ResourceLocation textureDown() {
        return textureDown;
    }

    public boolean hasDownState() {
        return textureDown != null;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String model;
        private String texture;
        private String textureDown;

        private Builder() {
        }

        public Builder model(String model) {
            this.model = Objects.requireNonNull(model, "model");
            return this;
        }

        public Builder texture(String texture) {
            this.texture = Objects.requireNonNull(texture, "texture");
            return this;
        }

        /**
         * Set texture for "down" state (used for NVG toggle)
         */
        public Builder textureDown(String textureDown) {
            this.textureDown = textureDown;
            return this;
        }

        public ArmorPartVisualSpec build() {
            if (model == null) {
                throw new IllegalStateException("Model path is required");
            }
            if (texture == null) {
                throw new IllegalStateException("Texture path is required");
            }
            ResourceLocation modelLoc = parseResourceLocation(model);
            ResourceLocation textureLoc = parseResourceLocation(texture);
            ResourceLocation textureDownLoc = textureDown != null ? parseResourceLocation(textureDown) : null;
            return new ArmorPartVisualSpec(modelLoc, textureLoc, textureDownLoc);
        }

        @SuppressWarnings("removal")
        private ResourceLocation parseResourceLocation(String path) {
            if (path.contains(":")) {
                String[] parts = path.split(":", 2);
                String namespace = parts[0];
                String resourcePath = parts[1];
                return new ResourceLocation(namespace, resourcePath);
            }
            return new ResourceLocation(path);
        }
    }
}
