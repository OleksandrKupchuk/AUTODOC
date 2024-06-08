package api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import model.Pet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PetApi {
    private static final Logger logger = LogManager.getLogger(PetApi.class);
    public Response addPet(Pet body){
        logger.info("---GET PET RESPONSE---");
        Response response = RestAssured.given()
                .body(body)
                .post("/pet");
        return response;
    }

    public Response getPetById(int id){
        logger.info("---GET PET BY ID RESPONSE---");
        Response response = RestAssured.given()
                .get("/pet/{id}", id);
        return response;
    }

    public void updatePet(int id, String name, String status){
        logger.info("---UPDATE QUERY PET RESPONSE---");
        Response response = RestAssured.given()
                .pathParams("petId", id)
                .queryParam("name", name)
                .queryParam("status", status)
                .request("POST", "/pet/{petId}");
        response.prettyPrint();
    }

    public Response updatePet(Pet body){
        logger.info("---UPDATE BODY PET RESPONSE---");
        Response response = RestAssured.given()
                .body(body)
                .put("/pet");
        response.prettyPrint();
        return response;
    }

    public Response deletePet(int id){
        logger.info("---DELETE PET RESPONSE---");
        Response response = RestAssured.given()
                .delete("/pet/{id}", id);
        return response;
    }
}
