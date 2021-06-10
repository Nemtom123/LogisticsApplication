package hu.webuni.logistics.dobiasz.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import hu.webuni.logistics.dobiasz.dto.DelayDto;
import hu.webuni.logistics.dobiasz.service.MilestoneService;
import hu.webuni.logistics.dobiasz.service.SectionService;
import hu.webuni.logistics.dobiasz.service.TransportPlanService;

@RestController
@RequestMapping("/api/transportPlans")
public class TransportPlansController {
	
	@Autowired
	TransportPlanService transportPlanService;
	
	@Autowired
	MilestoneService milestoneService;
	
	@Autowired
	SectionService sectionService;
	
	
	
	@PostMapping("/{id}/delay")
	public void addDelayTransportPlan(@RequestParam Long id, @RequestBody DelayDto delay) {
		 
		if(transportPlanService.findById(id).isEmpty()|| milestoneService.findById(delay.getMilestoneId()).isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		
		if(sectionService.findByTransportPlanAndMilestone(id, delay.getMilestoneId()).isEmpty())
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		
	     transportPlanService.registerDelay(id, delay.getMilestoneId(),delay.getDelay());
	}

}
