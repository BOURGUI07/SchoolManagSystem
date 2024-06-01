/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 *
 * @author lenovo
 */
public class Student extends Registrable{
  private List<Course> currentCourses;
  private Map<Course, List<Double>> courseGrades; 
  private List<Assignement> assignments;
  private List<UUID> requestIDs;
  private List<String> announcements;
  private Map<Course, Integer> absenseMap;
  
  public Student(String name, LocalDate birthdate, Gender gender, String email,String phone) {
    super(name, birthdate, gender, email, phone);
    this.currentCourses = new ArrayList<>();
    this.courseGrades = new HashMap<>();
    this.assignments = new ArrayList<>();
    this.requestIDs = new ArrayList<>();
    this.announcements = new ArrayList<>();
    this.absenseMap=new HashMap<>();
  }
  
  public void addAbsence(String courseCode){
    Course course = this.getManag().getCourseForCode(courseCode);
    if(this.absenseMap.containsKey(course)){
      int number = this.absenseMap.get(course);
      number++;
      this.absenseMap.put(course, number);
    }else{
      this.absenseMap.put(course, 1);
    }
  }
  
  public int getNumberOfAbsenceForCourse(Course course){
    return this.absenseMap.get(course);
  }
  
  public List<String> getAnnouncements(){
    return this.announcements;
  }
  
  public void addAnnoucement(String announce){
    this.announcements.add(announce);
  }
  
  public List<UUID> getRequestIdList(){
    return this.requestIDs;
  }
  
  public List<Assignement> getAssignements(){
    return this.assignments;
  }
  
  @Override
  public RegistrableType getType(){
    return RegistrableType.STUDENT;
  }
  
  public Map<Course, List<Double>> getCourseGrades(){
    return this.courseGrades;
  }
  
  public List<Course> getCurrentCourses(){
    return this.currentCourses;
  }
  
  public void enroll_In_Course(String courseCode){
    Course course = this.getManag().getCourseForCode(courseCode);
    StudentRequest enroll = new Enroll(this, course);
    this.requestIDs.add(enroll.getRequestID());
    this.getManag().getEnrollementRequests().add((Enroll) enroll);
  }
  
  
  public void dropCourse(String courseCode){
    Course course = this.getManag().getCourseForCode(courseCode);
    StudentRequest drop = new Drop(this, course);
    this.requestIDs.add(drop.getRequestID());
    this.getManag().getDropRequests().add((Drop) drop);
  }
  
  public void addGrade(Course course, double grade){
    if(this.courseGrades.containsKey(course)){
      this.courseGrades.get(course).add(grade);
    }else{
      this.courseGrades.putIfAbsent(course, new ArrayList<>());
      this.courseGrades.get(course).add(grade);
    }
  }
  
  @Override
  public String toString(){
    return this.getName() + "\n" + 
            "ID: " + this.getId() + "\n" + 
            "Birthdate: " + this.getBirthdate() + "\n" + 
            this.getGender() + "\n" + 
            "Email: " + this.getEmail() + "\n" + 
            "Phone: " + this.getPhone() + "\n";
  }
  
  public double getAverageGradeForCourse(Course course){
    double average = -1;
    if(this.courseGrades.containsKey(course)){
      List<Double> grades = this.courseGrades.get(course);
       if(!grades.isEmpty()){
         average = grades.stream().mapToDouble(Double::doubleValue).average().orElse(-1);
       }
    }
    return average;
  }
  
  public double getTotalGrade(){
    double totalAvg = 0;
    Set<Course> set = this.courseGrades.keySet();
    Iterator<Course> itr = set.iterator();
    while(itr.hasNext()){
      Course course = itr.next();
      double average = this.getAverageGradeForCourse(course);
      if(average!=(-1)){
        totalAvg+=average;
      }
    }
    return totalAvg;
  }
  
  public void viewGradeForEachCourse(){
    String a = """
               Course name\tCourseGrade
               """;
    for(Course course:this.currentCourses){
      a+= course.getCode()+ "\t" + this.getAverageGradeForCourse(course) + "\n";
    }
    System.out.println(a);
  }
  
  @Override
  public int compareTo(Registrable reg){
    Student student = (Student) reg;
    return (int) (student.getTotalGrade() - this.getTotalGrade());
  }


  
}
