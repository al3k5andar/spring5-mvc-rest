package guru.springframework.bootstrap;

import guru.springframework.domain.Category;

import java.util.Arrays;
import java.util.List;

public class CategoryData
{
    public static List<Category> categories(){
        Category fruits= new Category();
        fruits.setName("Fruits");

        Category dried= new Category();
        dried.setName("Dried");

        Category fresh= new Category();
        fresh.setName("Fresh");

        Category exotic= new Category();
        exotic.setName("Exotic");

        Category nuts= new Category();
        nuts.setName("Nuts");

        return Arrays.asList(fruits,dried,fresh,exotic,nuts);
    }
}
