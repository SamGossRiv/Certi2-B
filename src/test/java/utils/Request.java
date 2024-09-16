package utils;

import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

public class Request {

    public static Response get(String url, int id) {
        return given()
                .header("Accept", "application/json")  // Indicamos que esperamos una respuesta en JSON
                .pathParam("id", id)
                .when()
                .get(url)
                .then()
                .contentType(JSON)  // Verificamos que la respuesta sea de tipo JSON
                .extract().response();
    }

    public static Response post(String url, Object body) {
        return given()
                .header("Content-Type", "application/json")
                .body(body)
                .when()
                .post(url)
                .then()
                .contentType(JSON)
                .extract().response();
    }

    public static Response put(String url, int id, Object body) {
        return given()
                .header("Accept", "application/json")  // Esperamos una respuesta en JSON
                .header("Content-Type", "application/json")  // Enviamos datos en formato JSON
                .header("Cookie", "token=abc123")  // Si la API requiere autenticación, añade un token
                .pathParam("id", id)
                .body(body)
                .when()
                .put(url)
                .then()
                .contentType(JSON)  // Verificamos que la respuesta sea de tipo JSON
                .extract().response();
    }


    public static Response delete(String url, int id) {
        return given()
                .header("Content-Type", "application/json")
                .pathParam("id", id)
                .when()
                .delete(url)
                .then()
                .extract().response();
    }
}

