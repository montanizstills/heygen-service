package io.github.montanizstills.heygenAPI.model.voice;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

// v3 voice_settings.engine_settings with engine_type "elevenlabs".
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ElevenLabsSettings {
    @Builder.Default
    @JsonProperty(value = "engine_type", required = true)
    private String engineType = "elevenlabs";

    @JsonProperty("model")
    private ElevenLabsModel model;

    @JsonProperty("similarity_boost")
    private Float similarityBoost; // 0.0 to 1.0

    @JsonProperty("stability")
    private Float stability; // 0.0 to 1.0

    @JsonProperty("style")
    private Float style; // 0.0 to 1.0

    @JsonProperty("use_speaker_boost")
    private Boolean useSpeakerBoost;
}
