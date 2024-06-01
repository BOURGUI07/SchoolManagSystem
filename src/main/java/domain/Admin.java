/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domain;

import java.util.Objects;

/**
 *
 * @author lenovo
 */
public class Admin {
  private String username;
  private String password;
  private String email;
  
  public Admin(String username, String password, String email){
    this.email=email;
    this.username=username;
    this.password=password;
  }
  
  
  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public String getEmail() {
    return email;
  }
  
  @Override
  public int hashCode() {
    int hash = 3;
    hash = 97 * hash + Objects.hashCode(this.username);
    hash = 97 * hash + Objects.hashCode(this.password);
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
    final Admin other = (Admin) obj;
    if (!Objects.equals(this.username, other.username)) {
      return false;
    }
    return Objects.equals(this.password, other.password);
  }
}
