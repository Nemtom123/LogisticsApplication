package hu.webuni.logistics.dobiasz.web;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import hu.webuni.logistics.dobiasz.dto.AddressDto;
import hu.webuni.logistics.dobiasz.dto.ExampleDto;
import hu.webuni.logistics.dobiasz.mapper.AddressMapper;
import hu.webuni.logistics.dobiasz.model.Address;
import hu.webuni.logistics.dobiasz.service.AddressServices;

@RestController
@RequestMapping("/api/addresses")
public class AddressesController {
	
	@Autowired
	AddressServices addressServices;
	
	@Autowired
	AddressMapper addressMapper;

	//private Page<Address> page;
	
	@GetMapping
	public List<AddressDto> getAll(){
		return addressMapper.addressToDtos(addressServices.findAll());
		
	}
	
	@GetMapping("/{id}")
	public AddressDto getById(@PathVariable long id) {
		Address address = addressServices.findById(id)
				.orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
		return addressMapper.addressToDtos(address);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<AddressDto> modifyAddress(@PathVariable long id, @RequestBody AddressDto addressDto){
		if(addressDto.getId() != null && addressDto.getId() != 0 && addressDto.getId() != id)
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		Address address = addressMapper.dtoToAddress(addressDto);
		address.setId(id);
		try {
			AddressDto savedAddressDto = addressMapper.addressToDtos(addressServices.updateAddress(address));
			return ResponseEntity.ok(savedAddressDto);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping
	public AddressDto addNewAddress(@RequestBody @Valid AddressDto addressDto) {
		if (addressDto.getId() != null && addressDto.getId() != 0L)
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
			return addressMapper.addressToDtos(addressServices.addNewAddress(addressMapper.dtoToAddress(addressDto)));
	}
	

	@PostMapping(value = "/search")
	public ResponseEntity<List<AddressDto>>  findByExample(@RequestBody ExampleDto example,@PageableDefault(direction = Direction.ASC, size = Integer.MAX_VALUE, sort="id") Pageable pageable){
		Page<Address> page = addressServices.findAddressExample(example, pageable);
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("Total", Long.toString(page.getTotalElements()));
		
		return ResponseEntity.ok()
				.headers(responseHeaders)
				.body(addressMapper.addressToDtos(page.getContent()));
	}
	
	
	@DeleteMapping("/{id}")
	public void deleteAddress(@PathVariable long id) {
		try {
			addressServices.delete(id);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
	}
	
	
	
}
