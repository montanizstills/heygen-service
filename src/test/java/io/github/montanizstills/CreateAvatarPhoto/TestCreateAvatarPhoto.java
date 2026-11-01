package io.github.montanizstills.CreateAvatarPhoto;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.montanizstills.http.SimpleHttp;
import io.github.montanizstills.http.SimpleHttpResponse;
import io.github.montanizstills.heygenAPI.HeygenEndpoints;
import io.github.montanizstills.heygenAPI.model.photo.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestCreateAvatarPhoto {
    private PhotoSettings photo() {
        return PhotoSettings.builder()
                .name("Simple-Test-Photo")
                .age(Age.Unspecified)
                .gender(Gender.Man)
                .ethnicity(Ethnicity.White)
                .orientation(Orientation.Horizontal)
                .pose(Pose.Full_Body)
                .style(Style.Vintage)
                .appearance("dull colors; wool sweater; black dress shoes; bright lighting; standing in front of a window; clear sky background;")
                .build();
    }

    @Test
    void photoAvatarSerializesToV3PromptAvatar() throws Exception {
        String request = photo().toJson();
        System.out.println(request);
        JsonNode json = new ObjectMapper().readTree(request);

        assertEquals("prompt", json.get("type").asText());
        assertEquals("Simple-Test-Photo", json.get("name").asText());
        assertEquals("16:9", json.get("aspect_ratio").asText());
        assertEquals("A White man, full body shot, Vintage style. Appearance: dull colors; wool sweater; black dress shoes; bright lighting; standing in front of a window; clear sky background;",
                json.get("prompt").asText());
        // v2 attribute fields must not leak: v3 rejects unknown fields
        for (String v2Field : List.of("age", "gender", "ethnicity", "orientation", "pose", "style", "appearance")) {
            assertFalse(json.has(v2Field), v2Field);
        }
    }

    @Test
    void multiWordEnumsReadNaturally() {
        String prompt = PhotoSettings.builder().name("n")
                .age(Age.Early_Middle_Age).ethnicity(Ethnicity.South_East_Asian).gender(Gender.Unspecified)
                .pose(Pose.Close_Up)
                .build().getPrompt();
        assertEquals("A early middle age South East Asian person, close up shot.", prompt);
    }

    @Test
    void promptOverLimitIsRejected() {
        PhotoSettings tooLong = PhotoSettings.builder().name("n").appearance("x".repeat(PhotoSettings.MAX_PROMPT_LENGTH)).build();
        assertThrows(IllegalArgumentException.class, tooLong::getPrompt);
    }

    @Test
    @EnabledIfEnvironmentVariable(named = "HEYGEN_API_KEY", matches = ".+")
    void createAvatarPhotoLive() {
        SimpleHttpResponse response = SimpleHttp.POST.sendRequest(HeygenEndpoints.CREATE_AVATAR, photo().toJson());
        System.out.println(response.getBody());
        assertTrue(response.isSuccessful(), response.getStatusCode() + " " + response.getBody());
    }
}
