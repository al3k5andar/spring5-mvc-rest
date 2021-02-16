package guru.springfamework.api.v1.model.category;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CategoryListDTO
{
    private List<CategoryDTO> categories;
}
