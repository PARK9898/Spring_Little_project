package com.group.libraryapp.dto.user.request;

public class UserCreateRequest {
	private String name;
	private Integer age; // Integer 이렇게 사용하면 null이 들어와도 됨

	public UserCreateRequest(String name, Integer age) {
		this.name = name;
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public Integer getAge() {
		return age;
	}
}
