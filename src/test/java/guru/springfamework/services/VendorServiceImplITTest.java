package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.VendorMapper;
import guru.springfamework.api.v1.model.vendor.VendorDTO;
import guru.springfamework.bootstrap.Bootstrap;
import guru.springfamework.domain.Vendor;
import guru.springfamework.repositories.CategoryRepository;
import guru.springfamework.repositories.CustomerRepository;
import guru.springfamework.repositories.VendorRepository;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class VendorServiceImplITTest {

    @Autowired
    VendorRepository vendorRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CategoryRepository categoryRepository;

    VendorService vendorService;

    @Before
    public void setUp() throws Exception {
        Bootstrap bootstrap= new Bootstrap(categoryRepository,customerRepository,vendorRepository);
        bootstrap.run();

        vendorService= new VendorServiceImpl(vendorRepository, VendorMapper.INSTANCE);
    }

    @Test
    public void testChangeVendorName(){

        String name= "some name";
        long id= generateVendorId();

        Vendor original= vendorRepository.getOne(id);

        String originalName= original.getName();

        VendorDTO vendorDTO= new VendorDTO();
        vendorDTO.setName(name);

        vendorService.updateVendor(id, vendorDTO);

        Vendor updatedVendor= vendorRepository.findById(id).get();

        Assert.assertNotNull(updatedVendor);
        Assert.assertThat(originalName, Matchers.not(Matchers.equalTo(updatedVendor.getName())));
        Assert.assertEquals(name, updatedVendor.getName());

    }

    private Long generateVendorId(){
        return vendorRepository.findAll().get(0).getId();
    }
}