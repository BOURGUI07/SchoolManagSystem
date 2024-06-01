/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author lenovo
 */
public class Assignement {
  private Teacher teacher;
  private Course course;
  private String title;
  private LocalDate deadline;
  private String description;
  private List<Student> assignedStudents;
  private boolean isAssignementExtended;
  
  public Assignement(Teacher teacher, Course course, String title, LocalDate deadline, String description){
    this.course=course;
    this.deadline=deadline;
    this.title=title;
    this.description = description;
    this.assignedStudents= new ArrayList<>();
    this.teacher=teacher;
    this.isAssignementExtended=false;
  }
  
  public boolean isAssignementExtended(){
    return this.isAssignementExtended;
  }
  
  public void extendDeadline(){
    this.isAssignementExtended=true;
  }
  
  public List<Student> getAssignedStudents(){
    return this.assignedStudents;
  }

  public Course getCourse() {
    return course;
  }

  public String getTitle() {
    return title;
  }

  public LocalDate getDeadline() {
    return deadline;
  }

  public String getDescription() {
    return description;
  }
  
  public Teacher getTeacher(){
    return this.teacher;
  }

  public void setCourse(Course course) {
    this.course = course;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setDeadline(LocalDate deadline) {
    this.deadline = deadline;
  }

  public void setDescription(String description) {
    this.description = description;
  }
  
  @Override
  public String toString(){
    return "Course: " + this.course + "\n"+
            "Title: " + this.title + "\n" +
            "Deadline: " + this.deadline + "\n"+
            "Description: " + this.description + "\n";
  }
  
  
  @Override
  public int hashCode() {
    int hash = 3;
    hash = 97 * hash + Objects.hashCode(this.course);
    hash = 97 * hash + Objects.hashCode(this.title);
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
    final Assignement other = (Assignement) obj;
    if (!Objects.equals(this.title, other.title)) {
      return false;
    }
    return Objects.equals(this.course, other.course);
  }
}
