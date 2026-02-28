package ru.liko.warbornrenewed.packs;

import com.google.gson.annotations.SerializedName;

public class ArmorDef {

    @SerializedName("id")
    private String id;

    @SerializedName("type")
    private String type = "helmet";

    @SerializedName("name")
    private String name;

    @SerializedName("names")
    private java.util.Map<String, String> names;

    @SerializedName("model_id")
    private String modelId;

    @SerializedName("defense")
    private int defense = 0;

    @SerializedName("toughness")
    private float toughness = 0.0f;

    @SerializedName("knockback_resistance")
    private float knockbackResistance = 0.0f;

    @SerializedName("durability")
    private int durability = 0;
    
    public ArmorDef() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public java.util.Map<String, String> getNames() {
        return names;
    }

    public void setNames(java.util.Map<String, String> names) {
        this.names = names;
    }

    public String getDisplayName(String locale) {
        if (names != null && locale != null) {
            String localized = names.get(locale.toLowerCase());
            if (localized != null && !localized.isEmpty()) return localized;
        }
        if (name != null && !name.isEmpty()) return name;
        return id;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public float getToughness() {
        return toughness;
    }

    public void setToughness(float toughness) {
        this.toughness = toughness;
    }

    public float getKnockbackResistance() {
        return knockbackResistance;
    }

    public void setKnockbackResistance(float knockbackResistance) {
        this.knockbackResistance = knockbackResistance;
    }

    public int getDurability() {
        return durability;
    }

    public void setDurability(int durability) {
        this.durability = durability;
    }
}
