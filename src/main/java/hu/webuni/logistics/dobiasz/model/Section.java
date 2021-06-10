package hu.webuni.logistics.dobiasz.model;

import javax.persistence.*;
import javax.persistence.Id;

@Entity
public class Section {

	@Id
	@GeneratedValue
	private long id;
	
	private int number;
	
	@OneToOne
	private Milestone fromMilestone;
	
	@OneToOne
	private Milestone toMilestone;

	@ManyToOne
	@JoinColumn(name = "transportPlan_id")
	private TransportPlan transportPlan;


	public Section() {
	}

	public Section(long id, Milestone fromMilestone, Milestone toMilestone, int number, TransportPlan transportPlan) {
		this.id = id;
		this.fromMilestone = fromMilestone;
		this.toMilestone = toMilestone;
		this.number = number;
		this.transportPlan = transportPlan;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Milestone getFromMilestone() {
		return fromMilestone;
	}

	public void setFromMilestone(Milestone fromMilestone) {
		this.fromMilestone = fromMilestone;
	}

	public Milestone getToMilestone() {
		return toMilestone;
	}

	public void setToMilestone(Milestone toMilestone) {
		this.toMilestone = toMilestone;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public TransportPlan getTransportPlan() {
		return transportPlan;
	}

	public void setTransportPlan(TransportPlan transportPlan) {
		this.transportPlan = transportPlan;
	}

}