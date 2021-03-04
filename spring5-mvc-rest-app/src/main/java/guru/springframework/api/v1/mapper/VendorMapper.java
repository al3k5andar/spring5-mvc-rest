package guru.springframework.api.v1.mapper;

import guru.springframework.api.v1.model.vendor.VendorDTO;
import guru.springframework.domain.Vendor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface VendorMapper {

    VendorMapper INSTANCE= Mappers.getMapper(VendorMapper.class);

    @Mapping(source = "id", target = "id")
    VendorDTO vendorToVendorDTO(Vendor vendor);

    @Mapping(source = "id", target = "id")
    Vendor vendorDtoToVendor(VendorDTO vendorDTO);
}
