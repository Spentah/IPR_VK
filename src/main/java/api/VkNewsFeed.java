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
    private Map<String, List<Object>> ids = new HashMap<>();

    public void getRecommendedFeed() {
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
        getIds(response);
//        return Arrays.asList(postId, sourceId, postType);
    }

    public void like(int number) {
        int itemId = 1001;
        int owner = -322371;
        String type = "post";
        List<Integer> ownerId = (List<Integer>) ids.get("owner_id").get(number - 1);

        Response response = given().spec(RequestSpecUtil.getSpecification()).log().all()
//                .params(getRecommendedFeed())
                  .param("type", ids.get("type").get(number - 1).toString())
                  .param("owner_id", ownerId.get(0))
                  .param("item_id", ids.get("item_id").get(number - 1))
//                .param("type", "post")
//                .param("owner_id", -140880848)
//                .param("item_id", list.get(0))
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
//        Map<String, List<Object>> ids = new HashMap<>();
//        ids.put("post_id", postIds.get(number - 1));
//        ids.put("type", postTypes.get(number - 1));
//        ids.put("owner_id", ownerIds.get(number - 1));
//        return ids;
    }
}
