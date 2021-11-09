package api;

import api.utils.RequestSpecUtil;
import api.utils.VkUtils;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;

import java.io.File;

import static io.restassured.RestAssured.given;

public class VkDocument {

    public void getDocumentType(int owner_id) {
        Response response = given().spec(RequestSpecUtil.getSpecification()).log().all()
                .param("owner_id", owner_id)
                .get(EndPoints.GET_DOCUMENT_TYPES.endPoint);
        response.then().statusCode(200);

        int documentCount = JsonPath.from(response.asString()).getInt("response.items.find{it.name == 'Текстовые'}.count");
        Assert.assertTrue(documentCount >= 0, "Не осталось места для загрузки текстовых документов");
    }

    public String getUploadServer() {
        Response response = given().spec(RequestSpecUtil.getSpecification()).log().all()
                .get(EndPoints.GET_DOCS_UPLOAD_SERVER.endPoint);
        response.then().statusCode(200);
        return JsonPath.from(response.asString()).getString("response.upload_url");
    }

    public String uploadDocument() {
        Response response = given().log().all()
                .contentType("multipart/form-data")
                .multiPart(new File("src/main/resources/test.txt"))
                .post(getUploadServer());
        response.then().statusCode(200);
         return JsonPath.from(response.asString()).getString("file");
    }

    public void saveDocument(String documentName) {
        Response response = given().spec(RequestSpecUtil.getSpecification()).log().all()
                .param("file", uploadDocument())
                .param("title", documentName)
                .get(EndPoints.SAVE_DOCUMENT.endPoint);
        response.then().statusCode(200);
    }

    public int getDocIdByName(String documentName) {
        Response response = given().spec(RequestSpecUtil.getSpecification()).log().all()
                .param("type", 1)
                .param("owner_id", VkUtils.getCurrentOwnerId())
                .get(EndPoints.GET_DOC_ID.endPoint);
        response.then().statusCode(200);
        int documentId = JsonPath.from(response.asString()).getInt("response.items.find{it.title == '" + documentName + "'}.id");
        return documentId;
    }
}
