/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domain;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;
import logic.SchoolManagement;

/**
 *
 * @author lenovo
 */
public abstract class Registrable implements Comparable<Registrable> {

  private String name;
  private UUID id;
  private String phone;
  private String email;
  private Gender gender;
  private LocalDate birthdate;
  private SchoolManagement manag;
  
  public Registrable(String name, LocalDate birthdate, Gender gender, String email, String phone){
    this.id = UUID.randomUUID();
    this.name=name; 
    this.birthdate=birthdate;
    this.gender=gender;
    this.email = email;
    this.phone = phone;
  }
  
  public abstract RegistrableType getType();
  
  public void setManag(SchoolManagement manag){
    this.manag=manag;
  }
  
  public SchoolManagement getManag(){
    return this.manag;
  }
  
  public void setName(String name) {
    this.name = name;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setGender(Gender gender) {
    this.gender = gender;
  }

  public void setBirthdate(LocalDate birthdate) {
    this.birthdate = birthdate;
  }

  public String getName() {
    return name;
  }

  public UUID getId() {
    return id;
  }

  public String getPhone() {
    return phone;
  }

  public String getEmail() {
    return email;
  }

  public Gender getGender() {
    return gender;
  }

  public LocalDate getBirthdate() {
    return birthdate;
  }
  
  
  @Override
  public int hashCode() {
    int hash = 7;
    hash = 67 * hash + Objects.hashCode(this.name);
    hash = 67 * hash + Objects.hashCode(this.id);
    return hash;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final Registrable other = (Registrable) obj;
    return Objects.equals(this.name, other.name);
  }
  
  @Override
  public abstract int compareTo(Registrable reg);
}
