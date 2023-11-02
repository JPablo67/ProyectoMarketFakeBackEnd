package com.Alejandro.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name ="suppliers")
public class Supplier {

	@Id
	@Column(name = "idSupplier")
	private long idSupplier;
	
	@Column(name = "nameSupplier")
	private String nameSupplier;
	
	@Column(name = "urlSupplier")
	private String urlSupplier;
	
	@Column(name = "emailSupplier")
	private String emailSupplier;
	

	@Column(name = "phoneNumberSupplier")
	private String phoneNumberSupplier;
	
	@Column(name = "addressSupplier")
	private String addressSupplier;

	
	
	public Supplier() {
		
	}

	public long getIdSupplier() {
		return idSupplier;
	}

	public void setIdSupplier(long idSupplier) {
		this.idSupplier = idSupplier;
	}

	public String getNameSupplier() {
		return nameSupplier;
	}

	public void setNameSupplier(String nameSupplier) {
		this.nameSupplier = nameSupplier;
	}

	public String getUrlSupplier() {
		return urlSupplier;
	}

	public void setUrlSupplier(String urlSupplier) {
		this.urlSupplier = urlSupplier;
	}

	public String getEmailSupplier() {
		return emailSupplier;
	}

	public void setEmailSupplier(String emailSupplier) {
		this.emailSupplier = emailSupplier;
	}

	public String getPhoneNumberSupplier() {
		return phoneNumberSupplier;
	}

	public void setPhoneNumberSupplier(String phoneNumberSupplier) {
		this.phoneNumberSupplier = phoneNumberSupplier;
	}

	public String getAddressSupplier() {
		return addressSupplier;
	}

	public void setAddressSupplier(String addressSupplier) {
		this.addressSupplier = addressSupplier;
	}
	
	


}
