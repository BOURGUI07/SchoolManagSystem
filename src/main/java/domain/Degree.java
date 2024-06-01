/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package domain;

/**
 *
 * @author lenovo
 */
public enum Degree {
  ASSOCIATE("Associate Degree"),
  BACHELOR("Bachelor Degree"),
  MASTER("Master Degree"),
  PHD("PhD");
  
  private final String string;
  
  Degree(String string){
    this.string=string;
  }
  
  @Override
  public String toString(){
    return this.string;
  }
}
