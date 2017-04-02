package com.example.saisi.agni;

/**
 * Created by saisi on 25-Mar-17.
 */

public class CourseDetails {
    private String courseName;
    private String courseDetails;
    private String url;

    public CourseDetails(){

    }

    public CourseDetails(String courseName, String courseDetails ,String url) {
        this.courseName = courseName;
        this.courseDetails = courseDetails;
        this.url=url;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getCourseDetails() {
        return courseDetails;
    }


    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setCourseDetails(String courseDetails) {
        this.courseDetails = courseDetails;
    }

    public String getUrl() {
       return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
