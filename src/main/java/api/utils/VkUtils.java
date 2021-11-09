package api.utils;

import api.profile_info.ProfileInfo;

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
}
