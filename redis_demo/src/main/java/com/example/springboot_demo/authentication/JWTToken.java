package com.example.springboot_demo.authentication;

public class JWTToken {

  private static final long serialVersionUID = 1282057025599826155L;

  private String token;

  private String exipreAt;

  public JWTToken(String token) {
    this.token = token;
  }

  public JWTToken(String token, String exipreAt) {
    this.token = token;
    this.exipreAt = exipreAt;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public String getExipreAt() {
    return exipreAt;
  }

  public void setExipreAt(String exipreAt) {
    this.exipreAt = exipreAt;
  }
}
