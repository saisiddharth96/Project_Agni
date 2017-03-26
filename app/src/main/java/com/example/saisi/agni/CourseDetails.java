package com.example.saisi.agni;

/**
 * Created by saisi on 25-Mar-17.
 */

public class CourseDetails {
    private String courseName;
    private String courseDetails;

    public CourseDetails(String courseName, String courseDetails) {
        this.courseName = courseName;
        this.courseDetails = courseDetails;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getCourseDetails() {
        return courseDetails;
    }

}
