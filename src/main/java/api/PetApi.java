package api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import model.Pet;

public class PetApi {
    public Response addPet(Pet body){
        Response response = RestAssured.given()
                .body(body)
                .post("/pet");
        return response;
    }

    public Response getPetById(int id){
        Response response = RestAssured.given()
                .get("/pet/{id}", id);
        return response;
    }

    public void updatePet(int id, String name, String status){
        Response response = RestAssured.given()
                .pathParams("petId", id)
                .queryParam("name", name)
                .queryParam("status", status)
                .request("POST", "/pet/{petId}");
        response.prettyPrint();
    }

    public Response updatePet(Pet body){
        Response response = RestAssured.given()
                .body(body)
                .put("/pet");
        response.prettyPrint();
        return response;
    }

    public Response deletePet(int id){
        Response response = RestAssured.given()
                .delete("/pet/{id}", id);
        return response;
    }
}
