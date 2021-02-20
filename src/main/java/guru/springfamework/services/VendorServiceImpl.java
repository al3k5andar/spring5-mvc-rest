package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.VendorMapper;
import guru.springfamework.api.v1.model.vendor.VendorDTO;
import guru.springfamework.controllers.v1.VendorController;
import guru.springfamework.domain.Vendor;
import guru.springfamework.repositories.VendorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VendorServiceImpl implements VendorService {

    private final VendorRepository vendorRepository;
    private final VendorMapper vendorMapper;

    public VendorServiceImpl(VendorRepository vendorRepository, VendorMapper vendorMapper) {
        this.vendorRepository = vendorRepository;
        this.vendorMapper = vendorMapper;
    }

    @Override
    public List<VendorDTO> getAllVendors() {
        return vendorRepository.findAll().stream()
                .map(vendor -> {
                    VendorDTO vendorDTO= vendorMapper.vendorToVendorDTO(vendor);
                    vendorDTO.setVendorUrl(getVendorUrl(vendor.getId()));
                    return vendorDTO;
        }).collect(Collectors.toList());
    }

    @Override
    public VendorDTO getVendorById(Long id) {
        return vendorRepository.findById(id).map(vendor -> {
            VendorDTO vendorDTO= vendorMapper.vendorToVendorDTO(vendor);
            vendorDTO.setVendorUrl(getVendorUrl(vendor.getId()));
            return vendorDTO;
        }).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public VendorDTO createNewVendor(VendorDTO vendorDTO) {
        return saveAndReturnDto(vendorDTO);
    }

    private VendorDTO saveAndReturnDto(VendorDTO vendorDTO){
        Vendor vendor= vendorMapper.vendorDtoToVendor(vendorDTO);

        VendorDTO savedVendor= vendorMapper.vendorToVendorDTO(vendorRepository.save(vendor));
        savedVendor.setVendorUrl(getVendorUrl(savedVendor.getId()));
        return savedVendor;
    }

    @Override
    public VendorDTO replaceVendor(Long id, VendorDTO vendorDTO) {
        Vendor vendor= vendorMapper.vendorDtoToVendor(vendorDTO);
        vendor.setId(id);

        VendorDTO updatedVendor= vendorMapper.vendorToVendorDTO(vendorRepository.save(vendor));
        updatedVendor.setVendorUrl(getVendorUrl(updatedVendor.getId()));
        return updatedVendor;
    }

    @Override
    public VendorDTO updateVendor(Long id, VendorDTO vendorDTO) {
        Vendor theVendor= vendorRepository.findById(id).map(vendor -> {
            if(vendorDTO.getName()!= null)
                vendor.setName(vendorDTO.getName());
            return vendor;
        }).orElseThrow(ResourceNotFoundException::new);

        VendorDTO updatedVendor= vendorMapper.vendorToVendorDTO(vendorRepository.save(theVendor));
        updatedVendor.setVendorUrl(getVendorUrl(updatedVendor.getId()));

        return updatedVendor;
    }

    private String getVendorUrl(Long id){
        return VendorController.BASE_URL+ "/" +id;
    }
}
