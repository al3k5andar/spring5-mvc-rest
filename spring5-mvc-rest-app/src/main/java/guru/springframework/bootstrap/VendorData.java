package guru.springframework.bootstrap;

import guru.springframework.domain.Vendor;

import java.util.Arrays;
import java.util.List;

public class VendorData
{
    public static List<Vendor> vendorList(){

        Vendor derby= new Vendor();
        derby.setId(1L);
        derby.setName("Derby");

        Vendor fruitCompany= new Vendor();
        fruitCompany.setId(2L);
        fruitCompany.setName("Fruit Company");

        Vendor exoticFruits= new Vendor();
        exoticFruits.setId(3L);
        exoticFruits.setName("Exotic Fruits");

        List<Vendor> vendors= Arrays.asList(derby, fruitCompany, exoticFruits);

        return vendors;
    }
}
