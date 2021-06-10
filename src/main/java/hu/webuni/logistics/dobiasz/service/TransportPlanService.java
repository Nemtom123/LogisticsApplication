package hu.webuni.logistics.dobiasz.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hu.webuni.logistics.dobiasz.config.LogisticsConfig;
import hu.webuni.logistics.dobiasz.config.LogisticsConfig.Reduction;
import hu.webuni.logistics.dobiasz.model.Milestone;
import hu.webuni.logistics.dobiasz.model.Section;
import hu.webuni.logistics.dobiasz.model.TransportPlan;
import hu.webuni.logistics.dobiasz.repository.TrasportPlanRepository;

@Service
public class TransportPlanService {
	
	@Autowired
	static
	TrasportPlanRepository transportPlanRepository;
	
	@Autowired 
	SectionService sectionService;
	
	@Autowired
	MilestoneService milestoneService;
	
	@Autowired
	LogisticsConfig config;
	
	public List<TransportPlan> getAllTransportPlans() {
		return transportPlanRepository.findAll();
	}

	public Optional<TransportPlan> findById(long id) {
		return transportPlanRepository.findById(id);
	}
	
	@Transactional
	public static TransportPlan addNewTransportPlan(TransportPlan transportPlan) {
		TransportPlan newTransportPlan = transportPlanRepository.save(transportPlan);
		newTransportPlan.getSection().stream().forEach(s -> s.setTransportPlan(newTransportPlan));
		return newTransportPlan;
		
	}
	
	@Transactional
	public TransportPlan updateTransportPlan(TransportPlan transportPlan) {
		return transportPlanRepository.save(transportPlan);
	}
	
	@Transactional
	public void deleteAll() {
		sectionService.getAllSections().stream().forEach(s -> s.setTransportPlan(null));
		getAllTransportPlans().stream().forEach(t -> t.setSection(null));
		transportPlanRepository.deleteAll();
	}
	
	@Transactional
	public long registerDelay(long transportPlanId, long milestoneId, int delayInMinutes) {
		long newRevenue = adjustRevenue(transportPlanId, delayInMinutes);	
		setDelayInAffectMilestones(transportPlanId, milestoneId, delayInMinutes);		
		return newRevenue;
	}

	private long adjustRevenue(long transportPlanId, int delayInMinutes) {
		TransportPlan transportPlan = transportPlanRepository.findById(transportPlanId).get();
		long current = transportPlan.getExpectedRevenue();
		long unit = 100;
		Reduction reduction = config.getReduction();
		if (delayInMinutes < 30) {
			current = (long) (((unit-reduction.getFirst()) / unit)* current);
		} else if (delayInMinutes < 60) {
			current = (long)(((unit-reduction.getSecond()) / unit)* current);
		} else if (delayInMinutes < 120) {
			current = (long)(((unit-reduction.getThird()) / unit)* current);
		} else {
			current = (long)(((unit-reduction.getThird()) / unit)* current);
		}
		transportPlan.setExpectedRevenue(current);

		return current;
	}
	
	
	private void setDelayInAffectMilestones(long transportPlanId, long firstMilestoneId, int delayInMinutes) {
		Milestone currentMilestone = milestoneService.findById(firstMilestoneId).get();
		currentMilestone.setPlannedTime(currentMilestone.getPlannedTime().plusMinutes(delayInMinutes));
		Section section = currentMilestone.getSection();
		Milestone nextMilestone = null;
		if (section.getFromMilestone().equals(currentMilestone)) {
			nextMilestone = section.getToMilestone();
		} else {
			int nextSectionNumber = (int) (section.getNumber() + 1);
			Section nextSection = sectionService.findByTransportPlanIdAndNumber(transportPlanId, nextSectionNumber).orElse(null);
			if (nextSection != null) {	
				nextMilestone = nextSection.getFromMilestone();
			}
		}
		
		if (nextMilestone != null) {
			nextMilestone.setPlannedTime(nextMilestone.getPlannedTime().plusMinutes(delayInMinutes));
		}
	}

}
