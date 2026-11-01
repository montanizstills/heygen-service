package io.github.montanizstills.heygenAPI.model.voice;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

// v3 voice_settings. Applies only to script (TTS) audio. Unset fields use the API defaults.
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VoiceSettings {
    @JsonProperty("speed")
    private Float speed; // 0.5 to 1.5, default 1

    @JsonProperty("pitch")
    private Integer pitch; // -50 to 50, default 0

    @JsonProperty("volume")
    private Float volume; // 0 to 1, default 1

    @JsonProperty("locale")
    private String locale; // e.g. en-US

    @JsonProperty("engine_settings")
    private ElevenLabsSettings engineSettings;
}
