package io.github.montanizstills.heygenAPI.model.background;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

// v3 background for avatar/image videos.
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BackgroundSettings {
    @JsonProperty(value = "type", required = true)
    private BackgroundType type;

    @JsonProperty("value")
    private String value; // Color type only, hex format (e.g. #FFFFFF).

    @JsonProperty("url")
    private String url; // Image type only. Mutually exclusive with asset_id.

    @JsonProperty("asset_id")
    private String assetId; // Image type only. Mutually exclusive with url.
}
