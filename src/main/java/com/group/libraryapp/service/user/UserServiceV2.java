package com.group.libraryapp.service.user;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.group.libraryapp.domain.user.User;
import com.group.libraryapp.domain.user.UserRepository;
import com.group.libraryapp.dto.user.request.UserCreateRequest;
import com.group.libraryapp.dto.user.request.UserUpdateRequest;
import com.group.libraryapp.dto.user.response.UserResponse;


@Service
public class UserServiceV2 {
	private final UserRepository userRepository;

	public UserServiceV2(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Transactional // 이 메서드가 정상적으로 성공하면 commit 아니면 Rollback 처음 함수가 시작할 때 start transaction; 을 해준다
	public void saveUser(UserCreateRequest request) {
		userRepository.save(new User(request.getName(),request.getAge()));
		//throw new IllegalArgumentException(); // 이 경우 롤백 되어야 맞는거임 한 번 확인해보기 @Transactionl이 없으면 그냥 반영이 되어버린다 크나큰 오류!
	} // save 메소드에 객체를 넣어주면 INSERT SQL 자동으로 날라간다 // id는 자동으로 생성
	@Transactional(readOnly = true) // Select 쿼리만 사용한다면 readonly옵션을 사용 할 수 있다 -> 데이터 삭제 조회 이런 기능이 빠져서 성능이 약간 좋아진다
	public List<UserResponse> getUser() {
		List<User> users = userRepository.findAll(); // List<> 반환
		return users.stream()
			.map(user -> new UserResponse(user.getId(),user.getName(),user.getAge()))
			.collect(Collectors.toList());
	}// findAll을 사용하면 User를 다 찾아온다
	@Transactional
	public void updateUser(UserUpdateRequest request) {
		User user  = userRepository.findById(request.getId()) // 옵셔널로 안에 아무것도 존재하지 않는다면
			.orElseThrow(IllegalArgumentException::new);

		user.updateName(request.getName()); // 유저 이름을 업데이트 하는것이므로 이 메서드는 유저에 있는게 맞다
		//userRepository.save(user); //바뀐걸 기준으로 업데이트 쿼리가 날라간다 user를 레포지토리에 넣으면 여기에 맞는 테이블에 매핑시켜 저장한다
	} //
	@Transactional
	public void deleteUser(String name) { //
		//findById는 있지만 Name은 없다 그럼 인터페이스에서 작업한다
		User user = userRepository.findByName(name). // -> 영속성 컨택스트 시작
			orElseThrow(IllegalArgumentException::new); // orELseThrow 사용하려면 userRepository 측에서 optional로 처리해줘야한다
		// if(!userRepository.existsByName(name)) {
		// 	throw new IllegalArgumentException();
		// }
		// User user = userRepository.findByName(name); optional을 사용하면 반환되는 곳에다가 orElseThrow를 해줘야함 이런 경우는 그냥 반환 타입을 유저라고 설정해주면 된다
		userRepository.delete(user); // -> 영속성 컨택스트 끝
	}

}
