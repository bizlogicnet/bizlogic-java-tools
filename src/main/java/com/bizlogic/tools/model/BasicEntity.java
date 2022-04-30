package com.bizlogic.tools.model;

import javax.persistence.Embedded;
import javax.persistence.Entity;

@Entity
public class BasicEntity {
  
  @Embedded
	private Basic basic;

}
