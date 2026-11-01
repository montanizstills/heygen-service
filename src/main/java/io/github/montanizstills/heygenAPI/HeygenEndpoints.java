package io.github.montanizstills.heygenAPI;

// HeyGen v3 API. v1/v2 were retired on 2026-11-01.
// Mapping: https://developers.heygen.com/endpoint-version-comparison.md
public class HeygenEndpoints {
    private final static String BASE = "https://api.heygen.com/v3";

    public final static String USER_DATA = BASE + "/users/me";

    public final static String LIST_AVATAR_LOOKS = BASE + "/avatars/looks";
    public final static String GET_AVATAR_LOOK = BASE + "/avatars/looks/{look_id}";
    public final static String CREATE_AVATAR = BASE + "/avatars"; // replaces v2 photo_avatar/photo/generate (type "prompt")

    public final static String CREATE_VIDEO = BASE + "/videos"; // replaces v2 video/generate and v2 video/av4/generate
    public final static String GET_VIDEO = BASE + "/videos/{video_id}";
    public final static String DELETE_VIDEO = BASE + "/videos/{video_id}";

    public final static String DELETE_ASSET = BASE + "/assets/{asset_id}";

    public final static String CREATE_VIDEO_FROM_TEMPLATE = BASE + "/templates/{template_id}";

    public final static String LIST_VOICES = BASE + "/voices";
}
