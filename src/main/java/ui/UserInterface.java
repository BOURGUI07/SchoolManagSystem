/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui;

import domain.*;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import logic.AdminManag;
import logic.GradeStudent;
import logic.SchoolManagement;

/**
 *
 * @author lenovo
 */
public class UserInterface {
  private Scanner scanner;
  private SchoolManagement manag;
  private AdminManag adma;
  private static final String EMAIL_PATTERN = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
  
  public UserInterface(Scanner scanner){
    this.scanner=scanner;
    this.manag = new SchoolManagement();
    this.adma= new AdminManag();
  }
  
  public void start(){
    while(true){
      System.out.println("[R]: Register as Admin");
      System.out.println("[A]: Login as Admin");
      System.out.println("[S]: Login as Student");
      System.out.println("[T]: Login as Teacher");
      System.out.println("[X]: Quit");
      String answer = this.scanner.nextLine().toUpperCase();
      if(answer.equals("X")){
        break;
      }
      if(answer.equals("A")){
        this.adminLoginPage();
      }else if(answer.equals("R")){
        this.registerAdminPage();
      }else if(answer.equals("S")){
        System.out.println("Enter Student Id");
        String id = this.scanner.nextLine();
        Student student = (Student) this.manag.getRegistrableForID(id);
        if(student==null){
          System.out.println("You're not even registrated!");
        }else{
          this.studentMenu(student);
        }
      }else{
        System.out.println("Enter teacher id: ");
        String id = this.scanner.nextLine();
        Teacher teacher = this.manag.getTeacherForID(id);
        if(teacher==null){
          System.out.println("You're not even registrated");
        }else{
          this.teacherMenu(teacher);
        }
      }
    }
  }
  
  private void registerAdminPage(){
    System.out.println("Enter User Name: ");
    System.out.println("The username must starts with \"@admin\"");
    String username = this.scanner.nextLine();
    while(!this.adma.isAdminUserNameValid(username)){
      System.out.println("The username isn't valid! Try again");
      username = this.scanner.nextLine();
    }
    System.out.println("Enter password");
    System.out.println("The password must contain at leat 8 charcaters");
    System.out.println("To be considered secure, it must contain uppercase, lowercase, special characters & digits");
    String password = this.scanner.nextLine();
    while(!this.adma.isSecure(password)){
      System.out.println("The password isn 't secure! Try again");
      password = this.scanner.nextLine();
    }
    System.out.println("Confirm password");
    String confirmedPass = this.scanner.nextLine();
    while(!confirmedPass.equals(password)){
      System.out.println("Confirmed Password isn't valid! Try again!");
      confirmedPass = this.scanner.nextLine();
    }
    System.out.println("Enter Email");
    String email = this.scanner.nextLine();
    while(!this.adma.isValidEmail(email)){
      System.out.println("The email isn't valid! Try again");
      email = this.scanner.nextLine();
    }
    Admin admin = new Admin(username, password, email);
    this.adma.registerAdmin(admin);
    if(this.adma.isRegistrated(admin)){
      System.out.println("Successfully Registrated");
      System.out.println("Login to access the admin menu!");
      this.adminLoginPage();
    }else{
      System.out.println("The registration process failed! Try again");
      this.registerAdminPage();
    }
  }
  
  private void adminLoginPage(){
    System.out.println("Enter Username");
    String username = this.scanner.nextLine();
    System.out.println("Enter Password");
    String password = this.scanner.nextLine();
    if(this.adma.canAdminLogin(username, password)){
      this.adminMenu();
    }else{
      System.out.println("It seems that you haven't yet registrated!");
      this.registerAdminPage();
    }
  }
  
  
  
  private void adminMenu(){
    System.out.println("Welcome to The Admin Panel!");
    while(true){
      System.out.println("[C]: Manage Courses");
      System.out.println("[T]: Manage Teachers");
      System.out.println("[S]: Add Students");
      System.out.println("[V]: View Requests");
      System.out.println("[R]: Answer Requests");
      System.out.println("[X]: Quit");
      String answer = this.scanner.nextLine().toUpperCase();
      if(answer.equals("X")){
        break;
      }
      switch(answer){
        case "C" -> this.manageCoursesPage();
        case "T" -> this.manageTeachersPage();
        case "S" -> this.addStudentPage();
        case "V" -> this.viewRequestsPage();
        default -> this.answerRequestsPage();
      }
    }
  }
  
