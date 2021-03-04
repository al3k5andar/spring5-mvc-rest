package guru.springframework.bootstrap;

import guru.springframework.domain.Category;
import guru.springframework.domain.Customer;
import guru.springframework.domain.Vendor;
import guru.springframework.repositories.CategoryRepository;
import guru.springframework.repositories.CustomerRepository;
import guru.springframework.repositories.VendorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap implements CommandLineRunner
{
    private final CategoryRepository categoryRepository;
    private final CustomerRepository customerRepository;
    private final VendorRepository vendorRepository;

    public Bootstrap(CategoryRepository categoryRepository,
                     CustomerRepository customerRepository,
                     VendorRepository vendorRepository) {
        this.categoryRepository = categoryRepository;
        this.customerRepository = customerRepository;
        this.vendorRepository = vendorRepository;
    }


    @Override
    public void run(String... args) throws Exception {

        for (Category category: CategoryData.categories())
            categoryRepository.save(category);

        for (Customer customer: CustomerData.customers())
            customerRepository.save(customer);

        for (Vendor vendor: VendorData.vendorList())
            vendorRepository.save(vendor);

        System.out.println("Categories loaded = "+ categoryRepository.count());
        System.out.println("Customers loaded = "+ customerRepository.count());
        System.out.println("Vendors loaded = "+ vendorRepository.count());
    }
}
