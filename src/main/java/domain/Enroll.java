/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domain;

/**
 *
 * @author lenovo
 */
public class Enroll extends StudentRequest{
  
  public Enroll(Student student, Course course){
    super(student, course);
  }
  
  @Override
  public void acceptRequest(){
    super.requestAccepted();
    super.getCourse().getEnrolledStudents().add(super.getStudent());
    super.getCourse().occupySeat();
    super.getStudent().getCurrentCourses().add(super.getCourse());
  }
 
  @Override
  public RequestSubject getSubject(){
    return RequestSubject.ENROLLEMENT;
  }
  
  @Override
  public String toString(){
    return "Enroll request id: " + this.getRequestID() + "\n" +
            super.getStudent().getName() + "Wants to Enroll in: " + super.getCourse().getCode();
  }
  
}
