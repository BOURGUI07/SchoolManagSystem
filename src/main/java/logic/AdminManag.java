/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logic;

import domain.Admin;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author lenovo
 */
public class AdminManag {
  private List<Admin> admins;
  private static final String EMAIL_PATTERN = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
  
  public AdminManag(){
    this.admins=new ArrayList<>();
  }
  
  public void registerAdmin(Admin admin){
    if(!this.admins.contains(admin)){
      this.admins.add(admin);
    }else{
      throw new IllegalArgumentException("The admin already registrated!!");
    }
  }
  
  public boolean isRegistrated(Admin admin){
    return this.admins.contains(admin);
  }
  
  public boolean canAdminLogin(String username, String password){
    return this.getAdmin(username, password)!=null;
  }
  
  public Admin getAdmin(String username, String password){
    for(Admin admin:this.admins){
      if(admin.getPassword().equals(password) && admin.getUsername().equals(username)){
        return admin;
      }
    }
    return null;
  }
  
  public boolean isValidEmail(String email){
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

  public boolean isSecure(String password) {
    if (password == null || password.isEmpty()) {
        return false;
    }
    
    // Define regex patterns
    String digitRegex = ".*\\d.*";
    String uppercaseRegex = ".*[A-Z].*";
    String lowercaseRegex = ".*[a-z].*";
    String specialCharRegex = ".*[@#$%^&+=].*";
    
    // Check password against each pattern
    boolean hasDigit = password.matches(digitRegex);
    boolean hasUppercase = password.matches(uppercaseRegex);
    boolean hasLowercase = password.matches(lowercaseRegex);
    boolean hasSpecialChar = password.matches(specialCharRegex);
    
    // Check password length
    boolean isLengthValid = password.length() >= 8;

    return hasDigit && hasUppercase && hasLowercase && hasSpecialChar && isLengthValid;
  }
  
  public boolean isAdminUserNameValid(String username){
    return username.startsWith("@admin");
  }
}