  private void manageCoursesPage(){
    while(true){
      System.out.println("[A]: Add Course");
      System.out.println("[R]: Remove Course");
      System.out.println("[U]: Update Course");
      System.out.println("[C]: View Courses");
      System.out.println("[X]: Return to the admin panel!");
      String answer = this.scanner.nextLine().toUpperCase();
      if("X".equals(answer)){
        break;
      }
      if(answer.isBlank()){
        System.out.println("You wrote nothing! Try again!");
        this.manageCoursesPage();
      }else if("A".equals(answer)){
        this.addCoursePage();
      }else if("R".equals(answer)){
        this.removeCoursePage();
      }else if("U".equals(answer)){
        this.updateCoursePage();
      }else{
        this.viewWhatInsideTheList(this.manag.getCourses());
      }
    }
  }
  
  private void addCoursePage(){
    System.out.println("Enter the course code: ");
    String code = this.scanner.nextLine();
    while(code.isBlank()){
      System.out.println("Course code isn't valid, Try again!");
      code = this.scanner.nextLine();
    }
    System.out.println("Enter the course title: ");
    String title = this.scanner.nextLine();
    while(title.isBlank()){
      System.out.println("Course title isn't valid, try again!");
      title = this.scanner.nextLine();
    }
    System.out.println("Enter the course description");
    String desc = this.scanner.nextLine();
    while(desc.isBlank()){
      System.out.println("Course description");
    }
    System.out.println("Enter the number of seats: ");
    int seats = Integer.parseInt(this.scanner.nextLine());
    while(seats<0){
      System.out.println("Number of seats can't be negative, Try again!");
      seats = Integer.parseInt(this.scanner.nextLine());
    }
    this.manag.addCourse(code, title, desc, seats);
    Course course = new Course(code, title, desc, seats);
    if(this.manag.getCourses().contains(course) && this.manag.getCodes().contains(code)){
      System.out.println("Course was successfully registrated!");
    }else{
      System.out.println("The course already exists");
    }
  }
  
  private void removeCoursePage(){
    System.out.println("Enter the course code: ");
    String code = this.scanner.nextLine();
    while(!this.manag.getCodes().contains(code)){
      System.out.println("The code doesn't exists!");
      code = this.scanner.nextLine();
    }
    Course course = this.manag.getCourseForCode(code);
    if(course==null){
      System.out.println("No course was found for the code: " + code);
      this.removeCoursePage();
    }
    this.manag.removeCourse(code);
    if(this.manag.getCodes().contains(code) || this.manag.getCourses().contains(course)){
      System.out.println("We failed to remove the course!");
      this.removeCoursePage();
    }else{
      System.out.println("Course was successfully removed!");
    }
  }
  
