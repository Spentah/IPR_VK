import api.VkApi;
import hooks.Hooks;
import org.testng.annotations.Test;

public class VkTest extends Hooks {

    @Test
    public void vkApiTest() {
        VkApi vkApi = new VkApi();
        vkApi
//                .getProfileInfo()
//                .saveProfileInfo()
//                .saveUploadPhoto()
//                .checkAvaibleType()
//                .uploadDoc("Омар Хайям")
//                .renameDoc("test-doc123")
//                .deleteDocument()
//                .getNewsFeed()
//                .likePost(5)
//                .banAccount(6)
//                .addToFav(2)
//                .createGroup("апи тест и че")
//                .createTopic("новый топик", "Test api")
//                .fixTopic()
//                .createComment("лол")
//                .createComment("кек")
//                .createComment("чебурек")
//                .editComment(2, "или не лол")
//                .deleteComment(1)
//                .leaveGroup()
                .createAlbum("java")
                .uploadPhoto("src/main/resources/fish.jpg");
    }
}
