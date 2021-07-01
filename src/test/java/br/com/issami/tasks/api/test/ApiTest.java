package br.com.issami.tasks.api.test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.CoreMatchers;
import org.junit.BeforeClass;
import org.junit.Test;

public class ApiTest {

    @BeforeClass
    public static void setup() {
        RestAssured.baseURI = "http://localhost:8001/tasks-backend";
    }

    @Test
    public void deveRetornarTarefas() {
        RestAssured.given()
                   .when()
                        .get("/todo")
                   .then()
                        .statusCode(200);
    }

    @Test
    public void deveAdicionarTarefaComSuesso() {
        RestAssured.given()
                         .body("{ \"task\": \"Tarefa teste com RestAssured\", \"dueDate\": \"2021-06-29\" }")
                         .contentType(ContentType.JSON)
                   .when()
                        .post("/todo")
                   .then()
                        .statusCode(201);
    }

    @Test
    public void naoDeveAdicionarTarefaInvalida() {
        RestAssured.given()
                        .body("{ \"task\": \"Tarefa teste com RestAssured\", \"dueDate\": \"2010-06-29\" }")
                        .contentType(ContentType.JSON)
                   .when()
                        .post("/todo")
                   .then()
                        .statusCode(400)
                        .body("message", CoreMatchers.is("Due date must not be in past"));
    }
}