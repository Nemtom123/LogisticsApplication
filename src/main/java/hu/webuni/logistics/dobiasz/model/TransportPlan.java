package hu.webuni.logistics.dobiasz.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.OneToMany;
import javax.persistence.Id;

@Entity
public class TransportPlan {
	
	@Id
	@GeneratedValue
	private Long id;
	
	private Long expectedRevenue;
	
	@OneToMany(mappedBy = "transportPlan")
	private List<Section> section;
	
	public TransportPlan() {
	}

	public TransportPlan(Long id, Long expectedRevenue, List<Section> section) {
		super();
		this.id = id;
		this.expectedRevenue = expectedRevenue;
		this.section = section;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getExpectedRevenue() {
		return expectedRevenue;
	}

	public void setExpectedRevenue(Long expectedRevenue) {
		this.expectedRevenue = expectedRevenue;
	}

	public List<Section> getSection() {
		return section;
	}

	public void setSection(List<Section> section) {
		this.section = section;
	}

}
