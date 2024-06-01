/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domain;

import java.util.UUID;

/**
 *
 * @author lenovo
 */
public abstract class StudentRequest {
  private Student student;
  private Course course;
  private boolean isRequestAccpeted; 
  private UUID requestID;
  
  public StudentRequest(Student student, Course course){
    this.course=course;
    this.student=student;
    this.isRequestAccpeted = false;
    this.requestID=UUID.randomUUID();
  }
  
  public abstract RequestSubject getSubject();
  
  public UUID getRequestID(){
    return this.requestID;
  }
  
  public Student getStudent(){
    return this.student;
  }
  
  public Course getCourse(){
    return this.course;
  }
  
  public boolean requestStatus(){
    return this.isRequestAccpeted;
  }
  
  public void requestAccepted(){
    this.isRequestAccpeted=true;
  }
  
  public abstract void acceptRequest();
  
  public  void denyRequest(){
    this.isRequestAccpeted = false;
  }
}
