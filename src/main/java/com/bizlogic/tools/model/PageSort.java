package com.bizlogic.tools.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageSort {
  
  Integer page;
  Integer limit;
  String sort;
  String field;
  
}
