package api.vk;

import api.endpoints.EndPoints;
import api.utils.RequestSpecUtil;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class VkNewsFeed {

    private Response recommendedFeed;
    private Map<String, List<Object>> ids = new HashMap<>();
    private final String OWNER = "owner_id";
    private final String PARSE_RESPONSE = "response.";

    public void getRecommendedFeed() {
        recommendedFeed = given().spec(RequestSpecUtil.getSpecification()).log().all()
                .get(EndPoints.GET_NEWSFEED.endPoint);
        recommendedFeed.then().statusCode(200);
    }

    public void like(int postNumber) {
        Response response = given().spec(RequestSpecUtil.getSpecification()).log().all()
                  .param("type", "post")
                .param("owner_id", getOwnerIdByNum(recommendedFeed, postNumber - 1))
                .param("item_id", getItemIdByNum(recommendedFeed, postNumber - 1))
                .get(EndPoints.ADD_LIKE.endPoint);
        response.then().statusCode(200);
    }

    public void ban(int number) {
        Response response = given().spec(RequestSpecUtil.getSpecification()).log().all()
                .param(OWNER, getOwnerIdByNum(recommendedFeed, number - 1))
                .get(EndPoints.BAN_ACCOUNT.endPoint);
        response.then().statusCode(200);
        Assert.assertTrue(JsonPath.from(response.asString()).getInt(PARSE_RESPONSE) == 1, "Судя по ответу, бан не произошел");
    }

    public void addToFavorite(int number) {
        Response response = given().spec(RequestSpecUtil.getSpecification()).log().all()
                .param("owner_id", getOwnerIdByNum(recommendedFeed, number - 1))
                .param("id", getItemIdByNum(recommendedFeed, number - 1))
                .get(EndPoints.ADD_TO_FAVORITE.endPoint);
        response.then().statusCode(200);
    }

    public Integer getOwnerIdByNum(Response response, int number) {
        return JsonPath.from(response.asString()).getInt("response.items.attachments[" + number + "].photo.owner_id[0]");
    }

    public Integer getItemIdByNum(Response response,  int number) {
        return JsonPath.from(response.asString()).getInt("response.items.post_id[" + number + "]");
    }
}
