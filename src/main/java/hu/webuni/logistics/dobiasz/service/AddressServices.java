package hu.webuni.logistics.dobiasz.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import hu.webuni.logistics.dobiasz.dto.ExampleDto;
import hu.webuni.logistics.dobiasz.model.Address;
import hu.webuni.logistics.dobiasz.repository.AddressRepository;

@Service
public class AddressServices {

	
		@Autowired
		AddressRepository addressRepository;
		
		public List<Address> findAll(){
			return addressRepository.findAll();
		}
		
	    public Optional<Address> findById(long id) {
	        return addressRepository.findById(id);
	    }
	    
	    public void delete(long id) {
	        addressRepository.deleteById(id);
	    }

	    @Transactional
	    public void deleteAddress(long id) {
	       if ( addressRepository.findById(id).isPresent());
	       addressRepository.deleteById(id);
	    }
	    @Transactional
	    public void deleteAll() {
	    	addressRepository.deleteAll();
	    }
	 
	    @Transactional
	    public Address addNewAddress(Address address) {
    		return addressRepository.save(address);
	    }
	    
	    @Transactional
	    public Address updateAddress(Address address) {
	    	if(addressRepository.existsById(address.getId())) {
	    		return addressRepository.save(address);
	    	}
	    	else {
	    		throw new NoSuchElementException();
	    	}
	    			    	
	    }
	    
	    public Page<Address> findAddressExample(ExampleDto example, Pageable pageable) {
	    	
	        long id = example.getId();
	        String countryIsoCode = example.getCountryIsoCode();
			String city = example.getCity();
			String street = example.getStreet();
			String postCode = example.getPostCode();
			String houseNumber = example.getHouseNumber();
			String width = example.getWidth();
			String length = example.getLength();
	     
	        Specification<Address> spec = Specification.where(null);

	        if (id > 0) {
	            spec = spec.and(AddressSpecification.hasId(id));
	        }

	        if (StringUtils.hasText(countryIsoCode))
	            spec = spec.and(AddressSpecification.hascCountryIsoCode(countryIsoCode));

	        if (StringUtils.hasText(city))
	            spec = spec.and(AddressSpecification.hasCity(city));
	        
	        if (StringUtils.hasText(street))
	            spec = spec.and(AddressSpecification.hasStreet(street));
	        
	        if (StringUtils.hasText(postCode))
	            spec = spec.and(AddressSpecification.hasPostCode(postCode));
	        
	        if (StringUtils.hasText(houseNumber))
	            spec = spec.and(AddressSpecification.hasHauseNumber(houseNumber));
	        
	        if (StringUtils.hasText(width))
	            spec = spec.and(AddressSpecification.hasWidth(width));
	        
	        if (StringUtils.hasText(length))
	            spec = spec.and(AddressSpecification.hasLength(length));
	        
	        return addressRepository.findAll(spec, pageable);

	    }	
}
