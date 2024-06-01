/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import logic.GradeStudent;

/**
 *
 * @author lenovo
 */
public class Teacher extends Registrable{
  private List<Course> courses;
  private Degree degree;
  
  public Teacher(String name, LocalDate birthdate, Gender gender, String email, String phone, Degree degree){
    super(name, birthdate, gender, email, phone);
    this.degree=degree;
    this.courses= new ArrayList<>();
  }
  
  public List<Course> getCourses(){
    return this.courses;
  }
  
  public int getNumberOfAssignedCourses(){
    return this.courses.size();
  }
  
  public void removeCourse(Course course){
    if(this.courses.contains(course)){
      this.courses.remove(course);
    }
  }
  
  public void addCourse(Course course){
    if(this.getManag().getCourses().contains(course)){
      this.courses.add(course);
    }
  }
  
  @Override
  public RegistrableType getType(){
    return RegistrableType.TEACHER;
  }
  
  public Degree getDegree(){
    return this.degree;
  }
  
  public void markAbsence(String courseCode, String studentName, LocalDate date){
    Course course = this.getManag().getCourseForCode(courseCode);
    Student student = this.getManag().getStudentForName(studentName);
    student.addAbsence(courseCode);
    String ann = this.sendAbsenceAnnoucement(student, courseCode, date);
    student.addAnnoucement(ann);
  }
  
  public String sendAbsenceAnnoucement(Student student, String courseCode, LocalDate date){
    return "Dear, " + student.getEmail() + "\n" + 
            "You've been marked as ABSENT in Course: " + courseCode + " On date: " + date;
  }
  
  public void extendAssignementDeadline(String code, String title, LocalDate newDeadline){
    Assignement assign = this.getManag().getAssignement(code, title);
    if(assign!=null && this.canTeacherDealWithCourse(assign.getCourse())){
      if(this.isNewDeadlineValid(assign.getDeadline(), newDeadline)){
        assign.setDeadline(newDeadline);
        assign.extendDeadline();
        this.sendAnnoucementToStudents(assign, code, title, newDeadline);
      }else{
        throw new IllegalArgumentException("The new deadline isn't after the old deadline!");
      }
    }else{
      throw new IllegalArgumentException("Either the course is null or the course isn't among the courses you teach!");
    }
  }
  
  public void sendAnnoucementToStudents(Assignement assign, String courseCode, String title, LocalDate newDeadline){
    String ann = this.extendAnnoucement(courseCode, title, newDeadline);
    for(Student student:assign.getAssignedStudents()){
      student.addAnnoucement(ann);
    }
  }
  
  public String extendAnnoucement(String courseCode, String title, LocalDate newDeadline){
    return "Course: " + courseCode + "\n" + 
            "Annoucement: The deadline for " + title + " has been extended to " + newDeadline + ".";
  }
  
  public boolean isNewDeadlineValid(LocalDate oldDeadline, LocalDate newDeadline){
    return (newDeadline.isAfter(oldDeadline));
  }
  
  public void assignCourse(String courseCode, String title, LocalDate deadline, String description){
    Course course = this.getManag().getCourseForCode(courseCode);
    if(this.courses.contains(course)){
      this.getManag().addAssignement(this, courseCode, title, deadline, description);
    }else{
      throw new IllegalArgumentException("You can't assign a course you're not assigned to!");
    }
  }
  
  public void assignStudent(String courseCode, String title, String studentID){
    Assignement assign = this.getManag().getAssignement(courseCode, title);
    Registrable reg = this.getManag().getRegistrableForID(studentID);
    Student student = null;
    if(reg.getType()==RegistrableType.STUDENT){
       student = (Student) reg;
       if(student.getCurrentCourses().contains(assign.getCourse()) && assign.getCourse().getEnrolledStudents().contains(student)){
          assign.getAssignedStudents().add(student);
          student.getAssignements().add(assign);
       }else{
        throw new IllegalArgumentException("You cannot assign a course to a student that isn't even enrolled in the course!");
       }
    }
    
  }
  
  public void gradeStudent(String studentID, String courseCode, String assignementTitle, double grade){
    Registrable reg = this.getManag().getRegistrableForID(studentID);
    if(reg==null){
      throw new IllegalArgumentException("No Student as found for the ID!");
    }
    Student student;
    if(reg.getType()==RegistrableType.STUDENT){
       student = (Student) reg;
       Assignement assign = this.getManag().getAssignement(courseCode, assignementTitle);
       if(assign.getAssignedStudents().contains(student) && student.getCurrentCourses().contains(assign.getCourse()) && assign.getCourse().getEnrolledStudents().contains(student)){
          GradeStudent gradeRequest = new GradeStudent(this, student, assign, grade);
          this.getManag().getGradingRequests().add(gradeRequest);
       }else{
          throw new IllegalArgumentException("The student isn't even enrolled in course!");
       }
    }
  }
  
  public boolean canTeacherDealWithCourse(Course course){
    return this.courses.contains(course);
  }

  @Override
  public int compareTo(Registrable reg) {
    Teacher teacher = (Teacher) reg;
    return teacher.getNumberOfAssignedCourses() - this.getNumberOfAssignedCourses();
  }
  
  @Override
  public String toString(){
    return this.getName() + "\n" +
            "ID: " + this.getId() + "\n" + 
            "Birthdate: " + this.getBirthdate() + "\n" +
            "Gender: " + this.getGender() + "\n" + 
            "Degree: " + this.getDegree() + "\n" + 
            "Email: " + this.getEmail() + "\n" + 
            "Phone: " + this.getPhone();
  }
  
  
}
