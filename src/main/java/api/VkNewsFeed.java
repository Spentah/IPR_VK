package api;

import api.utils.RequestSpecUtil;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class VkNewsFeed {

    public void getRecommendedFeed() {
        Response response = given().spec(RequestSpecUtil.getSpecification()).log().all()
                .param("count", 10)
                .get(EndPoints.GET_NEWSFEED.endPoint);
        //надо обновить токен, требуется доступ к wall и friends
        response.then().statusCode(200);
    }
}