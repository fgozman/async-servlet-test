package ro.gfl.asynctest;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Customer {
	@JsonProperty("n")
	public String name;
	@JsonProperty("a")
	public Integer age;

	public Address address;
	public Customer() {

	}

	public Customer(String name, Integer age,Address address) {
		this.name = name;
		this.age = age;
		this.address = address;
	}

}
