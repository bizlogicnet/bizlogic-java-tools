package com.bizlogic.tools.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageResponse {
  
  public long count;
  public Object data;
  public Object header;

}