package io.github.montanizstills.heygenAPI.model.video;

import io.github.montanizstills.heygenAPI.json.JsonEnum;

public enum EngineType implements JsonEnum {
    Avatar_IV, Avatar_III, Avatar_V;

    private final String value;

    EngineType() {
        this.value = this.name().toLowerCase();
    }

    @Override
    public String getValue() {
        return this.value;
    }
}
