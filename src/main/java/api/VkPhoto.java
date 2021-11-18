package api;

import api.utils.RequestSpecUtil;
import api.utils.VkUtils;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.io.File;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class VkPhoto {

    private Response albumResponse;

    public void createAlbum(String title) {
        albumResponse = given().spec(RequestSpecUtil.getSpecification()).log().all()
                .param("title", title)
                .param("privacy_view", "nobody")
                .get(EndPoints.CREATE_ALBUM.endPoint);
        albumResponse.then().statusCode(200);
    }

    public String getUploadServer() {
        Response response = given().spec(RequestSpecUtil.getSpecification()).log().all()
                .param("album_id", VkUtils.getId(albumResponse))
                .get(EndPoints.PHOTO_GET_UPLOAD_SERVER.endPoint);
        response.then().statusCode(200);
        return JsonPath.from(response.asString()).getString("response.upload_url");
    }

    public Map<String, Object> uploadPhoto(String filePath) {
        Response response = given().spec(RequestSpecUtil.getSpecification()).log().all()
                .contentType("multipart/form-data")
                .multiPart(new File(filePath))
                .post(getUploadServer());
        response.then().statusCode(200);
        return JsonPath.from(response.asString()).getMap("$");
    }

    public void saveUploadedPhoto(String filePath) {
        Response response = given().spec(RequestSpecUtil.getSpecification()).log().all()
                .params(uploadPhoto(filePath))
                .param("album_id", VkUtils.getId(albumResponse))
                .get(EndPoints.SAVE_PHOTO.endPoint);
        response.then().statusCode(200);

    }

    public void parseResponse(Response response) {
        JsonPath.from(response.asString()).getInt("server");
        JsonPath.from(response.asString()).getInt("aid");
        JsonPath.from(response.asString()).getString("hash");

    }
}
