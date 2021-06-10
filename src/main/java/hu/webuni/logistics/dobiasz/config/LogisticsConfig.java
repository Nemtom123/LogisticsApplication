package hu.webuni.logistics.dobiasz.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix="logistics")
@Component
public class LogisticsConfig {
		
	private boolean test;
		
	private Reduction reduction = new Reduction();
	
	
	public static class Reduction {

		private int first;
		private int second;
		private int third;

		public int getFirst() {
			return first;
		}

		public void setFirst(int first) {
			this.first = first;
		}

		public int getSecond() {
			return second;
		}

		public void setSecond(int second) {
			this.second = second;
		}

		public int getThird() {
			return third;
		}

		public void setThird(int third) {
			this.third = third;
		}

	}

	public Reduction getReduction() {
		return reduction;
	}

	public void setReduction(Reduction reduction) {
		this.reduction = reduction;
	}

	
	public boolean isTest() {
		return test;
	}

	public void setTest(boolean test) {
		this.test = test;
	}

}
