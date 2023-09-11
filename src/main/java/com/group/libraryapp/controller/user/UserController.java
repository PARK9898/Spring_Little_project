package com.group.libraryapp.controller.user;


import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
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
import com.group.libraryapp.service.user.UserService;

//API을 통해 생긴 유저는 RAM에 저장한다
@RestController
public class UserController {
	private final UserService userService = new UserService();
	private final JdbcTemplate jdbcTemplate; // 자바 데이터베이스 커넥터에 대한 클래스 이걸 통해서 데이터 베이스에 접근 가능
	// 스프링이 자동으로 Jdbc 템픞릿을 넣어준다

	public UserController(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	@PostMapping("/user")
	public void saveUser(@RequestBody UserCreateRequest request) {
		String sql = "INSERT INTO user (name , age) VALUE (?,?)"; // 물을표에 하나씩 들어간다
		jdbcTemplate.update(sql,request.getName() , request.getAge());
	}
	//컨트롤러에서 getter가 있는 객체를 반환하는 JSON이 된다
	@GetMapping("/user")
	public List<UserResponse> getUser() {
		String sql = "SELECT * FROM user"; //  user 정보를 우리가 선언한 타입으로 바꿔주는 함수
		return jdbcTemplate.query(sql, (rs, rowNum) -> {
			long id = rs.getLong("id");
			String name = rs.getString("name");
			int age = rs.getInt("age");
			return new UserResponse(id,name,age);
		});
	}
	//Request Param & Mapping 관련 정리 한 번 하기
	@PutMapping("/user") // @ReqeustBody로 객체로 변환시켜주고 있음
	public void updateUser(@RequestBody UserUpdateRequest request) { // request.getId 는 ?에 들어감
		userService.updateUser(jdbcTemplate,request);
	}
	//DeleteMapping 은 바디가 아닌 쿼리를 쓴다
	@DeleteMapping("/user")
	public void deleteUser(@RequestParam String name) {
		String sql = "DELETE FROM user WHERE name = ?";
		String readSql = " SELECT * FROM user WHERE id = ?";
		boolean isUserNotExist = jdbcTemplate.query(readSql,(rs, rowNum) -> 0 , name).isEmpty();
		if (isUserNotExist) {
			throw new IllegalArgumentException();
		}
		jdbcTemplate.update(sql,name); // update는 변경사항이 있을경우 사용
	}
}
