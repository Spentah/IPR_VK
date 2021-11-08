import api.VkProfile;
import hooks.Hooks;
import org.testng.annotations.Test;

public class VkTest extends Hooks {

    @Test
    public void vkApiTest() {
        VkProfile vkProfile = new VkProfile();
        vkProfile.getProfileInfo();
        vkProfile.setProfileInfo("status", "api test");
    }
}
