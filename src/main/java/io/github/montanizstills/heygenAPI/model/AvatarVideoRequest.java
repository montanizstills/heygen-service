package io.github.montanizstills.heygenAPI.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.montanizstills.heygenAPI.interfaces.IRequest;
import io.github.montanizstills.heygenAPI.model.background.BackgroundSettings;
import io.github.montanizstills.heygenAPI.model.background.Fit;
import io.github.montanizstills.heygenAPI.model.video.*;
import io.github.montanizstills.heygenAPI.model.voice.VoiceSettings;
import lombok.*;

// POST /v3/videos, "avatar" and "image" variants. The API rejects unknown fields, so only set what applies.
// avatar: animates an avatar look (avatar_id). Replaces v2 video/generate.
// image:  lip-syncs a photo of a person (image). Replaces v2 video/av4/generate.
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AvatarVideoRequest implements IRequest {
    @Builder.Default
    @JsonProperty(value = "type", required = true)
    private VideoType type = VideoType.Avatar;

    @JsonProperty("avatar_id")
    private String avatarId; // Required for avatar type. Avatar look ID from GET /v3/avatars/looks.

    @JsonProperty("image")
    private AssetInput image; // Required for image type.

    @JsonProperty("engine")
    private Engine engine; // Avatar type only. Defaults to avatar_iv.

    // ======= Audio: script + voice_id, or audio_url, or audio_asset_id =========
    @JsonProperty("script")
    private String script;

    @JsonProperty("voice_id")
    private String voiceId; // Required with script unless the avatar has a default voice.

    @JsonProperty("audio_url")
    private String audioUrl;

    @JsonProperty("audio_asset_id")
    private String audioAssetId;

    @JsonProperty("voice_settings")
    private VoiceSettings voiceSettings; // Applies only to script (TTS) audio.

    // ======= Output =========
    @JsonProperty("title")
    private String title;

    @JsonProperty("aspect_ratio")
    private AspectRatio aspectRatio; // Defaults to 16:9.

    @JsonProperty("resolution")
    private Resolution resolution;

    @JsonProperty("fit")
    private Fit fit; // contain or cover only.

    @JsonProperty("background")
    private BackgroundSettings background;

    @JsonProperty("remove_background")
    private Boolean removeBackground; // Requires a matting-enabled video avatar.

    @JsonProperty("output_format")
    private OutputFormat outputFormat; // webm = transparent background; rejects background.

    @JsonProperty("caption")
    private Caption caption;

    @JsonProperty("motion_prompt")
    private String motionPrompt; // Photo avatars on any engine; video avatars only on avatar_v.

    @JsonProperty("expressiveness")
    private Expressiveness expressiveness; // Photo avatars on avatar_iv only.

    @JsonProperty("folder_id")
    private String folderId;

    @JsonProperty("callback_url")
    private String callbackUrl;

    @JsonProperty("callback_id")
    private String callbackId;
}
