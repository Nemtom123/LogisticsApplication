package hu.webuni.logistics.dobiasz.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import hu.webuni.logistics.dobiasz.model.TransportPlan;

public interface TrasportPlanRepository extends JpaRepository<TransportPlan, Long>{

}
