/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package testDomain;

import domain.Gender;
import domain.Registrable;
import domain.RegistrableType;
import domain.Course;
import domain.Degree;
import domain.Drop;
import domain.Enroll;
import domain.Student;
import domain.Teacher;
import java.time.LocalDate;
import java.time.Month;
import java.util.UUID;
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
public class TestAppLogic {
  private Student student;
  private Course course;
  private Teacher teacher;
  private SchoolManagement manag;
  private Drop drop;
  private Enroll enroll;
  
  public TestAppLogic() {
  }
  
  @BeforeAll
  public static void setUpClass() {
  }
  
  @AfterAll
  public static void tearDownClass() {
  }
  
  @BeforeEach
  public void setUp() {
    student = new Student("Youness" , LocalDate.of(2000, Month.NOVEMBER, 17), Gender.MALE, "email", "phone");
    course = new Course("CS61B" , "Data Structures" , "description" , 100);
    teacher = new Teacher("Moha" , LocalDate.of(1988, Month.MARCH, 5), Gender.MALE, "email", "phone", Degree.PHD);
    manag = new SchoolManagement();
    student.setManag(manag);
    
    manag.addCourse("CS61B" , "Data Structures" , "description" , 100);
    manag.addStudent("Youness" , LocalDate.of(2000, Month.NOVEMBER, 17), Gender.MALE, "email", "phone");
    manag.addTeacher("Moha" , LocalDate.of(1988, Month.MARCH, 5), Gender.MALE, "email", "phone", Degree.PHD);
    manag.getTeachers().get(0).setManag(manag);
  }
  
  @Test
  void test1(){
    assertEquals(RegistrableType.STUDENT, student.getType());
    assertFalse(student.getId()==null);
    assertEquals("Youness", student.getName());
    assertEquals(LocalDate.of(2000, Month.NOVEMBER, 17), student.getBirthdate());
    assertEquals(Gender.MALE, student.getGender());
    assertEquals("email", student.getEmail());
    assertEquals("phone", student.getPhone());
    assertFalse(student.getManag()==null);
    assertTrue(student.getCurrentCourses().isEmpty());
    assertTrue(student.getCourseGrades().isEmpty());
    assertTrue(student.getTotalGrade()==0);
  }
  
  @Test
  void test2(){
    assertTrue(course.isAvailable());
    assertTrue(course.currentOccupiedSeats()==0);
    assertTrue(course.getAssignements().isEmpty());
    assertTrue(course.getEnrolledStudents().isEmpty());
    assertEquals("CS61B", course.getCode());
    assertEquals("Data Structures", course.getTitle());
    assertEquals("description", course.getDescription());
    assertEquals(100, course.getSeatLimit());
    assertEquals(100, course.numberOfAvailableSeats());
  }
  
  @Test
  void test3(){
    assertTrue(manag.getStudents().contains(student));
    assertTrue(manag.getStudents().size()==1);
    assertTrue(manag.getTeachers().contains(teacher));
    assertTrue(manag.getCourses().contains(course));
  }
  
  @Test
  void test4(){
    assertEquals(Degree.PHD, teacher.getDegree());
    assertEquals("Moha", teacher.getName());
    assertEquals(Gender.MALE, teacher.getGender());
    assertEquals(RegistrableType.TEACHER, teacher.getType());
    assertTrue(teacher.getCourses().isEmpty());
    assertTrue(teacher.getNumberOfAssignedCourses()==0);
    assertTrue(teacher.getId()!= null);
  }
  
  @Test
  void test5(){
    String id = manag.getTeachers().get(0).getId().toString();
    manag.assignCourseToTeacher(id, "CS61B");
    Teacher teacher1 = manag.getTeachers().get(0);
    assertFalse(teacher1.getCourses().isEmpty());
    assertFalse(manag.getMap().isEmpty());
    assertTrue(manag.getMap().containsKey(teacher1));
    assertTrue(teacher1.getCourses().contains(course));
    assertTrue(teacher1.getNumberOfAssignedCourses()==1);
  }
  
  @Test
  void test6(){
    UUID id = teacher.getId();
    UUID id1 = manag.getTeachers().get(0).getId();
    assertFalse(id.equals(id1));
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
