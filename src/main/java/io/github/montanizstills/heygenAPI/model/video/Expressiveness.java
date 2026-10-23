package io.github.montanizstills.heygenAPI.model.video;

import io.github.montanizstills.heygenAPI.json.JsonEnum;

public enum Expressiveness implements JsonEnum {
    High, Medium, Low;

    private final String value;

    Expressiveness() {
        this.value = this.name().toLowerCase();
    }

    @Override
    public String getValue() {
        return this.value;
    }
}
