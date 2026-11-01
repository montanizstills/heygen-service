package io.github.montanizstills.mvc.controller;

import io.github.montanizstills.heygenAPI.HeygenEndpoints;
import io.github.montanizstills.http.SimpleHttp;
import io.github.montanizstills.http.SimpleHttpResponse;
import io.github.montanizstills.mvc.model.ImageGenerationDTO;
import io.github.montanizstills.mvc.service.ImageDTOService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ImageController {
    @DeleteMapping(path = "/assets/{assetId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteAsset(@PathVariable String assetId) {
        SimpleHttpResponse response = SimpleHttp.DELETE.sendRequest(
                HeygenEndpoints.DELETE_ASSET.replace("{asset_id}", assetId), null);
        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }

    @RequestMapping(path = "/create-image", method = RequestMethod.POST)
    public ResponseEntity<ImageGenerationDTO> createAImage() {
        ImageGenerationDTO imageGenerationDTO = this.imageDTOService.createImage();
        return new ResponseEntity<>(imageGenerationDTO, HttpStatus.OK);
    }


    private final ImageDTOService imageDTOService;

//    @Autowired //removed Spring4.3, only one constructor
//    public ImageController(ImageDTOService imageDTOService) {
//        this.imageDTOService = imageDTOService;
//    }
}
