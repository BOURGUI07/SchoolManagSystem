/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domain;

/**
 *
 * @author lenovo
 */
public class Drop extends StudentRequest{
  
  public Drop(Student student, Course course){
    super(student, course);
  }
  
  @Override
  public RequestSubject getSubject(){
    return RequestSubject.DROP;
  }
  
  @Override
  public void acceptRequest(){
    super.requestAccepted();
    super.getCourse().getEnrolledStudents().remove(super.getStudent());
    super.getCourse().leaveSeat();
    super.getStudent().getCurrentCourses().remove(super.getCourse());
  }
  
  @Override
  public String toString(){
    return "Enroll request id: " + this.getRequestID() + "\n" +
            super.getStudent().getName() + "Wants to Drop this: " + super.getCourse().getCode();
  }
}
