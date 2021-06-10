package hu.webuni.logistics.dobiasz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import hu.webuni.logistics.dobiasz.model.Address;

public interface AddressRepository extends JpaRepository<Address, Long>, JpaSpecificationExecutor<Address> {
		
}
