package io.github.montanizstills.heygenAPI.model.photo;

import io.github.montanizstills.heygenAPI.json.JsonEnum;

public enum Orientation implements JsonEnum {
    Square("1:1"), Horizontal("16:9"), Vertical("9:16");

    private final String orientation; // v3 aspect_ratio

    Orientation(String aspectRatio){
        this.orientation = aspectRatio;
    }
    @Override
    public String getValue() {
        return this.orientation;
    }
}
