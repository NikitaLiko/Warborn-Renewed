package ru.liko.warbornrenewed.content.armorset;

import net.minecraft.resources.ResourceLocation;
import ru.liko.warbornrenewed.Warbornrenewed;

import javax.annotation.Nullable;
import java.util.Objects;

public record ArmorVisualSpec(ResourceLocation model, ResourceLocation texture, @Nullable ResourceLocation animation) {
    public ArmorVisualSpec {
        Objects.requireNonNull(model, "model");
        Objects.requireNonNull(texture, "texture");
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private ResourceLocation model;
        private ResourceLocation texture;
        private ResourceLocation animation;

        private Builder() {
        }

        public Builder model(ResourceLocation model) {
            this.model = Objects.requireNonNull(model, "model");
            return this;
        }

        public Builder model(String path) {
            return model(resolve(path));
        }

        public Builder texture(ResourceLocation texture) {
            this.texture = Objects.requireNonNull(texture, "texture");
            return this;
        }

        public Builder texture(String path) {
            return texture(resolve(path));
        }

        public Builder animation(@Nullable ResourceLocation animation) {
            this.animation = animation;
            return this;
        }

        public Builder animation(String path) {
            return animation(resolve(path));
        }

        public ArmorVisualSpec build() {
            Objects.requireNonNull(model, "model");
            Objects.requireNonNull(texture, "texture");
            return new ArmorVisualSpec(model, texture, animation);
        }

        private static ResourceLocation resolve(String path) {
            return path.contains(":") ? new ResourceLocation(path) : Warbornrenewed.id(path);
        }
    }
}
