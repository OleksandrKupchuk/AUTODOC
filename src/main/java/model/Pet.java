package model;

import lombok.*;

import java.util.ArrayList;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Pet {
    public int id;
    public String name;
    public Category category;
    public String[] photoUrls;
    public ArrayList<Tag> tags;
    public String status;
}
