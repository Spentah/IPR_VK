package api.utils;

import api.profile_info.ProfileInfo;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class VkUtils {

    private static ProfileInfo profileInfo;

    private VkUtils() {

    }

    public static void setCurrentProfile(ProfileInfo profileInfo) {
        VkUtils.profileInfo = profileInfo;
    }

    public static int getCurrentOwnerId() {
        return profileInfo.getResponse().getId();
    }

    public static Integer getId(Response response) {
        return JsonPath.from(response.asString()).getInt("response.id");
    }
}
