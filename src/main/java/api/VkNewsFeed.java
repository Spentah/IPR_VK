package api;

import api.utils.RequestSpecUtil;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class VkNewsFeed {

    private Response recommendedFeed;
    private Map<String, List<Object>> ids = new HashMap<>();

    public void getRecommendedFeed() {
        recommendedFeed = given().spec(RequestSpecUtil.getSpecification()).log().all()
                .param("count", 10)
                .get(EndPoints.GET_NEWSFEED.endPoint);
        recommendedFeed.then().statusCode(200);
        getIds(recommendedFeed);
    }

    public void like(int postNumber) {
        List<Integer> ownerId = (List<Integer>) ids.get("owner_id").get(postNumber - 1);
        Integer itemId = (Integer) ids.get("item_id").get(postNumber - 1);
        Response response = given().spec(RequestSpecUtil.getSpecification()).log().all()
                  .param("type", ids.get("type").get(postNumber - 1).toString())
                  .param("owner_id", ownerId.get(0))
                  .param("item_id", itemId)
                .get(EndPoints.ADD_LIKE.endPoint);
        response.then().statusCode(200);
    }

    public void getIds(Response response) {
        List<Object> postIds = JsonPath.from(response.asString()).getList("response.items.post_id");
        List<Object> postTypes = JsonPath.from(response.asString()).getList("response.items.post_type");
        List<Object> ownerIds = JsonPath.from(response.asString()).getList("response.items.attachments.photo.owner_id");
        ids.put("item_id", postIds);
        ids.put("type", postTypes);
        ids.put("owner_id", ownerIds);
    }

    public void getIdsFromRec(Response response, int number) {

    }
}
