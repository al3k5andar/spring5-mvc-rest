package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.vendor.VendorDTO;
import guru.springfamework.api.v1.model.vendor.VendorDTOList;
import guru.springfamework.services.VendorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Api(description = "This is the Vendor Api")
@RestController
@RequestMapping(VendorController.BASE_URL)
public class VendorController {

    public static final String BASE_URL= "/api/v1/vendors";

    private final VendorService vendorService;

    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @ApiOperation(value = "View list of vendors", notes = "There are some API Notes")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public VendorDTOList listVendors(){
        return new VendorDTOList(vendorService.getAllVendors());
    }


    @ApiOperation(value = "Get vendor by ID")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public VendorDTO getVendorById(@PathVariable("id") Long id){
        return vendorService.getVendorById(id);
    }


    @ApiOperation(value = "Create a new vendor")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VendorDTO createNewVendor(@RequestBody VendorDTO vendorDTO){
        return vendorService.createNewVendor(vendorDTO);
    }

    @ApiOperation(value = "Replace an existing vendor with new data")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public VendorDTO replaceVendor(@PathVariable("id") Long id, @RequestBody VendorDTO vendorDTO){
        return vendorService.replaceVendor(id, vendorDTO);
    }

    @ApiOperation(value = "Update an existing vendor")
    @PatchMapping("/{id}")
    public VendorDTO updateVendor(@PathVariable("id") Long id, @RequestBody VendorDTO vendorDTO){
        return vendorService.updateVendor(id, vendorDTO);
    }

    @ApiOperation(value = "Delete a vendor by ID")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteVendorById(@PathVariable("id") Long id){
        vendorService.deleteVendorById(id);
    }

}