  private void updateCoursePage(){
    System.out.println("Enter the course code: ");
    String code = this.scanner.nextLine();
    while(!this.manag.getCodes().contains(code)){
      System.out.println("The course code you entered doesn't exist! Try again");
      code = this.scanner.nextLine();
    }
    Course course = this.manag.getCourseForCode(code);
    System.out.println("What parameters you would like to change for the course? Empty line will stop!");
    while(true){
      System.out.println("[C]: Change course code");
      System.out.println("[T]: Change course title");
      System.out.println("[D]: Change course description");
      System.out.println("[S]: change the number of seats");
      System.out.println("[X]: Return to manage Courses Panel");
      String answer = this.scanner.nextLine().toUpperCase();
      if(answer.equals("X")){
        break;
      }
      if(answer.equals("C")){
        System.out.println("Enter the new course code: ");
        String newcode = this.scanner.nextLine();
        while(newcode.isBlank()){
          System.out.println("Code was blank. try again");
          newcode = this.scanner.nextLine();
        }
        course.setCode(newcode);
        if(course.getCode().equals(newcode)){
          System.out.println("Code Successfull Updated!");
        }else{
          System.out.println("Failed to update code!");
        }
      }else if(answer.equals("T")){
        System.out.println("Enter the new course title: ");
        String title = this.scanner.nextLine();
        while(title.isBlank()){
          System.out.println("Title was blank. try again");
          title = this.scanner.nextLine();
        }
        course.setTitle(title);
        if(course.getTitle().equals(title)){
          System.out.println("Title Successfull Updated!");
        }else{
          System.out.println("Failed to update title!");
        }
      }else if(answer.equals("D")){
        System.out.println("Enter the new course description: ");
        String desc = this.scanner.nextLine();
        while(desc.isBlank()){
          System.out.println("Description was blank. try again");
          desc = this.scanner.nextLine();
        }
        course.setDescription(desc);
        if(course.getDescription().equals(desc)){
          System.out.println("Description Successfull Updated!");
        }else{
          System.out.println("Failed to update description!");
        }
      }else{
        System.out.println("Enter the new course seat number: ");
        int seats = Integer.parseInt(this.scanner.nextLine());
        while(seats<0){
          System.out.println("Seat number can't be negative!");
          seats = Integer.parseInt(this.scanner.nextLine());
        }
        course.setSeatLimit(seats);
        if(course.getSeatLimit()==seats){
          System.out.println("Seat number Successfull Updated!");
        }else{
          System.out.println("Failed to update seat number!");
        }
      }
    }
  }
  
  
  private void manageTeachersPage(){
    while(true){
      System.out.println("[A]: Add teacher");
      System.out.println("[V]: View Teachers");
      System.out.println("[S]: Assign Course to teacher");
      System.out.println("[U]: UnAssign Course From teacher");
      System.out.println("[X]: Return to admin Panel");
      String answer = this.scanner.nextLine().toUpperCase();
      if(answer.equals("X")){
        break;
      }
      if(answer.equals("A")){
        this.addTeacherPage();
      }else if(answer.equals("S")){
        this.assignTeacherPage();
      }else if(answer.equals("U")){
        this.unassignTeacherPage();
      }else{
        this.viewWhatInsideTheList(this.manag.getTeachers());
      }
    }
  }
  
  private void assignTeacherPage(){
    System.out.println("Enter the teacher ID: ");
    String id = this.scanner.nextLine();
    while(!this.manag.isValidUUID(id)){
      System.out.println("Non-Valid id! Try again");
      id = this.scanner.nextLine();
    }
    System.out.println("Enter the course code");
    String code = this.scanner.nextLine();
    while(!this.manag.getCodes().contains(code)){
      System.out.println("Code doesn't exists. Try again");
      code = this.scanner.nextLine();
    }
    Teacher teacher = this.manag.getTeacherForID(id);
    Course course = this.manag.getCourseForCode(code);
    this.manag.assignCourseToTeacher(id, code);
    if(teacher.getCourses().contains(course)){
      System.out.println("Seccessfully assigned course to teacher");
    }else{
      System.out.println("Failed! Try again!");
      this.assignTeacherPage();
    }
  }
  
  private void unassignTeacherPage(){
    System.out.println("Enter the teacher ID: ");
    String id = this.scanner.nextLine();
    while(!this.manag.isValidUUID(id)){
      System.out.println("Non-Valid id! Try again");
      id = this.scanner.nextLine();
    }
    System.out.println("Enter the course code");
    String code = this.scanner.nextLine();
    while(!this.manag.getCodes().contains(code)){
      System.out.println("Code doesn't exists. Try again");
      code = this.scanner.nextLine();
    }
    Teacher teacher = this.manag.getTeacherForID(id);
    Course course = this.manag.getCourseForCode(code);
    this.manag.assignCourseToTeacher(id, code);
    if(!teacher.getCourses().contains(course)){
      System.out.println("Seccessfully unassigned course to teacher");
    }else{
      System.out.println("Failed! Try again!");
      this.assignTeacherPage();
    }
  }
  
