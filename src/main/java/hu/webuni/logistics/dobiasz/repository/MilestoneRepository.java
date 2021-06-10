package hu.webuni.logistics.dobiasz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import hu.webuni.logistics.dobiasz.model.Milestone;

public interface MilestoneRepository extends JpaRepository<Milestone, Long>{
	
	List<Milestone> findByAddressId(long id);

}
