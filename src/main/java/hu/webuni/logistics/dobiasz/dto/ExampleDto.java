package hu.webuni.logistics.dobiasz.dto;


public class ExampleDto {
	
	private Long id;
	private String countryIsoCode;
	private String city;
	private String street;
	private String postCode;
	private String houseNumber;
	private String width;
	private String length;
	
	public ExampleDto() {
		
	}

	public ExampleDto(Long id, String countryIsoCode, String city, String street, String postCode, String houseNumber,
			String width, String length) {
		super();
		this.id = id;
		this.countryIsoCode = countryIsoCode;
		this.city = city;
		this.street = street;
		this.postCode = postCode;
		this.houseNumber = houseNumber;
		this.width = width;
		this.length = length;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCountryIsoCode() {
		return countryIsoCode;
	}

	public void setCountryIsoCode(String countryIsoCode) {
		this.countryIsoCode = countryIsoCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getHouseNumber() {
		return houseNumber;
	}

	public void setHouseNumber(String houseNumber) {
		this.houseNumber = houseNumber;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}
	

}
