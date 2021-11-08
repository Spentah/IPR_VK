package api;

import api.profile_info.VkResponse;
import api.utils.RequestSpecUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class VkProfile {
    private VkResponse vkResponse;

    public void getProfileInfo() {
        Response response = given().spec(RequestSpecUtil.getSpecification()).log().all()
                .when()
                .get("account.getProfileInfo");
        response.then().statusCode(200);
        ObjectMapper mapper = new ObjectMapper();
        try {
            vkResponse = mapper.readValue(response.getBody().asString(), VkResponse.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        JsonPath.from("").getMap("$.response");
    }

    public void setProfileInfo(String field, String value) {
        Response response = given().spec(RequestSpecUtil.getSpecification()).log().all()
                .param(field, value)
                .when()
                .get("account.saveProfileInfo");
        response.then().statusCode(200);

    }

    public void findEmptyProfileInfo() {
        Map<String, String> profileFields = new HashMap<>();
        profileFields.put("first_name", vkResponse.getResponse().getFirstName());
        profileFields.put("last_name", vkResponse.getResponse().getFirstName());
        profileFields.put("bdate", vkResponse.getResponse().getBdate());
        profileFields.put("screen_name", vkResponse.getResponse().getScreenName());
        profileFields.put("status", vkResponse.getResponse().getStatus());
        profileFields.put("home_town", vkResponse.getResponse().getHomeTown());
        profileFields.put("country_id", vkResponse.getResponse().getCountry().getId() + "");
        profileFields.put("city_id", vkResponse.getResponse().getCity().getId() + "");

        if (profileFields.containsValue("")) {
            for (Map.Entry<String, String> entry : profileFields.entrySet()) {
                if (entry.getValue().isEmpty()) {

                }
            }
        }

    }
}
