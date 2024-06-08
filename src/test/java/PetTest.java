import actions.PetActions;
import api.PetApi;
import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import model.Pet;
import model.Tag;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import status.PetStatus;

import java.util.ArrayList;


public class PetTest {
    private PetApi petApi = new PetApi();;
    private ThreadLocal<PetActions> petActions = new ThreadLocal<>();

    @BeforeClass
    public void setup(){
        RestAssured.baseURI = "https://petstore3.swagger.io/api/v3";
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .addHeader("Content-Type", "application/json")
                .build();
    }

    @BeforeMethod
    public void setupTest(){
        petActions.set(new PetActions());
    }

    @AfterMethod
    public void tearDown(){
        if (petApi.getPetById(petActions.get().getId()).statusCode() != 404){
            petApi.deletePet(petActions.get().getId());
        }
    }

    @Test
    public void createPet(){
        Pet pet = petActions.get().createPet();

        Assert.assertEquals(pet.getId(), petActions.get().getId());
        Assert.assertEquals(pet.getName(), petActions.get().getName());
    }

    @Test
    public void getPetById(){
        Pet pet = petActions.get().createPet();

        Assert.assertEquals(pet.getId(), petActions.get().getId());
        Assert.assertEquals(pet.getName(), petActions.get().getName());

        Pet petById = petApi.getPetById(pet.getId()).as(Pet.class);
        Assert.assertEquals(petById.getStatus(), PetStatus.AVAILABLE);
    }

    @Test
    public void updatePetByIdWithQueryPram(){
        Faker faker = new Faker();
        String editedPetName = faker.animal().name();

        Pet pet = petActions.get().createPet();

        Assert.assertEquals(pet.getId(), petActions.get().getId());
        Assert.assertEquals(pet.getName(), pet.getName());

        petApi.updatePet(pet.getId(), editedPetName, PetStatus.SOLD);

        Pet petById = petApi.getPetById(pet.getId()).as(Pet.class);
        Assert.assertEquals(petById.getName(), editedPetName);
        Assert.assertEquals(petById.getStatus(), PetStatus.SOLD);
    }

    @Test
    public void updatePetByIdWithBody(){
        Faker faker = new Faker();
        int editedPetCategoryId = faker.number().numberBetween(0, 10000);
        String editedPetName = faker.animal().name();

        Pet pet = petActions.get().createPet();

        Assert.assertEquals(pet.getId(), petActions.get().getId());
        Assert.assertEquals(pet.getName(), petActions.get().getName());

        Tag editedPetCategory = new Tag()
                .builder()
                .id(editedPetCategoryId)
                .name("small")
                .build();

        pet.setName(editedPetName);
        pet.setTags(new ArrayList<>(){{
            add(editedPetCategory);
        }});

        Pet updatedPet = petApi.updatePet(pet).as(Pet.class);

        Assert.assertEquals(updatedPet.getName(), editedPetName);
        Assert.assertEquals(updatedPet.getTags().get(0).name, "small");
    }

    @Test
    public void deletePet(){
        Pet pet = petActions.get().createPet();

        petApi.deletePet(pet.getId());

        Response response = petApi.getPetById(pet.getId());
        Assert.assertEquals(response.statusCode(), 404);
    }
}
