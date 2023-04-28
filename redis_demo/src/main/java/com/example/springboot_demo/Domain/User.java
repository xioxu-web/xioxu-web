package com.example.springboot_demo.Domain;
import java.io.Serializable;
import java.util.Set;

public class User implements Serializable {
  private Integer userId;
  private String username;
  private String pswd;
  private Set<String> role;
  private Set<String> permission;

  public User(String username, String pswd, Set<String> role, Set<String> permission) {
    this.username = username;
    this.pswd = pswd;
    this.role = role;
    this.permission = permission;
  }

  public Set<String> getRole() {
    return role;
  }

  public void setRole(Set<String> role) {
    this.role = role;
  }

  public Set<String> getPermission() {
    return permission;
  }

  public void setPermission(Set<String> permission) {
    this.permission = permission;
  }

  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPswd() {
    return pswd;
  }

  public void setPswd(String pswd) {
    this.pswd = pswd;
  }

  @Override
  public String toString() {
    return "bean{" +
      "userId=" + userId +
      ", username='" + username + '\'' +
      ", pswd='" + pswd + '\'' +
      '}';
  }
}
