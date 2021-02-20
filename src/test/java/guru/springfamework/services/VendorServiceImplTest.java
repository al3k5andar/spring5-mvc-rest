package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.VendorMapper;
import guru.springfamework.api.v1.model.vendor.VendorDTO;
import guru.springfamework.domain.Vendor;
import guru.springfamework.repositories.VendorRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class VendorServiceImplTest {

    @Mock
    VendorRepository vendorRepository;

    VendorMapper vendorMapper;

    VendorServiceImpl vendorService;

    public static Long ID= 1L;
    public static String NAME= "Fruit";

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        vendorMapper= VendorMapper.INSTANCE;

        vendorService= new VendorServiceImpl(vendorRepository, vendorMapper);
    }

    @Test
    public void getAllVendors() {

//        Given
        Vendor fruit= new Vendor();
        fruit.setId(ID);
        fruit.setName(NAME);

        Vendor vegetables= new Vendor();
        vegetables.setId(2L);
        vegetables.setName("Vegetables");

        List<Vendor> vendorList= Arrays.asList(fruit, vegetables);

        Mockito.when(vendorRepository.findAll()).thenReturn(vendorList);

//        When
        List<VendorDTO> vendorDTOList= vendorService.getAllVendors();

//        Then
        Assert.assertEquals(vendorList.size(), vendorDTOList.size());


    }

    @Test
    public void getVendorById() {

//        Given
        Vendor vendor= new Vendor();
        vendor.setId(ID);
        vendor.setName(NAME);

        VendorDTO vendorDTO= new VendorDTO();
        vendorDTO.setId(vendor.getId());
        vendorDTO.setName(vendor.getName());;

        Mockito.when(vendorRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(vendor));

//        When
        VendorDTO returnedDto= vendorService.getVendorById(ID);

//        Then
        Assert.assertEquals(ID, returnedDto.getId());
        Assert.assertEquals(NAME, returnedDto.getName());
    }

    @Test
    public void createNewVendor() {

//        Given
        Vendor savedVendor= new Vendor();
        savedVendor.setName(NAME);

        VendorDTO dto= new VendorDTO();

        Mockito.when(vendorRepository.save(Mockito.any(Vendor.class))).thenReturn(savedVendor);

//        When
        VendorDTO returnedVendor= vendorService.createNewVendor(dto);

//        Then
        Assert.assertEquals(NAME, returnedVendor.getName());
    }

    @Test
    public void updateVendor() {

//        Given
        VendorDTO vendorDTO= new VendorDTO();
        vendorDTO.setName(NAME);

        Vendor updatedVendor= new Vendor();
        updatedVendor.setId(ID);
        updatedVendor.setName(vendorDTO.getName());

//        When
        Mockito.when(vendorRepository.save(Mockito.any())).thenReturn(updatedVendor);

        VendorDTO returnedDto= vendorService.replaceVendor(ID, vendorDTO);

//        Then
        Assert.assertNotNull(returnedDto);
        Assert.assertEquals(vendorDTO.getName(),returnedDto.getName());
    }
}