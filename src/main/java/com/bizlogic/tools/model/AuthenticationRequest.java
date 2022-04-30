package com.bizlogic.tools.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticationRequest {
  
  private String userName;
  private String password;

}