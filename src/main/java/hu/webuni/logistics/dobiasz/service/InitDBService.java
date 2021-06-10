package hu.webuni.logistics.dobiasz.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.webuni.logistics.dobiasz.model.Address;
import hu.webuni.logistics.dobiasz.model.Milestone;
import hu.webuni.logistics.dobiasz.model.Section;
import hu.webuni.logistics.dobiasz.model.TransportPlan;

@Service
public class InitDBService {

	@Autowired
	AddressServices addressServices;

	@Autowired
	MilestoneService milestoneService;

	@Autowired
	SectionService sectionService;

	@Autowired
	TransportPlanService transportPlanService;

	public TransportPlan init() {

		Address address1 = addressServices.addNewAddress(new Address(1L, "HU", "Bugyi", "Nagy Ferenc", "2347", "11", null, null));
		Address address2 = addressServices.addNewAddress(new Address(2L, "HU", "Nagytőke", "Erkel Ferenc", "6612", "20", null, null));

		Milestone milestone1 = milestoneService.addNewMilestone(new Milestone(1, address1, LocalDateTime.of(2000, 10, 14, 9, 0)));
		Milestone milestone2 = milestoneService.addNewMilestone(new Milestone(2, address1, LocalDateTime.of(2000, 10, 13, 10, 0)));
		Milestone milestone3 = milestoneService.addNewMilestone(new Milestone(3, address2, LocalDateTime.of(2000, 10, 12, 11, 0)));
		Milestone milestone4 = milestoneService.addNewMilestone(new Milestone(4, address2, LocalDateTime.of(2000, 10, 11, 12, 0)));

		List<Section> sections = new ArrayList<>();
		sections.add(sectionService.addNewSection(new Section(1, milestone1, milestone2, 1, null)));
		sections.add(sectionService.addNewSection(new Section(2, milestone3, milestone4, 2, null)));

		return TransportPlanService.addNewTransportPlan(new TransportPlan(1L, 1000L, sections));
	}
}
