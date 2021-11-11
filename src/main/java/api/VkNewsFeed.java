package api;

import api.utils.RequestSpecUtil;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class VkNewsFeed {

    private List<String> list = new ArrayList<>();
    Map<String, Object> ids = new HashMap<>();

    public Map<String, Object> getRecommendedFeed() {
        Response response = given().spec(RequestSpecUtil.getSpecification()).log().all()
                .param("count", 10)
                .get(EndPoints.GET_NEWSFEED.endPoint);
        response.then().statusCode(200);
//        String postId = JsonPath.from(response.asString()).getInt("response.items[4].post_id") +"";
//        String sourceId = JsonPath.from(response.asString()).getInt("response.items[4].source_id") + "";
//        String postType = JsonPath.from(response.asString()).getString("response.items[4].post_type");
//        String ownerId = JsonPath.from(response.asString()).getInt("response.items[4].attachments[0].photo.owner_id") + "";
//        list.add(postId);
//        list.add(sourceId);
//        list.add(postType);
        return getIds(response, 5);
//        return Arrays.asList(postId, sourceId, postType);
    }

    public void like() {
        Response response = given().spec(RequestSpecUtil.getSpecification()).log().all()
//                .params(getRecommendedFeed())
//                .param("type", "post")
//                .param("owner_id", -140880848)
//                .param("item_id", list.get(0))
                .get("likes.add");
        response.then().statusCode(200);
    }

    public Map<String, Object> getIds(Response response, int number) {
        List<Integer> postIds = JsonPath.from(response.asString()).getList("response.items.post_id");
        List<String> postTypes = JsonPath.from(response.asString()).getList("response.items.post_type");
        int ownerId = JsonPath.from(response.asString()).getInt("response.items[" + number + "].attachments[0].photo.owner_id");
        Map<String, Object> ids = new HashMap<>();
        ids.put("post_id", postIds.get(number - 1));
        ids.put("type", postTypes.get(number - 1));
        ids.put("owner_id", ownerId);
        return ids;
    }
}
