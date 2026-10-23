package io.github.montanizstills.heygenAPI.model.video;

import io.github.montanizstills.heygenAPI.json.JsonEnum;

public enum OutputFormat implements JsonEnum {
    Mp4, Webm;

    private final String value;

    OutputFormat() {
        this.value = this.name().toLowerCase();
    }

    @Override
    public String getValue() {
        return this.value;
    }
}
