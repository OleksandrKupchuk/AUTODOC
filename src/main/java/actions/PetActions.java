package actions;

import api.PetApi;
import com.github.javafaker.Faker;
import model.Category;
import model.Pet;
import model.Tag;
import status.PetStatus;

import java.util.ArrayList;

public class PetActions {
    private PetApi petApi= new PetApi();
    private Pet body;
    private int id;
    private String name;
    private Category category;
    private ArrayList<Tag> tags = new ArrayList<>();
    public int getId(){return id;}
    public String getName(){return name;}
    public Pet createPet(){
        Faker faker = new Faker();
        int petCategoryId = faker.number().numberBetween(0, 10000);
        int petDogTagId = faker.number().numberBetween(0, 10000);
        int petLabradorId = faker.number().numberBetween(0, 10000);
        id = faker.number().numberBetween(0, 10000);
        name = faker.animal().name();
        String[] photo = new String[]{"https://someurlimage"};

        category = new Category()
                .builder()
                .id(petCategoryId)
                .name("big")
                .build();

        Tag petDogTag = new Tag()
                .builder()
                .id(petDogTagId)
                .name("dog")
                .build();

        Tag petLabradorTag = new Tag()
                .builder()
                .id(petLabradorId)
                .name("Labrador")
                .build();

        tags.add(petDogTag);
        tags.add(petLabradorTag);

        body = new Pet()
                .builder()
                .id(id)
                .name(name)
                .category(category)
                .photoUrls(photo)
                .tags(tags)
                .status(PetStatus.AVAILABLE)
                .build();

        return petApi.addPet(body).as(Pet.class);
    }
}
