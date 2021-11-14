import api.VkApi;
import hooks.Hooks;
import org.testng.annotations.Test;

public class VkTest extends Hooks {

    @Test
    public void vkApiTest() {
        VkApi vkApi = new VkApi();
        vkApi.getProfileInfo()
//                .saveProfileInfo()
//                .saveUploadPhoto()
//                .checkAvaibleType()
//                .uploadDoc("Омар Хайям")
//                .renameDoc("test-doc123")
//                .deleteDocument()
                .getNewsFeed()
                .likePost(5);
    }
}
