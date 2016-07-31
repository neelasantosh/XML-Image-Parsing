package com.example.assignmentxmlimage;

public class ImageData {

	String ImageTitle;
	String ImageUrl;
	String ImageFilename;
	public String getImageTitle() {
		return ImageTitle;
	}
	public void setImageTitle(String imageTitle) {
		ImageTitle = imageTitle;
	}
	public String getImageUrl() {
		return ImageUrl;
	}
	public void setImageUrl(String imageUrl) {
		ImageUrl = imageUrl;
	}
	public String getImageFilename() {
		return ImageFilename;
	}
	public void setImageFilename(String imageFilename) {
		ImageFilename = imageFilename;
	}
	public ImageData(){}
	
	public ImageData(String imageTitle, String imageUrl, String imageFilename) {
		super();
		ImageTitle = imageTitle;
		ImageUrl = imageUrl;
		ImageFilename = imageFilename;
	}
	@Override
	public String toString() {
		return "ImageData [ImageTitle=" + ImageTitle + ", ImageUrl=" + ImageUrl
				+ ", ImageFilename=" + ImageFilename + "]";
	}
	
	
}
