/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logic;

import domain.Assignement;
import domain.Course;
import domain.Degree;
import domain.Drop;
import domain.Enroll;
import domain.Gender;
import domain.Registrable;
import domain.RegistrableType;
import domain.RequestSubject;
import domain.StudentRequest;
import domain.Student;
import domain.Teacher;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 *
 * @author lenovo
 */
public class SchoolManagement {
  private List<Course> courses;
  private Set<String> codes;
  private List<Registrable> students;
  private List<Teacher> teachers;
  private List<StudentRequest> enrollRequests;
  private List<StudentRequest> dropRequests;
  private List<Assignement> assignements;
  private List<GradeStudent> gradeRequests;
  private Map<Teacher, List<Course>> map;
  private List<UUID> checkedRequests;
  
  public SchoolManagement(){
    this.courses = new ArrayList<>();
    this.students = new ArrayList<>();
    this.teachers = new ArrayList<>();
    this.assignements = new ArrayList<>();
    this.gradeRequests = new ArrayList<>();
    this.dropRequests= new ArrayList<>();
    this.enrollRequests = new ArrayList<>();
    this.map=new HashMap<>();
    this.codes=new HashSet<>();
    this.checkedRequests=new ArrayList<>();
  }
  
  public Map<Teacher, List<Course>> getMap(){
    return this.map;
  }
  
  public List<Course> getCourses() {
    return courses;
  }

  public List<Registrable> getStudents() {
    return students;
  }

  public List<Teacher> getTeachers() {
    return teachers;
  }
  
  public List<Assignement> getAssignements(){
    return this.assignements;
  }
  
  public List<UUID> getCheckedRequests(){
    return this.checkedRequests;
  }
  
  public void addCourse(String code, String title, String description, int seats){
    Course course = new Course(code, title, description, seats);
    if(!this.courses.contains(course)){
      this.courses.add(course);
      this.codes.add(code);
      if(codes.size()!=this.courses.size()){
        throw new IllegalArgumentException("The size of list of codes doesn't equal the size of course list!");
      }
    }else{
      throw new IllegalArgumentException("The course: " + code + " already exists!");
    }
  }
  
  public void addStudent(String name, LocalDate birthdate, Gender gender, String email, String phone){
    Registrable student = (Student) new Student(name, birthdate, gender, email, phone);
    if(!this.students.contains(student)){
      this.students.add(student);
    }
  }
  
  public Teacher getTeacherforName(String name){
    if(name==null){
      throw new IllegalArgumentException("Name can't be null");
    }else{
      for(Teacher teacher:this.teachers){
        if(teacher.getName().equals(name)){
          return teacher;
        }
      }
    }
    return null;
  }
  
  public void removeCourse(String courseCode){
    if(courseCode==null){
      throw new IllegalArgumentException("The course code you entered is null!");
    }
    Course course = this.getCourseForCode(courseCode);
    if(course==null){
      throw new IllegalArgumentException("No course was found for the courseCode: " + courseCode);
    }
    if(this.getCourses().contains(course)){
      if(course.getEnrolledStudents().isEmpty()){
        this.getCourses().remove(course);
        this.codes.remove(courseCode);
        for(Teacher teacher:this.teachers){
          if(teacher.getCourses().contains(course)){
           this.unassignCourseFromTeacher(teacher.getId().toString(), courseCode);
          }
        } 
      }else{
        throw new IllegalArgumentException("Some students are enrolled in the course, you can't remove it!");
      }      
    }else{
      throw new IllegalArgumentException("The course list doesn't even include the course whose code is: " + courseCode + " !!!");
    }
  }
 
  public void addTeacher(String name, LocalDate birthdate, Gender gender, String email, String phone, Degree degree){
    Teacher teacher = new Teacher(name, birthdate, gender, email, phone, degree);
    if(!this.teachers.contains(teacher)){
      this.teachers.add(teacher);
      this.map.put(teacher, new ArrayList<>());
    }else{
      throw new IllegalArgumentException("Teacher is already in the list!!");
    }
  }
  
  public void addAssignement(Teacher teacher, String courseCode, String assignementTitle, LocalDate deadline, String description){
    Course course  = this.getCourseForCode(courseCode);
    Assignement assign = new Assignement(teacher, course, assignementTitle, deadline, description);
    if(course.getAssignements().contains(assign)){
      throw new IllegalArgumentException("The assignement is already in the course!");
    }else{
      course.addAssignement(assign);
    }
    if(!this.assignements.contains(assign)){
      this.assignements.add(assign);
    }
  }
  
  public Course getCourseForCode(String code){
    if(code==null){
      throw new IllegalArgumentException("Course code can't be null!");
    }
    for(Course course:this.courses){
      if(course.getCode().equals(code)){
        return course;
      }
    }
    return null;
  }
  
  public Registrable getRegistrableForID(String idString){
    if(!this.isValidUUID(idString)){
      throw new IllegalArgumentException("Id is not valid");
    }
    UUID id = UUID.fromString(idString);
    for(Registrable reg:this.students){
      if(reg.getId().equals(id)){
        return reg;
      }
    }
    return null;
  }
  
  public Teacher getTeacherForID(String idString){
    if(!this.isValidUUID(idString)){
      throw new IllegalArgumentException("Id is not valid");
    }
    UUID id = UUID.fromString(idString);
    for(Teacher teacher:this.teachers){
      if(teacher.getId().equals(id)){
        return teacher;
      }
    }
    return null;
  }
  
  public void assignCourseToTeacher(String teacherId, String courseCode){
    Teacher teacher = this.getTeacherForID(teacherId);
    Course course = this.getCourseForCode(courseCode);
    if(teacher!= null){
      teacher.addCourse(course);
      if(this.map.containsKey(teacher)){
        this.map.get(teacher).add(course);
      }
    }else{
      throw new IllegalArgumentException("Teacher not found for ID: " + teacherId);
    }
    
  }
  
