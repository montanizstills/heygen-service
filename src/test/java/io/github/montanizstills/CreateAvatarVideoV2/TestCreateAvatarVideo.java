package io.github.montanizstills.CreateAvatarVideoV2;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.montanizstills.http.SimpleHttp;
import io.github.montanizstills.http.SimpleHttpResponse;
import io.github.montanizstills.heygenAPI.HeygenEndpoints;
import io.github.montanizstills.heygenAPI.model.AssetInput;
import io.github.montanizstills.heygenAPI.model.AvatarVideoRequest;
import io.github.montanizstills.heygenAPI.model.background.BackgroundSettings;
import io.github.montanizstills.heygenAPI.model.background.BackgroundType;
import io.github.montanizstills.heygenAPI.model.video.*;
import io.github.montanizstills.heygenAPI.model.voice.ElevenLabsModel;
import io.github.montanizstills.heygenAPI.model.voice.ElevenLabsSettings;
import io.github.montanizstills.heygenAPI.model.voice.VoiceSettings;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestCreateAvatarVideo {
    private static final String AVATAR_ID = "ef809a45b38340cf84cec36fa71c7aae"; //todo - replace with lookup
    private static final String VOICE_ID = "d3d194b2d2fd44298e4428b5f9358129"; // todo - replace with lookup (run query for name return id)

    private AvatarVideoRequest avatarVideoRequest() {
        return AvatarVideoRequest.builder()
                .avatarId(AVATAR_ID)
                .engine(Engine.of(EngineType.Avatar_IV))
                .script("Hello, this is a test video, my name is not Jay. I was written 20 seconds ago by awesome Nez, who just got off of work. Grand expectations are coming.!")
                .voiceId(VOICE_ID)
                .voiceSettings(VoiceSettings.builder().speed(1.0f).pitch(0).build())
                .background(BackgroundSettings.builder().type(BackgroundType.Color).value("#FFFFFF").build())
                .aspectRatio(AspectRatio.Landscape_16_9)
                .resolution(Resolution.R_720P)
                .title("My Second Test Video from JavaAPI")
                .caption(Caption.builder().build())
                .build();
    }

    @Test
    void avatarVideoSerializesToV3Shape() throws Exception {
        JsonNode json = new ObjectMapper().readTree(avatarVideoRequest().toJson());

        assertEquals("avatar", json.get("type").asText());
        assertEquals(AVATAR_ID, json.get("avatar_id").asText());
        assertEquals("avatar_iv", json.get("engine").get("type").asText());
        assertEquals(VOICE_ID, json.get("voice_id").asText());
        assertEquals(1.0, json.get("voice_settings").get("speed").asDouble());
        assertEquals("color", json.get("background").get("type").asText());
        assertEquals("#FFFFFF", json.get("background").get("value").asText());
        assertEquals("16:9", json.get("aspect_ratio").asText());
        assertEquals("720p", json.get("resolution").asText());
        assertEquals("srt", json.get("caption").get("file_format").asText());
        // v2-only fields must not leak: v3 rejects unknown fields
        for (String v2Field : List.of("video_inputs", "dimension", "character", "voice")) {
            assertFalse(json.has(v2Field), v2Field);
        }
        assertFalse(json.get("voice_settings").has("volume"), "unset fields are omitted");
    }

    @Test
    void imageVideoReplacesAvatarIV() throws Exception {
        AvatarVideoRequest request = AvatarVideoRequest.builder()
                .type(VideoType.Image)
                .image(AssetInput.url("https://example.com/face.png"))
                .script("Hi there")
                .voiceId(VOICE_ID)
                .motionPrompt("nods while speaking")
                .expressiveness(Expressiveness.Medium)
                .voiceSettings(VoiceSettings.builder()
                        .engineSettings(ElevenLabsSettings.builder().model(ElevenLabsModel.eleven_v3).stability(0.5f).build())
                        .build())
                .build();
        JsonNode json = new ObjectMapper().readTree(request.toJson());

        assertEquals("image", json.get("type").asText());
        assertEquals("url", json.get("image").get("type").asText());
        assertEquals("https://example.com/face.png", json.get("image").get("url").asText());
        assertEquals("medium", json.get("expressiveness").asText());
        JsonNode engineSettings = json.get("voice_settings").get("engine_settings");
        assertEquals("elevenlabs", engineSettings.get("engine_type").asText());
        assertEquals("eleven_v3", engineSettings.get("model").asText());
        assertFalse(json.has("avatar_id"));
    }

    @Test
    @EnabledIfEnvironmentVariable(named = "HEYGEN_API_KEY", matches = ".+")
    void createAvatarVideoLive() throws Exception {
        SimpleHttpResponse response = SimpleHttp.POST.sendRequest(HeygenEndpoints.CREATE_VIDEO, avatarVideoRequest().toJson());
        System.out.println(response.getBody());
        assertTrue(response.isSuccessful(), response.getStatusCode() + " " + response.getBody());
        assertFalse(new ObjectMapper().readTree(response.getBody()).path("data").path("video_id").asText().isEmpty());
    }
}
