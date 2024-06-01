/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package domain;

/**
 *
 * @author lenovo
 */
public enum RegistrableType {
  STUDENT("Student"), TEACHER("Teacher");
  
  private final String string;
  
  RegistrableType(String string){
    this.string=string;
  }
  
  @Override
  public String toString(){
    return  this.string;
  }
}
