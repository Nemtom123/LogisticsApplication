package hu.webuni.logistics.dobiasz.service;

import org.springframework.data.jpa.domain.Specification;

import hu.webuni.logistics.dobiasz.model.Address;
import hu.webuni.logistics.dobiasz.model.Address_;

public class AddressSpecification {
	
		
	public static Specification<Address> hasId(long id){
		return (root, cq, cb) -> cb.equal(root.get(Address_.id), id);
	}

	public static Specification<Address> hascCountryIsoCode(String countryIsoCode) {
		// TODO Auto-generated method stub
		return (root, cq, cb) -> cb.equal(root.get(Address_.countryIsoCode), countryIsoCode);
	}

	public static Specification<Address> hasCity(String city) {
		// TODO Auto-generated method stub
		return (root, cq, cb) -> cb.like(root.get(Address_.city), city + "%"); 
		
	}

	public static Specification<Address> hasStreet(String street) {
		// TODO Auto-generated method stub
		return (root, cq, cb) -> cb.like(root.get(Address_.street), street + "%"); 
	}

	public static Specification<Address> hasPostCode(String postCode) {
		// TODO Auto-generated method stub
		return (root, cq, cb) -> cb.like(root.get(Address_.postCode), postCode + "%");
	}

	public static Specification<Address> hasHauseNumber(String houseNumber) {
		// TODO Auto-generated method stub
		return (root, cq, cb) -> cb.equal(root.get(Address_.houseNumber), houseNumber);
	}

	public static Specification<Address> hasWidth(String width) {
		// TODO Auto-generated method stub
		return (root, cq, cb) -> cb.equal(root.get(Address_.width), width);
	}

	public static Specification<Address> hasLength(String length) {
		// TODO Auto-generated method stub
		return (root, cq, cb) -> cb.equal(root.get(Address_.length), length);
	}
	
	
	
}
