package com.bizlogic.tools.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponse {
  
  private int status;
  private Boolean success;
  private List<String> errorMessages;
  private List<String> infoMessages;
  private Object data;
  private String url;

  public void addMessage(String msg, Boolean error) {
		if (error == true) {
			if (this.errorMessages == null) this.errorMessages = new ArrayList<String>();
			this.errorMessages.add(msg);
      success = false;
		} else {
			if (this.infoMessages == null) this.infoMessages = new ArrayList<String>();
			this.infoMessages.add(msg);
		}
	}

  public void appendMessages(List<String> messages, Boolean error, String prefix) {
    if (error == true) {
			if (this.errorMessages == null) this.errorMessages = new ArrayList<String>();
      for (String message : messages) {
        this.errorMessages.add(prefix + message);
      }
		} else {
			if (this.infoMessages == null) this.infoMessages = new ArrayList<String>();
			for (String message : messages) {
        this.infoMessages.add(prefix + message);
      }
		}
  }

  public void setSuccess(Boolean success, int status) {
    this.success = success;
    this.status = status;
  }

  public ApiResponse() {
    this.setSuccess(true, 200);
  }

}