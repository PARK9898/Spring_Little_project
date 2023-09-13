package com.group.libraryapp.controller.user;


import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.group.libraryapp.dto.user.request.UserCreateRequest;
import com.group.libraryapp.dto.user.request.UserUpdateRequest;
import com.group.libraryapp.dto.user.response.UserResponse;
import com.group.libraryapp.service.user.UserServiceV1;
import com.group.libraryapp.service.user.UserServiceV2;

//API을 통해 생긴 유저는 RAM에 저장한다
@RestController
public class UserController {
	private final UserServiceV2 userServiceV2;

	 // 자바 데이터베이스 커넥터에 대한 클래스 이걸 통해서 데이터 베이스에 접근 가능
	// 스프링이 자동으로 Jdbc 템픞릿을 넣어준다
	// userController을 인스턴스화 한적은 없다
	public UserController(UserServiceV2 userServiceV2) {
		this.userServiceV2 = userServiceV2;
	}
	@PostMapping("/user")
	public void saveUser(@RequestBody UserCreateRequest request) {
		userServiceV2.saveUser(request);
	}
	//컨트롤러에서 getter가 있는 객체를 반환하는 JSON이 된다
	@GetMapping("/user")
	public List<UserResponse> getUser() {
		return userServiceV2.getUser();//  user 정보를 우리가 선언한 타입으로 바꿔주는 함수
	}
	//Request Param & Mapping 관련 정리 한 번 하기
	@PutMapping("/user") // @ReqeustBody로 객체로 변환시켜주고 있음
	public void updateUser(@RequestBody UserUpdateRequest request) { // request.getId 는 ?에 들어감
		userServiceV2.updateUser(request);
	}
	//DeleteMapping 은 바디가 아닌 쿼리를 쓴다
	@DeleteMapping("/user")
	public void deleteUser(@RequestParam String name) {
		userServiceV2.deleteUser(name);// update는 변경사항이 있을경우 사용
	}
}
