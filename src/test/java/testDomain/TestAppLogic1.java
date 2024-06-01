/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package testDomain;

import domain.*;
import java.time.LocalDate;
import java.time.Month;
import logic.SchoolManagement;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author lenovo
 */
public class TestAppLogic1 {
  private SchoolManagement manag;
  private Teacher teacher;
  private Course course;
  private Student student;
  private StudentRequest enroll;
  private StudentRequest drop;
  
  public TestAppLogic1() {
  }
  
  @BeforeAll
  public static void setUpClass() {
  }
  
  @AfterAll
  public static void tearDownClass() {
  }
  
  @BeforeEach
  public void setUp() {
   manag = new SchoolManagement();
   manag.addCourse("cs61b", "cs", "des", 100);
   manag.addStudent("youness", LocalDate.of(2000, Month.MARCH, 14) , Gender.MALE, "email", "phone");
   manag.addTeacher("omar", LocalDate.now(), Gender.MALE, "email", "phone", Degree.MASTER);
   course = manag.getCourses().get(0);
   teacher = manag.getTeachers().get(0);
   teacher.setManag(manag);
   student = (Student) manag.getStudents().get(0);
   student.setManag(manag);
   String teacherID = teacher.getId().toString();
   manag.assignCourseToTeacher(teacherID, "cs61b");
   teacher.assignCourse("cs61b", "homework", LocalDate.of(2024, Month.MARCH, 15), "desc");
   
  }
  
  @Test
  void test1(){
    assertTrue(!manag.getAssignements().isEmpty());
    assertFalse(manag.getAssignements().get(0)==null);
    Assignement assign = manag.getAssignements().get(0);
    assertFalse(course.getAssignements().isEmpty());
    assertTrue(course.getAssignements().contains(assign));
    assertEquals("homework", assign.getTitle());
    assertEquals(course, assign.getCourse());
  }
  
  @Test
  void test2(){
    student.enroll_In_Course("cs61b");
    enroll = manag.getEnrollementRequests().get(0);
    manag.confirmEnrollRequest(enroll.getRequestID().toString());
    assertTrue(enroll.requestStatus());
    assertFalse(student.getCurrentCourses().isEmpty());
    assertTrue(course.getEnrolledStudents().contains(student));
    assertFalse(course.currentOccupiedSeats()==0);
    Assignement assign = manag.getAssignements().get(0);
    String studentID = student.getId().toString();
    teacher.assignStudent("cs61b", "homework", studentID);
    assertTrue(assign.getAssignedStudents().contains(student));
  }
  
  @Test
  void test3(){
    student.enroll_In_Course("cs61b");
    enroll = manag.getEnrollementRequests().get(0);
    manag.confirmEnrollRequest(enroll.getRequestID().toString());
    student.dropCourse("cs61b");
    drop = manag.getDropRequests().get(0);
    String id = drop.getRequestID().toString();
    manag.confirmDropRequest(id);
    assertTrue(student.getCurrentCourses().isEmpty());
    assertFalse(course.getEnrolledStudents().contains(student));
    assertTrue(drop.requestStatus());
  }
  
  @Test
  void test4(){
    student.dropCourse("cs61b");
    assertFalse(manag.getDropRequests().isEmpty());
  }
  
  @Test
  void test5(){
    student.dropCourse("cs61b");
    assertNotNull(manag.getDropRequests().get(0));
  }
  
  @Test
  void test6(){
    student.dropCourse("cs61b");
    assertNotNull(manag.getDropRequests().get(0).getRequestID().toString());
  }
  
  @Test
  void test7(){
    student.dropCourse("cs61b");
    assertNotNull(manag.getDropRequests().get(0).getSubject());
  }
  
  @Test
  void test8(){
    student.enroll_In_Course("cs61b");
    enroll = manag.getEnrollementRequests().get(0);
    manag.confirmEnrollRequest(enroll.getRequestID().toString());
    String studentID = student.getId().toString();
    teacher.assignStudent("cs61b", "homework", studentID);
    teacher.gradeStudent(studentID, "cs61b", "homework", 15.5);
    String id = manag.getGradingRequests().get(0).getRequestID().toString();
    manag.confirmGradingRequest(id);
    assertTrue(student.getAverageGradeForCourse(course)==15.5);
  }
  
  @AfterEach
  public void tearDown() {
  }

  // TODO add test methods here.
  // The methods must be annotated with annotation @Test. For example:
  //
  // @Test
  // public void hello() {}
}