  private void addTeacherPage(){
    System.out.println("Enter the teacher name");
    String name = this.scanner.nextLine();
    System.out.println("Enter the birthdate");
    System.out.println("Year: ");
    int year = Integer.parseInt(this.scanner.nextLine());
    System.out.println("Month: ");
    int month = Integer.parseInt(this.scanner.nextLine());
    System.out.println("Day");
    int day = Integer.parseInt(this.scanner.nextLine());
    LocalDate birthdate = LocalDate.of(year, month, day);
    System.out.println("Enter the gender");
    System.out.println("[M]: Male");
    System.out.println("[F]: Female");
    String answer = this.scanner.nextLine().toUpperCase();
    Gender gender = this.genderMap().get(answer);
    System.out.println("Enter email: ");
    String email = this.scanner.nextLine();
    while(!this.isValidEmail(email)){
      System.out.println("Email isn't valid!");
      email = this.scanner.nextLine();
    }
    System.out.println("Enter phone");
    String phone = this.scanner.nextLine();
    System.out.println("Enter Degree");
    System.out.println("[A]: Associate Degree");
    System.out.println("[B]: Bachelor Degree");
    System.out.println("[M]: Master Degree");
    System.out.println("[P]: PhD");
    String degreeanswer = this.scanner.nextLine().toUpperCase();
    Degree degree = this.degreeMap().get(degreeanswer);
    this.manag.addTeacher(name, birthdate, gender, email, phone, degree);
    Teacher teacher = this.manag.getTeachers().get(manag.getTeachers().size()-1);
    if(this.checkIfAdded(teacher, name, birthdate)){
      System.out.println("Operation succeeded");
    }else{
      System.out.println("Operation Failed");
      this.addTeacherPage();
    }
  }
  
  private boolean checkIfAdded(Registrable reg, String name, LocalDate date){
    return (reg.getName().equals(name) && reg.getBirthdate().equals(date));
  }
  
  private Map<String, Degree> degreeMap(){
    Map<String, Degree> map = new HashMap<>();
    map.put("M", Degree.MASTER);
    map.put("A", Degree.ASSOCIATE);
    map.put("B", Degree.BACHELOR);
    map.put("P", Degree.PHD);
    return map;
  }
  
  private Map<String, Gender> genderMap(){
    Map<String, Gender> map = new HashMap<>();
    map.put("M", Gender.MALE);
    map.put("F", Gender.FEMALE);
    return map;
  }
  
  private void addStudentPage(){
    System.out.println("Enter the student name");
    String name = this.scanner.nextLine();
    System.out.println("Enter the birthyear");
    int year = Integer.parseInt(this.scanner.nextLine());
    while(year<1900 || year>2100){
      System.out.println("Invalid year! Try again!");
      year = Integer.parseInt(this.scanner.nextLine());
    }
    System.out.println("Enter the Month of birth");
    System.out.println("[J]: January");
    System.out.println("[F]: February");
    System.out.println("[M]: March");
    System.out.println("[A]: April");
    System.out.println("[Y]: May");
    System.out.println("[U]: June");
    System.out.println("[L]: July");
    System.out.println("[G]: August");
    System.out.println("[S]: September");
    System.out.println("[O]: October");
    System.out.println("[N]: November");
    System.out.println("[D]: December");
    String a = this.scanner.nextLine().toUpperCase();
    Month month = this.monthMap().get(a);
    while(month==null){
      System.out.println("Invalid Month. Try again");
      a = this.scanner.nextLine().toUpperCase();
      month = this.monthMap().get(a);
    }
    System.out.println("Enter the day of birth");
    int day = Integer.parseInt(this.scanner.nextLine());
    LocalDate birthdate = LocalDate.of(year, month, day);
    System.out.println("Enter the gender");
    System.out.println("[M]: Male");
    System.out.println("[F]: Female");
    String answer = this.scanner.nextLine().toUpperCase();
    Gender gender = this.genderMap().get(answer);
    String email = this.scanner.nextLine();
    while(!this.isValidEmail(email)){
      System.out.println("Email isn't valid!");
      email = this.scanner.nextLine();
    }
    System.out.println("Enter phone");
    String phone = this.scanner.nextLine();
    this.manag.addStudent(name, birthdate, gender, email, phone);
    Registrable student = this.manag.getStudents().get(manag.getStudents().size()-1);
    if(this.checkIfAdded(student, name, birthdate)){
      System.out.println("Operation succeeded");
    }else{
      System.out.println("Operation Failed");
      this.addTeacherPage();
    }
  }
  
