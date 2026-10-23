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
public class Caption {
    @Builder.Default
    @JsonProperty("file_format")
    private String fileFormat = "srt"; // srt is the only supported format

    @JsonProperty("style")
    private String style; // "default" burns captions into the video; null returns only the subtitle_url sidecar
}
