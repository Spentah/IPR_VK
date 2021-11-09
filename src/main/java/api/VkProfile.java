package api;

import api.profile_info.ProfileInfo;
import api.utils.RequestSpecUtil;
import api.utils.VkUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.log4testng.Logger;

import java.io.File;
import java.util.*;

import static io.restassured.RestAssured.given;

public class VkProfile {
    private final Logger logger = Logger.getLogger(VkProfile.class);
    private ProfileInfo profileInfo;

    public ProfileInfo getVkResponse() {
        return profileInfo;
    }

    public void getProfileInfo() {
        Response response = given().spec(RequestSpecUtil.getSpecification()).log().all()
                .when()
                .get(EndPoints.GET_PROFILE_INFO.endPoint);
        response.then().statusCode(200);
        ObjectMapper mapper = new ObjectMapper();
        try {
            profileInfo = mapper.readValue(response.getBody().asString(), ProfileInfo.class);
            VkUtils.setCurrentProfile(profileInfo);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public void setProfileInfo(String field, String value) {
        Response response = given().spec(RequestSpecUtil.getSpecification()).log().all()
                .param(field, value)
                .when()
                .get(EndPoints.SAVE_PROFILE_INFO.endPoint);
        response.then().statusCode(200);
    }

    public String findEmptyProfileInfo() {
        Map<String, String> profileFields = new HashMap<>();
        profileFields.put("first_name", profileInfo.getResponse().getFirstName());
        profileFields.put("last_name", profileInfo.getResponse().getFirstName());
        profileFields.put("bdate", profileInfo.getResponse().getBdate());
        profileFields.put("screen_name", profileInfo.getResponse().getScreenName());
        profileFields.put("status", profileInfo.getResponse().getStatus());
        profileFields.put("home_town", profileInfo.getResponse().getHomeTown());
        profileFields.put("country_id", profileInfo.getResponse().getCountry().getId() + "");
        profileFields.put("city_id", profileInfo.getResponse().getCity().getId() + "");

        List<String> emptyFields = new ArrayList<>();
        for (Map.Entry<String, String> pair : profileFields.entrySet()) {
            if (pair.getValue().equals("")) {
                emptyFields.add(pair.getKey());
            }
        }

        if (!emptyFields.isEmpty()) {
            String result = emptyFields.get(0);
            emptyFields.remove(0);
            return result;
        }
        return "";
    }

    public String getPhotoUploadServer() {
        Response response = given().spec(RequestSpecUtil.getSpecification()).log().all()
                .param("owner_id", profileInfo.getResponse().getId())
                .get(EndPoints.GET_PHOTO_UPLOAD_SERVER.endPoint);
        response.then().statusCode(200);
        return JsonPath.from(response.asString()).getString("response.upload_url");
    }

    public Map<String, String> uploadPhotoOnServer() {
        Response response = given().spec(RequestSpecUtil.getSpecification()).log().all()
                .when()
                .contentType("multipart/form-data")
                .multiPart(new File("src/main/resources/fish.jpg"))
                .post(getPhotoUploadServer());
        response.then().statusCode(200);
        return JsonPath.from(response.asString()).getMap("$");
    }

    public void saveUploadedPhoto() {
        Response response = given().spec(RequestSpecUtil.getSpecification()).log().all()
                .param("server", uploadPhotoOnServer().get("server"))
                .param("photo", uploadPhotoOnServer().get("photo"))
                .param("hash", uploadPhotoOnServer().get("hash"))
                .when()
                .get(EndPoints.SAVE_PHOTO_ON_SERVER.endPoint);
        response.then().statusCode(200);
        if (JsonPath.from(response.asString()).getInt("response.saved") != 1)
            logger.error("Фото не сохранено");
    }



}
