package io.github.montanizstills.heygenAPI.model.video;

import io.github.montanizstills.heygenAPI.json.JsonEnum;

public enum VideoType implements JsonEnum {
    Avatar, Image;

    private final String value;

    VideoType() {
        this.value = this.name().toLowerCase();
    }

    @Override
    public String getValue() {
        return this.value;
    }
}
