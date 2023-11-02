package com.Alejandro.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity(name ="reports")
public class Report {
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idReport")
	private long idReport;
	
	@Column(name = "reportType")
	private String reportType;
	
	@Column(name = "description")
	private String description;
	
	 @ManyToOne
	 @JoinColumn(name = "idEmployee")
	 private User employee;

		public Report(long idReport, String reportType, String description, User employee) {
			super();
			this.idReport = idReport;
			this.reportType = reportType;
			this.description = description;
			this.employee = employee;
		}

		public Report() {
			super();
			// TODO Auto-generated constructor stub
		}

		public long getIdReport() {
			return idReport;
		}

		public void setIdReport(long idReport) {
			this.idReport = idReport;
		}

		public String getReportType() {
			return reportType;
		}

		public void setReportType(String reportType) {
			this.reportType = reportType;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public User getEmployee() {
			return employee;
		}

		public void setEmployee(User employee) {
			this.employee = employee;
		}

		
		

	    
}