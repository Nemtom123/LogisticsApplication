package hu.webuni.logistics.dobiasz.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import hu.webuni.logistics.dobiasz.dto.AddressDto;
import hu.webuni.logistics.dobiasz.model.Address;

@Mapper(componentModel = "spring")
public interface AddressMapper {

	List<AddressDto> addressToDtos(List<Address> address);

	AddressDto addressToDtos(Address address);

	Address dtoToAddress(AddressDto addressDto);

	List<Address> dtosToAddress(List<AddressDto> addressDtos);
}
