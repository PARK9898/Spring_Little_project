package com.group.libraryapp.service.user;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.group.libraryapp.controller.domain.user.User;
import com.group.libraryapp.controller.domain.user.UserRepository;
import com.group.libraryapp.dto.user.request.UserCreateRequest;
import com.group.libraryapp.dto.user.request.UserUpdateRequest;
import com.group.libraryapp.dto.user.response.UserResponse;


@Service
public class UserServiceV2 {
	private final UserRepository userRepository;

	public UserServiceV2(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public void saveUser(UserCreateRequest request) {
		userRepository.save(new User(request.getName(),request.getAge()));
	} // save 메소드에 객체를 넣어주면 INSERT SQL 자동으로 날라간다 // id는 자동으로 생성

	public List<UserResponse> getUsers() {
		List<User> users = userRepository.findAll();
		return users.stream()
			.map(user -> new UserResponse(user.getId(),user.getName(),user.getAge()))
			.collect(Collectors.toList());
	}// findAll을 사용하면 User를 다 찾아온다

	public void updateUser(UserUpdateRequest request) {
		User user  = userRepository.findById(request.getId()) // 옵셔널로 안에 아무것도 존재하지 않는다면
			.orElseThrow(IllegalArgumentException::new);

		user.updateName(request.getName()); // 유저 이름을 업데이트 하는것이므로 이 메서드는 유저에 있는게 맞다
		userRepository.save(user); //바뀐걸 기준으로 업데이트 쿼리가 날라간다 user를 레포지토리에 넣으면 여기에 맞는 테이블에 매핑시켜 저장한다
	}

	public void deleteUser(String name) {
		//findById는 있지만 Name은 없다 그럼 인터페이스에서 작업한다
		User user = userRepository.findByName(name); // orELseThrow 사용 불가 findById에는 구현되어있지만 내가 만든 findByName에는 있지 않다
		if (user == null) {
			throw new IllegalArgumentException();
		}

		userRepository.delete(user);
	}

}