  private Map<String, Month> monthMap(){
    Map<String, Month> map = new HashMap<>();
    map.put("J", Month.JANUARY);
    map.put("F", Month.FEBRUARY);
    map.put("M", Month.MARCH);
    map.put("A", Month.APRIL);
    map.put("Y", Month.MAY);
    map.put("U", Month.JUNE);
    map.put("L", Month.JULY);
    map.put("G", Month.AUGUST);
    map.put("S", Month.SEPTEMBER);
    map.put("O", Month.OCTOBER);
    map.put("N", Month.NOVEMBER);
    map.put("D", Month.DECEMBER);
    return map;
  }
  
  private void viewRequestsPage(){
    while(true){
      System.out.println("[E]: View Enrollement Requests");
      System.out.println("[D]: View Drop-Course Requests");
      System.out.println("[G]: View Grading Request");
      System.out.println("[X]: Return to the Admin Panel");
      String answer = this.scanner.nextLine().toUpperCase();
      switch(answer){
        case "E" -> this.viewWhatInsideTheList(this.manag.getEnrollementRequests());
        case "D" -> this.viewWhatInsideTheList(this.manag.getDropRequests());
        case "G" -> this.viewWhatInsideTheList(this.manag.getGradingRequests());
      }
      if(answer.equals("X")){
        break;
      }
    }
  }
  
  private void viewWhatInsideTheList(List list){
    if(list.isEmpty()){
      System.out.println("No request is in the list");
    }else{
      list.stream().forEach(r -> System.out.println(r));
    }
  }
  
  private void answerRequestsPage(){
    while(true){
      System.out.println("[E]: Answer an Enrollement Request");
      System.out.println("[D]: Answer a Drop-Course Request");
      System.out.println("[G]: Confirm Grading Request");
      System.out.println("[X]: Return to the Admin Panel");
      String answer = this.scanner.nextLine().toUpperCase();
      if(answer.equals("X")){
        break;
      }
      String id =this.getId();
      switch(answer){
        case "E" -> this.manag.confirmEnrollRequest(id);
        case "D" -> this.manag.confirmDropRequest(id);
        case "G" -> this.manag.confirmGradingRequest(id);
      }
      this.checkIfRequestChecked(id);
    }
  }
  
  private void checkIfRequestChecked(String id){
    UUID idd = UUID.fromString(id);
    if(this.manag.getCheckedRequests().contains(idd)){
      System.out.println("Request Answered");
    }else{
      System.out.println("Request still unchecked!");
    }
  }
  
  private String getId(){
    System.out.println("Enter The Request Id: ");
    String id = this.scanner.nextLine();
    while(!this.manag.isValidUUID(id)){
      System.out.println("Invalid Id. Try again");
      id = this.scanner.nextLine();
    }
    return id;
  }
  
  private void teacherMenu(Teacher teacher){
    teacher.setManag(manag);
    while(true){
      System.out.println("[C]: View Assigned Courses");
      System.out.println("[A]: Assign Course");
      System.out.println("[D]: Extend Assignement Deadline");
      System.out.println("[B]: Mark Absence");
      System.out.println("[S]: Assign Student");
      System.out.println("[G]: Grade Student");
      System.out.println("[X]: Quit");
      String answer = this.scanner.nextLine().toUpperCase();
      if(answer.equals("X")){
        break;
      }
      if(answer.equals("C")){
        this.viewWhatInsideTheList(teacher.getCourses());
      }else if(answer.equals("A")){
        this.assignCoursePage(teacher);
      }else if(answer.equals("S")){
        this.assignStudentPage(teacher);
      }else if(answer.equals("G")){
        this.gradeStudentPage(teacher);
      }else if(answer.equals("D")){
        this.extendAssignPage(teacher);
      }else{
        this.markAbsencePage(teacher);
      }
    }
  }
  
