package com.czm.model;

import java.io.Serializable;

/**
 * 考试真题的实体model类
 * 
 * @author caizhiming
 * 
 */
public class Person implements Serializable {

	private static final long serialVersionUID = -2485479562973773291L;
	private int id;
	private String name;
	private String address;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}
