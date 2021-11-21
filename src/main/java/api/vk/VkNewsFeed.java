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
    private Map<String, Integer> idsFromRec = new HashMap<>();

    public void getRecommendedFeed() {
        recommendedFeed = given().spec(RequestSpecUtil.getSpecification()).log().all()
                .get(EndPoints.GET_NEWSFEED.endPoint);
        recommendedFeed.then().statusCode(200);
//        getIds(recommendedFeed);
    }

    public void like(int postNumber) {
//        List<Integer> ownerId = (List<Integer>) ids.get("owner_id").get(postNumber - 1);
//        Integer itemId = (Integer) ids.get("item_id").get(postNumber - 1);
        Response response = given().spec(RequestSpecUtil.getSpecification()).log().all()
                  .param("type", "post")
                .param("owner_id", getOwnerIdByNum(recommendedFeed, postNumber - 1))
                .param("item_id", getItemIdByNum(recommendedFeed, postNumber - 1))
//                  .params(getIdsFromRec(recommendedFeed, postNumber))
//                  .param("type", ids.get("type").get(postNumber - 1).toString())
//                  .param("owner_id", ownerId.get(0))
//                  .param("item_id", itemId)
                .get(EndPoints.ADD_LIKE.endPoint);
        response.then().statusCode(200);
        Assert.assertTrue(JsonPath.from(response.asString()).getInt(PARSE_RESPONSE) == 1, "Судя по ответу, лайк не произошел");
    }

    public void ban(int number) {
        Response response = given().spec(RequestSpecUtil.getSpecification()).log().all()
                .param(OWNER, getOwnerIdByNum(recommendedFeed, number - 1))
                .get(EndPoints.BAN_ACCOUNT.endPoint);
        response.then().statusCode(200);
        Assert.assertTrue(JsonPath.from(response.asString()).getInt(PARSE_RESPONSE) == 1, "Судя по ответу, бан не произошел");
    }

    /*
    МЕТОД ВОЗВРАЩАЕТ ОШИБКУ, хотя все правильно
     */
    public void addToFavorite(int number) {
        Response response = given().spec(RequestSpecUtil.getSpecification()).log().all()
                .param("owner_id", getOwnerIdByNum(recommendedFeed, number - 1))
                .param("id", getItemIdByNum(recommendedFeed, number - 1))
                .get(EndPoints.ADD_TO_FAVORITE.endPoint);
        response.then().statusCode(200);
//        Assert.assertTrue(JsonPath.from(response.asString()).getInt(PARSE_RESPONSE) == 1, "Судя по ответу, запись не добавлена в закладки");
    }

    public void getIds(Response response) {
        List<Object> postIds = JsonPath.from(response.asString()).getList("response.items.post_id");
        List<Object> postTypes = JsonPath.from(response.asString()).getList("response.items.post_type");
        List<Object> ownerIds = JsonPath.from(response.asString()).getList("response.items.attachments.photo.owner_id");
        ids.put("item_id", postIds);
        ids.put("type", postTypes);
        ids.put(OWNER, ownerIds);
    }

    public Integer getOwnerIdByNum(Response response, int number) {
        return JsonPath.from(response.asString()).getInt("response.items.attachments[" + number + "].photo.owner_id[0]");
    }

    public Integer getItemIdByNum(Response response,  int number) {
        return JsonPath.from(response.asString()).getInt("response.items.post_id[" + number + "]");
    }

    public Map<String, Integer> getIdsFromRec(Response response, int number) {
        int itemId = JsonPath.from(response.asString()).getInt("response.items.post_id[" + number + "]");
        int ownerId = JsonPath.from(response.asString()).getInt("response.items.attachments[" + number + "].photo.owner_id[0]");
        Map<String, Integer> i = new HashMap<>();
        i.put("item_id", itemId);
        i.put(OWNER, ownerId);
        return i;
    }
}
