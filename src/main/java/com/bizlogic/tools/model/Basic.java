package com.bizlogic.tools.model;

import java.util.Date;

import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

@Embeddable
public class Basic {

	@Getter
	@Setter
	private Integer basicStatus;

	@Getter
	@Setter
	private Date basicCreateDate;

	@Getter
	@Setter
	private Date basicUpdateDate;

	@Getter
	@Setter
	private String basicUpdateUser;

	@Getter
	@Setter
	private String basicUpdateIp;

	public void prePersist() {
		this.basicCreateDate = new Date();
	}

	public void preUpdate() {
		this.basicUpdateDate = new Date();
	}

}
