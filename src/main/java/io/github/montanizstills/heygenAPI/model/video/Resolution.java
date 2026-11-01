package io.github.montanizstills.heygenAPI.model.video;

import io.github.montanizstills.heygenAPI.json.JsonEnum;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Resolution implements JsonEnum {
    R_4K("4k"), // Avatar III digital twins and studio avatars only
    R_1080P("1080p"),
    R_720P("720p");

    private final String value;

    @Override
    public String getValue() {
        return this.value;
    }
}
