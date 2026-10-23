package io.github.montanizstills.mvc.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
//@Table(name = "ImageGeneration")
public class ImageGenerationDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "uuid2")
    private Long id;
    private String avatarId; // look ID; pass as avatar_id to POST /v3/videos
    private String avatarGroupId;
    private String error;

}
