package com.Alejandro.models;

public class Image {
	String url;
	

	public Image(String url) {
		super();
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "Image [url=" + url + "]";
	}
	
}