  public void unassignCourseFromTeacher(String teacherId, String courseCode){
    Teacher teacher = this.getTeacherForID(teacherId);
    Course course = this.getCourseForCode(courseCode);
    teacher.removeCourse(course);
    this.map.get(teacher).remove(course);
  }
  
  public Assignement getAssignement(String courseCode, String assignementTitle){
    Course course = this.getCourseForCode(courseCode);
    for(Assignement assign:this.assignements){
      if(assign.getCourse().equals(course) && assign.getTitle().equals(assignementTitle)){
        return assign;
      }
    }
    return null;
  }
  
  public List<GradeStudent> getGradingRequests(){
    return this.gradeRequests;
  }
  
  public List<StudentRequest> getEnrollementRequests(){
    return this.enrollRequests;
  }
  
  public List<StudentRequest> getDropRequests(){
    return this.dropRequests;
  }
  
  public GradeStudent getGradingRequestForID(String requestID){
    if(!this.isValidUUID(requestID)){
      throw new IllegalArgumentException("Id is not valid");
    }
    UUID id = UUID.fromString(requestID);
    Iterator<GradeStudent> itr = this.gradeRequests.iterator();
    while(itr.hasNext()){
      GradeStudent request = itr.next();
      if(request.getRequestID().equals(id)){
        return request;
      }
    }
    return null;
  }
  
  public void confirmGradingRequest(String requestID){
    GradeStudent request = this.getGradingRequestForID(requestID);
    Student student = request.getStudent();
    Assignement assign = request.getAssignement();
    Course course = assign.getCourse();
    double grade = request.getGrade();
    student.addGrade(course, grade);
    assign.getAssignedStudents().remove(student);
    this.checkedRequests.add(request.getRequestID());
    this.gradeRequests.remove(request);
  }
  
  public StudentRequest getEnrollRequestForID(String requestID){
    if(!this.isValidUUID(requestID)){
      throw new IllegalArgumentException("Id is not valid");
    }
    UUID id = UUID.fromString(requestID);
    Iterator<StudentRequest> itr = this.enrollRequests.iterator();
    while(itr.hasNext()){
      StudentRequest request = itr.next();
      if(request.getRequestID().equals(id)){
        return request;
      }
    }
    return null;
  }
  
  public void confirmEnrollRequest(String requestID){
    StudentRequest request = this.getEnrollRequestForID(requestID);
    if(request.getSubject()==RequestSubject.ENROLLEMENT){
      Enroll enroll = (Enroll) request;
      if(enroll.getCourse().isAvailable()){
        enroll.acceptRequest();
        this.checkedRequests.add(request.getRequestID());
        this.enrollRequests.remove(enroll);
      }else{
        this.checkedRequests.add(request.getRequestID());
        enroll.denyRequest();
      }
    }
  }
  
  public StudentRequest getDropRequestForID(String requestID){
    if(!this.isValidUUID(requestID)){
      throw new IllegalArgumentException("Id is not valid");
    }
    UUID id = UUID.fromString(requestID);
    Iterator<StudentRequest> itr = this.dropRequests.iterator();
    while(itr.hasNext()){
      StudentRequest request = itr.next();
      if(request.getRequestID().equals(id)){
        return request;
      }
    }
    return null;
  }
  
  public void confirmDropRequest(String requestID){
    StudentRequest request = this.getDropRequestForID(requestID);
    if(request==null){
      throw new IllegalArgumentException("No request found for the id!");
    }else{
      if(request.getSubject()==RequestSubject.DROP){
        Drop drop = (Drop) request;
        drop.acceptRequest();
        this.checkedRequests.add(request.getRequestID());
        this.dropRequests.remove(request);
      }else{
        throw new IllegalArgumentException("This is not a drop request!");
      }
    }
    
  }
  
  public boolean isValidUUID(String uuidString) {
    String uuidPattern = 
            "^\\h*\\b[0-9a-fA-F]{8}\\b-\\b[0-9a-fA-F]{4}\\b-\\b[0-9a-fA-F]{4}\\b-\\b[0-9a-fA-F]{4}\\b-\\b[0-9a-fA-F]{12}\\b\\h*$";
    return Pattern.matches(uuidPattern, uuidString);
  }
  
  public void sortStudentsByTotalGrade(){
    Collections.sort(this.students);
    String a = "Student Name\tStudent ID\tStudent Grade +\n"; 
    for(Registrable reg:this.students){
      Student student = (Student) reg;
      a+= student.getName() + "\t" + student.getId() + "\t" + student.getTotalGrade() + "\n";
    }
    System.out.println(a);
  }
  
  public void sortCoursesByNumberOfStudents(){
    Collections.sort(this.courses);
    String a = "Course name\tNumber of occupied seats\n";
    for(Course course:this.courses){
      a+= course.getCode() + "\t" + course.currentOccupiedSeats() + "\n";
    }
    System.out.println(a);
  }
  
  public void sortTeachersByNumberOfAssignedCourses(){
    Collections.sort(this.teachers);
    String a = "Teacher Name\tTeacher ID\tNumber Of Courses\n";
    for(Teacher teacher:this.teachers){
      a+= teacher.getName() + "\t" + teacher.getId() + "\t" + teacher.getNumberOfAssignedCourses() + "\n";
    }
    System.out.println(a);
  }
  
  public Set<String> getCodes(){
    return this.codes;
  }
  
  public Student getStudentForName(String name){
    for(Registrable reg:this.students){
      if(reg.getName().equals(name) && reg.getType()==RegistrableType.STUDENT){
        return (Student) reg;
      }
    }
    return null;
  }
}
