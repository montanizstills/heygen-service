package io.github.montanizstills.heygenAPI.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

// Shared v3 asset input: {type: url|asset_id|base64, ...}
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AssetInput {
    @JsonProperty(value = "type", required = true)
    private String type;

    @JsonProperty("url")
    private String url; // public HTTPS URL

    @JsonProperty("asset_id")
    private String assetId;

    @JsonProperty("media_type")
    private String mediaType; // base64 only, e.g. image/png

    @JsonProperty("data")
    private String data; // base64 only

    public static AssetInput url(String url) {
        return AssetInput.builder().type("url").url(url).build();
    }

    public static AssetInput assetId(String assetId) {
        return AssetInput.builder().type("asset_id").assetId(assetId).build();
    }

    public static AssetInput base64(String mediaType, String data) {
        return AssetInput.builder().type("base64").mediaType(mediaType).data(data).build();
    }
}
