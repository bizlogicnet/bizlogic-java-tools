package com.bizlogic.tools.model;

import java.nio.file.Path;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileDownload {
  
  private Boolean result;
  private String message;
  private String fileName;
  private Path target;

  public FileDownload() {
    super();
    this.result = true;
  }

}
