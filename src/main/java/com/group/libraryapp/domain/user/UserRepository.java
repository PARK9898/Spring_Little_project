package com.group.libraryapp.domain.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

// @Repository 붙히지 않아도 jpaRapository를 상속하는것만으로도 주입 된다
public interface UserRepository extends JpaRepository<User ,Long> {
	Optional<User> findByName(String name); // 이렇게 작성하면 UserService딴에서 사용이 가능하다 함수이름에 맞춰서 sql이 작성이 된다
	//find라고 작성하면 1개의 데이터만 가져옴(객체 or Optional)  By 뒤에 붙는 필드 이름으로 select쿼리의 where문이 작성이 된다.
	boolean existsByName(String name); // 이 이름을 가진 객체가 존재하면 true 아님 false

	long countByAge(Integer age);
}