  private void markAbsencePage(Teacher teacher){
    System.out.println("Enter date");
    LocalDate date= null;
    String inputDate = this.scanner.nextLine();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");
    try {
          date = LocalDate.parse(inputDate, formatter);
        } catch (Exception e) {
          System.out.println("Invalid date format. Please enter date in format dd/mm/yy");
        }
    String code = this.getCourseCode();
    System.out.println("Enter the names of absent students (Empty will stop!)");
    String name = this.scanner.nextLine();
    while(!name.isBlank()){
      teacher.markAbsence(code, name, date);
      name = this.scanner.nextLine();
    }
  }
  
  private void extendAssignPage(Teacher teacher){
    String courseCode = this.getCourseCode();
    System.out.println("Enter the assignement Title");
    String title = this.scanner.nextLine();
    Assignement assign = this.manag.getAssignement(courseCode, title);
    System.out.println("Enter the new deadline");
    System.out.print("Enter new deadline in format dd/mm/yy: ");
    LocalDate deadline= null;
    String inputDate = this.scanner.nextLine();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");
    try {
          deadline = LocalDate.parse(inputDate, formatter);
        } catch (Exception e) {
          System.out.println("Invalid date format. Please enter date in format dd/mm/yy");
        }
    teacher.extendAssignementDeadline(courseCode, title, deadline);
    if(assign.isAssignementExtended()){
      System.out.println("Assignement Deadline Successfully Extended!");
    }else{
      System.out.println("Failed to extend the deadline! Try again!");
      this.extendAssignPage(teacher);
    }
  }
  
  private void gradeStudentPage(Teacher teacher){
    while(true){
      Student student;
      Assignement assign;
      GradeStudent gradeRequest;
      while(true){
        System.out.println("Enter the student id");
        String id = this.scanner.next();
        student = (Student) this.manag.getRegistrableForID(id);
        if(student!=null){
          break;
        }
      }
      while(true){
        System.out.println("Enter the course code");
        String code = this.scanner.nextLine();
        while(!this.manag.getCodes().contains(code)){
          System.out.println("No course for the code you entered! Try again!");
          code = this.scanner.nextLine();
        }
        System.out.println("Enter the assignement title");
        String title = this.scanner.nextLine();
        assign = this.manag.getAssignement(code, title);
        if(assign!=null){
          break;
        }
      }
      System.out.println("Enter the grade");
      double grade = Double.parseDouble(this.scanner.nextLine());
      while(grade<0 || grade>20){
        System.out.println("Invalid grade! Try again");
        grade = Double.parseDouble(this.scanner.nextLine());
      }
      gradeRequest = this.manag.getGradingRequests().get(this.manag.getGradingRequests().size()-1);
      GradeStudent request = new GradeStudent(teacher, student, assign, grade);
      if(gradeRequest.equals(request)){
        System.out.println("Grading Request was sent!");
        break;
      }
    }
    
  }
  
  private void assignStudentPage(Teacher teacher){
    System.out.println("Enter the course code");
    String code = this.scanner.nextLine();
    while(!this.manag.getCodes().contains(code)){
      System.out.println("No course for the code you entered! Try again!");
      code = this.scanner.nextLine();
    }
    System.out.println("Enter the assignement title");
    String title = this.scanner.nextLine();
    System.out.println("Enter the student id");
    String id = this.scanner.nextLine();
    Assignement assign = this.manag.getAssignement(code, title);
    Student student = (Student) this.manag.getRegistrableForID(id);
    teacher.assignStudent(code, title, id);
    if(student.getAssignements().contains(assign)){
      System.out.println("Student successful assigned with the assignement!");
    }else{
      System.out.println("Failed to assign student! Try again!");
      this.assignStudentPage(teacher);
    }
  }
  
