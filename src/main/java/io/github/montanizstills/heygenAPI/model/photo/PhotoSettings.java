package io.github.montanizstills.heygenAPI.model.photo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

// POST /v3/avatars with type "prompt". Replaces v2 photo_avatar/photo/generate.
// v3 has no structured age/gender/ethnicity/pose/style fields, so they are folded into the free-text prompt.
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PhotoSettings implements IPhotoSettings {
    public static final int MAX_PROMPT_LENGTH = 1000;

    @JsonProperty(value = "name", required = true)
    private String name;

    @JsonIgnore
    private Age age;

    @JsonIgnore
    private Gender gender;

    @JsonIgnore
    private Ethnicity ethnicity;

    @JsonProperty("aspect_ratio")
    private Orientation orientation;

    @JsonIgnore
    private Pose pose;

    @JsonIgnore
    private Style style;

    @JsonIgnore
    private String appearance;

    @JsonProperty("avatar_group_id")
    private String avatarGroupId; // Adds the new look to an existing avatar group.

    @JsonProperty(value = "type", required = true)
    public String getType() {
        return "prompt";
    }

    @JsonProperty(value = "prompt", required = true)
    public String getPrompt() {
        List<String> subject = new ArrayList<>();
        if (age != null && age != Age.Unspecified) subject.add(age.getValue().toLowerCase());
        if (ethnicity != null && ethnicity != Ethnicity.Unspecified) subject.add(ethnicity.getValue().replace('_', ' '));
        subject.add(gender != null && gender != Gender.Unspecified ? gender.getValue().toLowerCase() : "person");

        StringBuilder prompt = new StringBuilder("A ").append(String.join(" ", subject));
        if (pose != null) prompt.append(", ").append(pose.getValue().replace('_', ' ')).append(" shot");
        if (style != null && style != Style.Unspecified) prompt.append(", ").append(style.getValue()).append(" style");
        prompt.append('.');
        if (appearance != null && !appearance.isBlank()) prompt.append(" Appearance: ").append(appearance.trim());

        if (prompt.length() > MAX_PROMPT_LENGTH) {
            throw new IllegalArgumentException("Avatar prompt is " + prompt.length() + " characters; HeyGen allows " + MAX_PROMPT_LENGTH + ". Shorten the appearance.");
        }
        return prompt.toString();
    }
}
