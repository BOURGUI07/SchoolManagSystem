/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logic;

import domain.Assignement;
import domain.Student;
import domain.Teacher;
import java.util.Objects;
import java.util.UUID;

/**
 *
 * @author lenovo
 */
public class GradeStudent {
  private Teacher teacher;
  private Student student;
  private Assignement assignement;
  private double grade;
  private UUID requestID;
  
  public GradeStudent(Teacher teacher, Student student, Assignement assignement, double grade){
    if(teacher==null){
      throw new IllegalArgumentException("Teacher can't be null!");
    }
    if(student==null){
      throw new IllegalArgumentException("Student can't be null!");
    }
    if(assignement==null){
      throw new IllegalArgumentException("Assignement can't be null!");
    }
    
    if(grade<0){
      throw new IllegalArgumentException("Grade can't be negative!");
    }
    if(grade>20){
      throw new IllegalArgumentException("Grade can't go above 20!");
    }
    
    this.teacher=teacher;
    this.student = student;
    this.assignement=assignement;
    this.grade= grade;
    this.requestID=UUID.randomUUID();
  }
  
  public UUID getRequestID(){
    return this.requestID;
  }
  
  
  public Teacher getTeacher() {
    return teacher;
  }

  public Student getStudent() {
    return student;
  }

  public Assignement getAssignement() {
    return assignement;
  }

  public double getGrade() {
    return grade;
  }
  
  @Override
  public String toString(){
    return "Grading Request id: " + this.requestID + "\n"+
            this.teacher.getName() + "\n" +
            this.assignement.getCourse().getCode() + "\n" + 
            this.assignement.getTitle() + "\n" + 
            this.student.getName() + "\n" + 
            this.grade + "/20";
  }
  
  @Override
  public int hashCode() {
    int hash = 7;
    hash = 37 * hash + Objects.hashCode(this.teacher);
    hash = 37 * hash + Objects.hashCode(this.student);
    hash = 37 * hash + Objects.hashCode(this.assignement);
    hash = 37 * hash + (int) (Double.doubleToLongBits(this.grade) ^ (Double.doubleToLongBits(this.grade) >>> 32));
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
    final GradeStudent other = (GradeStudent) obj;
    if (Double.doubleToLongBits(this.grade) != Double.doubleToLongBits(other.grade)) {
      return false;
    }
    if (!Objects.equals(this.teacher, other.teacher)) {
      return false;
    }
    if (!Objects.equals(this.student, other.student)) {
      return false;
    }
    return Objects.equals(this.assignement, other.assignement);
  }
}
