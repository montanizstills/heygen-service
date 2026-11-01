package io.github.montanizstills.heygenAPI.model.video;

import io.github.montanizstills.heygenAPI.json.JsonEnum;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum AspectRatio implements JsonEnum {
    Landscape_16_9("16:9"),
    Portrait_9_16("9:16"),
    Portrait_4_5("4:5"),
    Landscape_5_4("5:4"),
    Square_1_1("1:1"),
    Auto("auto");

    private final String value;

    @Override
    public String getValue() {
        return this.value;
    }
}
