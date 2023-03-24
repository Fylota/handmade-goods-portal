package hu.bme.edu.handmade.mappers;

import hu.bme.edu.handmade.models.Address;
import hu.bme.edu.handmade.web.dto.user.AddressDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AddressMapper {
    AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);

    Address toAddress(AddressDto dto);

    AddressDto toAddressDto(Address address);
}
