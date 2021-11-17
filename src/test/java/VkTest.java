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
//                .likePost(5)
//                .banAccount(6)
//                .addToFav(2)
                .createGroup("API")
                .createTopic("New topic");
    }
}
