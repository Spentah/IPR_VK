package api.utils;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class RequestSpecUtil {
    private static RequestSpecification specification;

    private RequestSpecUtil() {

    }

    public static RequestSpecification getSpecification() {
        return specification;
    }

    public static void setSpecification() {
        specification = new RequestSpecBuilder()
                .setBaseUri("https://api.vk.com/method/")
//                .addPathParam("{METHOD}", method)
                .addQueryParam("access_token", DatabaseExecutor.extract("token"))
                .addQueryParam("v", System.getProperty("api_version"))
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();
    }

}
