package io.github.montanizstills.heygenAPI.model.video;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Engine {
    @JsonProperty(value = "type", required = true)
    private EngineType type;

    @JsonProperty("reference_look_id")
    private String referenceLookId; // Applies only to avatar_v. Must be a digital_twin in the same avatar group.

    public static Engine of(EngineType type) {
        return Engine.builder().type(type).build();
    }
}
