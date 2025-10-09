package ru.liko.warbornrenewed.content.armorset;

import net.minecraft.world.item.ArmorItem;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

import javax.annotation.Nullable;

public final class ArmorBonesSpec {
    private final String head;
    private final String body;
    private final String rightArm;
    private final String leftArm;
    private final String rightLeg;
    private final String leftLeg;
    private final String rightBoot;
    private final String leftBoot;

    private ArmorBonesSpec(Builder builder) {
        this.head = builder.head;
        this.body = builder.body;
        this.rightArm = builder.rightArm;
        this.leftArm = builder.leftArm;
        this.rightLeg = builder.rightLeg;
        this.leftLeg = builder.leftLeg;
        this.rightBoot = builder.rightBoot;
        this.leftBoot = builder.leftBoot;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static ArmorBonesSpec defaults(ArmorItem.Type type) {
        return switch (type) {
            case HELMET -> builder().head("armorHead").build();
            case CHESTPLATE -> builder().body("armorBody").rightArm("armorRightArm").leftArm("armorLeftArm").build();
            case LEGGINGS -> builder().body("armorBody").rightLeg("armorRightLeg").leftLeg("armorLeftLeg").build();
            case BOOTS -> builder().rightBoot("armorRightBoot").leftBoot("armorLeftBoot").build();
        };
    }

    public void apply(GeoArmorRenderer<?> renderer) {
        renderer.head = create(head);
        renderer.body = create(body);
        renderer.rightArm = create(rightArm);
        renderer.leftArm = create(leftArm);
        renderer.rightLeg = create(rightLeg);
        renderer.leftLeg = create(leftLeg);
        renderer.rightBoot = create(rightBoot);
        renderer.leftBoot = create(leftBoot);
    }

    private static GeoBone create(@Nullable String boneName) {
        if (boneName == null || boneName.isBlank()) {
            return null;
        }
        return new GeoBone(null, boneName, false, 0.0D, false, false);
    }

    public static final class Builder {
        private String head;
        private String body;
        private String rightArm;
        private String leftArm;
        private String rightLeg;
        private String leftLeg;
        private String rightBoot;
        private String leftBoot;

        private Builder() {
        }

        public Builder head(@Nullable String head) {
            this.head = head;
            return this;
        }

        public Builder body(@Nullable String body) {
            this.body = body;
            return this;
        }

        public Builder rightArm(@Nullable String rightArm) {
            this.rightArm = rightArm;
            return this;
        }

        public Builder leftArm(@Nullable String leftArm) {
            this.leftArm = leftArm;
            return this;
        }

        public Builder rightLeg(@Nullable String rightLeg) {
            this.rightLeg = rightLeg;
            return this;
        }

        public Builder leftLeg(@Nullable String leftLeg) {
            this.leftLeg = leftLeg;
            return this;
        }

        public Builder rightBoot(@Nullable String rightBoot) {
            this.rightBoot = rightBoot;
            return this;
        }

        public Builder leftBoot(@Nullable String leftBoot) {
            this.leftBoot = leftBoot;
            return this;
        }

        public ArmorBonesSpec build() {
            return new ArmorBonesSpec(this);
        }
    }
}
