package api;

import api.utils.RequestSpecUtil;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class VkGroup {

    Response groupResponse;

    public void createGroup(String title) {
        groupResponse = given().spec(RequestSpecUtil.getSpecification()).log().all()
                .param("title", title)
                .get(EndPoints.CREATE_GROUP.endPoint);
        groupResponse.then().statusCode(200);
    }

    public void createTopic(String title) {
        Response response = given().spec(RequestSpecUtil.getSpecification()).log().all()
                .param("group_id", getGroupId())
                .param("title", title)
                .get(EndPoints.CREATE_TOPIC.endPoint);
        response.then().statusCode(200);

    }

    public Integer getGroupId() {
        return JsonPath.from(groupResponse.asString()).getInt("response.id");
    }
}
