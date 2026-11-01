package io.github.montanizstills.heygenAPI.model.background;

import io.github.montanizstills.heygenAPI.json.JsonEnum;

public enum BackgroundType implements JsonEnum {
    Color, Image; // video backgrounds exist only on studio scenes
    private String backgroundType;

    BackgroundType() {
        this.backgroundType = this.name().toLowerCase();
    }

    @Override
    public String getValue() {
        return this.backgroundType;
    }

    @Override
    public String toJson() {
        return JsonEnum.super.toJson();
    }
}