  private void assignCoursePage(Teacher teacher){
    System.out.println("Enter the course code");
    String code = this.scanner.nextLine();
    while(!this.manag.getCodes().contains(code)){
      System.out.println("No course for the code you entered! Try again!");
      code = this.scanner.nextLine();
    }
    System.out.println("Enter the assignement title");
    String title = this.scanner.nextLine();
    System.out.println("Enter the assignement deadline (dd/mm/yy)");
    String inputdate = this.scanner.next();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");
    LocalDate deadline = LocalDate.parse(inputdate, formatter);
    System.out.println("Enter the assignement description");
    String desc = this.scanner.nextLine();
    teacher.assignCourse(code, title, deadline, desc);
    if(this.manag.getAssignement(code, title)!=null){
      System.out.println("Assigned: " + title + " Added to Course: " + code);
    }else{
      System.out.println("Failed to assign the course! Try again!");
      this.assignCoursePage(teacher);
    }
  }
  
  private void studentMenu(Student student){
    student.setManag(manag);
    while(true){
      System.out.println("[C]: View Current Enrollement Courses");
      System.out.println("[A]: View Available Courses");
      System.out.println("[S]: View Assignements");
      System.out.println("[N]: View Announcements");
      System.out.println("[G]: Get Average Grade for a Specific Course");
      System.out.println("[T]: Get the current Total grade");
      System.out.println("[E]: Enroll in a course");
      System.out.println("[D]: Drop a course");
      System.out.println("[X]: Quit");
      String answer = this.scanner.nextLine().toUpperCase();
      if(answer.equals("X")){
        break;
      }
      if(answer.equals("C")){
        this.viewWhatInsideTheList(student.getCurrentCourses());
      }else if(answer.equals("A")){
        this.manag.getCourses().stream().filter(r -> r.isAvailable()).forEach(r -> System.out.println(r));
      }else if(answer.equals("S")){
        this.viewWhatInsideTheList(student.getAssignements());
      }else if(answer.equals("T")){
        student.getTotalGrade();
      }else if(answer.equals("E")){
        String code = this.getCourseCode();
        student.enroll_In_Course(code);
        UUID id = student.getRequestIdList().get(student.getRequestIdList().size()-1);
        this.checkIfRequestSent(RequestSubject.ENROLLEMENT, id);
      }else if(answer.equals("D")){
        String code = this.getCourseCode();
        student.dropCourse(code);
        UUID id = student.getRequestIdList().get(student.getRequestIdList().size()-1);
        this.checkIfRequestSent(RequestSubject.DROP, id);
      }else if(answer.equals("G")){
        String code = this.getCourseCode();
        double grade = student.getAverageGradeForCourse(this.manag.getCourseForCode(code));
        if(grade==-1){
          System.out.println("You aren't even enrolled in the course!");
        }else{
          System.out.println("The average grade for the course is: " + grade + "/20");
        }
      }else{
        for(int i=student.getAnnouncements().size()-1;i>=0;i--){
          System.out.println(student.getAnnouncements().get(i));
        }
      }
    }
  }
  
  private void checkIfRequestSent(RequestSubject request, UUID id){
    String idd = id.toString();
    if(request==RequestSubject.DROP){
      if(this.manag.getDropRequestForID(idd)!=null){
        System.out.println("Request Sent!");
      }else{
        System.out.println("Failed to sent the request!");
      }
    }else if(request==RequestSubject.ENROLLEMENT){
      if(this.manag.getEnrollRequestForID(idd)!=null){
        System.out.println("Request Sent!");
      }else{
        System.out.println("Failed to send the request!");
      }
    }
  }
  
  private String getCourseCode(){
    System.out.println("Enter the course code");
    String code = this.scanner.nextLine();
    while(!this.manag.getCodes().contains(code)){
      System.out.println("Invalid course code! Try again");
      code = this.scanner.nextLine();
    }
    return code;
  }
  
  public boolean isValidEmail(String email){
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
