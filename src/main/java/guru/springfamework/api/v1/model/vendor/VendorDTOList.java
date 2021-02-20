package guru.springfamework.api.v1.model.vendor;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class VendorDTOList
{
    private List<VendorDTO> vendors;
}
