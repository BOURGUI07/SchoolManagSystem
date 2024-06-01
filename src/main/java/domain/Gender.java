/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package domain;

/**
 *
 * @author lenovo
 */
public enum Gender {
  MALE("Male"),
  FEMALE("Female");
  
  private final String string;
  
  Gender(String string){
    this.string=string;
  }
  
  @Override
  public String toString(){
    return this.string;
  }
}
