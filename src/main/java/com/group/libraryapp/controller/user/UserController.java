package com.group.libraryapp.controller.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.group.libraryapp.controller.domain.user.User;
import com.group.libraryapp.dto.user.request.UserCreateRequest;
import com.group.libraryapp.dto.user.response.UserResponse;

@RestController
public class UserController {

	private final List<User> users = new ArrayList<>();

	@PostMapping("/user")
	public void saveUser(@RequestBody UserCreateRequest request) {
		users.add(new User(request.getName(), request.getAge()));
	}
	//컨트롤러에서 getter가 있는 객체를 반환하는 JSON이 된다
	@GetMapping("/user")
	public List<UserResponse> getUser() {
		List<UserResponse> responses = new ArrayList<>();
		for(int i = 0; i < users.size(); i++) {
			responses.add(new UserResponse(i+1,users.get(i)));
		}
		return responses;
	}
}
