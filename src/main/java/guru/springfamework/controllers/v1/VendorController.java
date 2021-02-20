package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.vendor.VendorDTO;
import guru.springfamework.api.v1.model.vendor.VendorDTOList;
import guru.springfamework.services.VendorService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(VendorController.BASE_URL)
public class VendorController {

    public static final String BASE_URL= "/api/v1/vendors";

    private final VendorService vendorService;

    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public VendorDTOList listVendors(){
        return new VendorDTOList(vendorService.getAllVendors());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public VendorDTO getVendorById(@PathVariable("id") Long id){
        return vendorService.getVendorById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VendorDTO createNewVendor(@RequestBody VendorDTO vendorDTO){
        return vendorService.createNewVendor(vendorDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public VendorDTO replaceVendor(@PathVariable("id") Long id, @RequestBody VendorDTO vendorDTO){
        return vendorService.replaceVendor(id, vendorDTO);
    }

    @PatchMapping("/{id}")
    public VendorDTO updateVendor(@PathVariable("id") Long id, @RequestBody VendorDTO vendorDTO){
        return vendorService.updateVendor(id, vendorDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteVendorById(@PathVariable("id") Long id){
        vendorService.deleteVendorById(id);
    }

}
