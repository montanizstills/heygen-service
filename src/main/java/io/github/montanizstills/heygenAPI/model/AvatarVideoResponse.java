package io.github.montanizstills.heygenAPI.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.montanizstills.heygenAPI.interfaces.IResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// The "data" object of POST /v3/videos. Poll GET /v3/videos/{video_id} until status is completed or failed.
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AvatarVideoResponse implements IResponse {
    @JsonProperty("video_id")
    private String videoId;

    @JsonProperty("status")
    private String status;

    @JsonProperty("output_format")
    private String outputFormat;
}
