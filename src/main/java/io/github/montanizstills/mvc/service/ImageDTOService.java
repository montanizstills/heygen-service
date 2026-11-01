package io.github.montanizstills.mvc.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.montanizstills.heygenAPI.HeygenEndpoints;
import io.github.montanizstills.heygenAPI.model.photo.*;
import io.github.montanizstills.http.SimpleHttp;
import io.github.montanizstills.http.SimpleHttpResponse;
import io.github.montanizstills.mvc.model.ImageGenerationDTO;
import io.github.montanizstills.mvc.repository.ImageDTORepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageDTOService {
    private final ImageDTORepository imageDTORepository;

    @Autowired
    public ImageDTOService(ImageDTORepository imageDTORepository) {
        this.imageDTORepository = imageDTORepository;
    }

    public ImageGenerationDTO createImage() {
        SimpleHttpResponse response = SimpleHttp.POST.sendRequest(
                HeygenEndpoints.CREATE_AVATAR,
                PhotoSettings.builder()
                        .name("Simple-Test-Photo_Java_API")
                        .age(Age.Senior)
                        .gender(Gender.Woman)
                        .ethnicity(Ethnicity.Hispanic)
                        .orientation(Orientation.Vertical)
                        .pose(Pose.Half_Body)
                        .style(Style.CyberPunk)
                        .appearance("dull colors; wool sweater; red dress shoes; bright lighting; standing in front of a window; clear sky background;")
                        .build()
                        .toJson()
        );
        System.out.println("\nResponse Body: " + response.getBody() + "\n");
        JsonNode root;
        try {
            root = new ObjectMapper().readTree(response.getBody());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        ImageGenerationDTO.ImageGenerationDTOBuilder imageDTO = ImageGenerationDTO.builder();
        if (response.isSuccessful()) {
            JsonNode data = root.path("data");
            imageDTO.avatarId(textOrNull(data.path("avatar_item").path("id")))
                    .avatarGroupId(textOrNull(data.path("avatar_group").path("id")));
        } else {
            imageDTO.error(response.getStatusCode() + " " + root.path("error").path("code").asText() + ": " + root.path("error").path("message").asText());
        }
        return this.imageDTORepository.save(imageDTO.build());
    }

    private static String textOrNull(JsonNode node) {
        return node.isMissingNode() || node.isNull() ? null : node.asText();
    }
}
