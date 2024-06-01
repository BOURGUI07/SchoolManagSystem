/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author lenovo
 */
public class Course implements Comparable<Course>{

  
  private String code;
  private String title;
  private String description;
  private int seatLimit;
  private int numberOfEnrolledStudents;
  private List<Student> enrolledStudents;
  private List<Assignement> assignements;

  public Course(String code, String title, String description, int seatLimit) {
    if(seatLimit<=0){
      throw new IllegalArgumentException("The seat limit can't be either 0 or less than 0!");
    }
    this.code = code;
    this.title = title;
    this.description = description;
    this.seatLimit = seatLimit;
    this.numberOfEnrolledStudents=0;
    this.enrolledStudents = new ArrayList<>();
    this.assignements=new ArrayList<>();
  }
  
  public void occupySeat(){
    if(this.isAvailable()){
      this.numberOfEnrolledStudents++;
    }else{
      throw new IllegalArgumentException("The class has reached its seat limit. Can't handle more students right now!");
    }
  }
  
  public void leaveSeat(){
    if(this.numberOfEnrolledStudents>0){
      this.numberOfEnrolledStudents--;
    }else{
      throw new IllegalArgumentException("No students to leave in the first place");
    }
  }
  
  public int currentOccupiedSeats(){
    return this.numberOfEnrolledStudents;
  }
  
  public List<Student> getEnrolledStudents() {
    return enrolledStudents;
  }
  
  public List<Assignement> getAssignements(){
    return this.assignements;
  }
  
  public void addAssignement(Assignement assign){
    this.assignements.add(assign);
  }

  public String getCode() {
    return code;
  }

  public String getTitle() {
    return title;
  }

  public String getDescription() {
    return description;
  }

  public int getSeatLimit() {
    return this.seatLimit;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setSeatLimit(int seats) {
    this.seatLimit = seats;
  }
  
  public int numberOfAvailableSeats(){
    return this.seatLimit - this.numberOfEnrolledStudents;
  }
  
  public boolean isAvailable(){
    return this.numberOfAvailableSeats()>0;
  }
  
  @Override
  public int hashCode() {
    int hash = 7;
    hash = 89 * hash + Objects.hashCode(this.code);
    return hash;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final Course other = (Course) obj;
    return Objects.equals(this.code, other.code);
  }
  
  @Override
  public String toString(){
    return "Course Code: " + this.code + "\n" +
            "Title: " + this.title + "\n" +
                    "Description: " + this.description + "\n";
                            
  }
  
  @Override
  public int compareTo(Course course){
    return course.currentOccupiedSeats() - this.numberOfEnrolledStudents;
  }
}
