package api;

import api.utils.RequestSpecUtil;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class VkGroup {

    private Response groupResponse;
    private Response topicResponse;
    private List<Integer> commentIds = new ArrayList<>();
    private final String GROUP_ID = "group_id";
    private final String TOPIC_ID = "topic_id";

    public void createGroup(String title) {
        groupResponse = given().spec(RequestSpecUtil.getSpecification()).log().all()
                .param("title", title)
                .get(EndPoints.CREATE_GROUP.endPoint);
        groupResponse.then().statusCode(200);
    }

    public void leaveGroup() {
        Response response = given().spec(RequestSpecUtil.getSpecification()).log().all()
                .param(GROUP_ID, getGroupId(groupResponse))
                .get(EndPoints.LEAVE_GROUP.endPoint);
        response.then().statusCode(200);
        Assert.assertEquals(JsonPath.from(response.asString()).getInt("response."), 1, "Судя по ответу сервера, группа не покинута");
    }

    public void createTopic(String title, String text) {
        topicResponse = given().spec(RequestSpecUtil.getSpecification()).log().all()
                .param(GROUP_ID, getGroupId(groupResponse))
                .param("title", title)
                .param("text", text)
                .get(EndPoints.CREATE_TOPIC.endPoint);
        topicResponse.then().statusCode(200);
    }

    public void fixTopic() {
        Response response = given().spec(RequestSpecUtil.getSpecification()).log().all()
                .param(GROUP_ID, getGroupId(groupResponse))
                .param(TOPIC_ID, getTopicId(topicResponse))
                .get(EndPoints.FIX_TOPIC.endPoint);
        response.then().statusCode(200);
        Assert.assertEquals(JsonPath.from(response.asString()).getInt("response."), 1, "Судя по ответу сервера, топик не закрепился");
    }

    public void createComment(String text) {
        Response response = given().spec(RequestSpecUtil.getSpecification()).log().all()
                .param(GROUP_ID, getGroupId(groupResponse))
                .param(TOPIC_ID, getTopicId(topicResponse))
                .param("message", text)
                .get(EndPoints.CREATE_COMMENT.endPoint);
        response.then().statusCode(200);
        commentIds.add(JsonPath.from(response.asString()).getInt("response."));
    }

    public void editComment(int number, String newText) {
        Response response = given().spec(RequestSpecUtil.getSpecification()).log().all()
                .param(GROUP_ID, getGroupId(groupResponse))
                .param(TOPIC_ID, getTopicId(topicResponse))
                .param("comment_id", commentIds.get(number - 1))
                .param("message", newText)
                .get(EndPoints.EDIT_COMMENT.endPoint);
        response.then().statusCode(200);
        Assert.assertEquals(JsonPath.from(response.asString()).getInt("response."), 1, "Судя по ответу сервера, коммент не отредактировался");
    }

    public void deleteComment(int commentNum) {
        Response response = given().spec(RequestSpecUtil.getSpecification()).log().all()
                .param(GROUP_ID, getGroupId(groupResponse))
                .param(TOPIC_ID, getTopicId(topicResponse))
                .param("comment_id", commentIds.get(commentNum - 1))
                .get(EndPoints.DELETE_COMMENT.endPoint);
        response.then().statusCode(200);
        Assert.assertEquals(JsonPath.from(response.asString()).getInt("response."), 1, "Судя по ответу сервера, коммент не удалился");
    }

    public Integer getGroupId(Response response) {
        return JsonPath.from(response.asString()).getInt("response.id");
    }

    public Integer getTopicId(Response response) {
        return JsonPath.from(response.asString()).getInt("response.");
    }
}
