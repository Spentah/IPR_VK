package api.vk;

import api.endpoints.EndPoints;
import api.utils.RequestSpecUtil;
import api.utils.VkUtils;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;

import java.io.File;
import java.util.Map;

import static api.utils.VkUtils.getCurrentOwnerId;
import static io.restassured.RestAssured.given;

public class VkPhoto {

    private int uploadedPhotoId;
    private final String ALBUM_ID = "album_id";
    private final String PHOTO_ID = "photo_id";

    public Integer createAlbum(String title, String privacy) {
        Response response = given().spec(RequestSpecUtil.getSpecification()).log().all()
                .param("title", title)
                .param("privacy_view", privacy)
                .get(EndPoints.CREATE_ALBUM.endPoint);
        response.then().statusCode(200);
        return VkUtils.getId(response);
    }

    public String getUploadServer(int albumId) {
        Response response = given().spec(RequestSpecUtil.getSpecification()).log().all()
                .param(ALBUM_ID, albumId)
                .get(EndPoints.PHOTO_GET_UPLOAD_SERVER.endPoint);
        response.then().statusCode(200);
        return JsonPath.from(response.asString()).getString("response.upload_url");
    }

    public Map<String, Object> uploadPhoto(String filePath, int albumId) {
        Response response = given().spec(RequestSpecUtil.getSpecification()).log().all()
                .contentType("multipart/form-data")
                .multiPart(new File(filePath))
                .post(getUploadServer(albumId));
        response.then().statusCode(200);
        return JsonPath.from(response.asString()).getMap("$");
    }

    public void saveUploadedPhoto(String filePath, int albumId) {
        Response response = given().spec(RequestSpecUtil.getSpecification()).log().all()
                .params(uploadPhoto(filePath, albumId))
                .param(ALBUM_ID, albumId)
                .get(EndPoints.SAVE_PHOTO.endPoint);
        response.then().statusCode(200);
        uploadedPhotoId = JsonPath.from(response.asString()).getInt("response[0].id");
    }

    public void makeCover(int albumId) {
        Response response = given().spec(RequestSpecUtil.getSpecification()).log().all()
                .param("owner_id", getCurrentOwnerId())
                .param(PHOTO_ID, uploadedPhotoId)
                .param(ALBUM_ID, albumId)
                .get(EndPoints.MAKE_COVER_PHOTO.endPoint);
        response.then().statusCode(200);
        Assert.assertEquals(JsonPath.from(response.asString()).getInt("response."), 1, "Судя по ответу, фотография не сделана обложкой альбома");
    }

    public void createComment(String text) {
        Response response = given().spec(RequestSpecUtil.getSpecification()).log().all()
                .param("owner_id", getCurrentOwnerId())
                .param(PHOTO_ID, uploadedPhotoId)
                .param("message", text)
                .get(EndPoints.CREATE_COMMENT_ON_PHOTO.endPoint);
        response.then().statusCode(200);
    }

    public void putTag(int userId) {
        Response response = given().spec(RequestSpecUtil.getSpecification()).log().all()
                .param("owner_id", getCurrentOwnerId())
                .param(PHOTO_ID, uploadedPhotoId)
                .param("user_id", userId)
                .param("x", 30)
                .param("y", 30)
                .param("x2", 50)
                .param("y2", 50)
                .get(EndPoints.PUT_TAG.endPoint);
        response.then().statusCode(200);
    }

    public void movePhoto(int targetAlbumId) {
        Response response = given().spec(RequestSpecUtil.getSpecification()).log().all()
                .param("owner_id", getCurrentOwnerId())
                .param("target_album_id", targetAlbumId)
                .param(PHOTO_ID, uploadedPhotoId)
                .get(EndPoints.MOVE_PHOTO.endPoint);
        response.then().statusCode(200);
        Assert.assertEquals(JsonPath.from(response.asString()).getInt("response."), 1, "Судя по ответу, фотография не перенеслась в другой альбом");
    }

    public void deleteAlbum(int albumId) {
        Response response = given().spec(RequestSpecUtil.getSpecification()).log().all()
                .param(ALBUM_ID, albumId)
                .get(EndPoints.DELETE_ALBUM.endPoint);
        response.then().statusCode(200);
        Assert.assertEquals(JsonPath.from(response.asString()).getInt("response."), 1, "Судя по ответу, альбом не удалился");
    }

    public void deletePhoto() {
        Response response = given().spec(RequestSpecUtil.getSpecification()).log().all()
                .param("owner_id", getCurrentOwnerId())
                .param(PHOTO_ID, uploadedPhotoId)
                .get(EndPoints.DELETE_PHOTO.endPoint);
        response.then().statusCode(200);
        Assert.assertEquals(JsonPath.from(response.asString()).getInt("response."), 1, "Судя по ответу, фотография не удалилась");
    }

}
