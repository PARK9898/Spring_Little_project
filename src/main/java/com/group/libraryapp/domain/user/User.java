package com.group.libraryapp.domain.user;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.group.libraryapp.domain.user.loanhistory.UserLoanHistory;

@Entity // 스프링이 User 객체와 user 테이블을 같은것으로 바라본다 저장되고 관리되어야하는 데이터
public class User {
	@Id //pk 로설정
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키 자동생성 id 값을 따로 할당하지 않아도 데이터 베이스가 자동으로 AUTO_INCREMENT 하여 기본키를 생성해준다
	private Long id = null;
	@Column(nullable = false,length =20 ,name = "name") //객체의 필드와 Table의 필드를 매핑한다 이름 같으면 name = "name" 생략 가능
	private String name;
	private Integer age; // age는 Integer와 int가 동일하고  null이 들어가도 되니까 Colum 사용하지 않아도 됨
	@OneToMany(mappedBy = "user",cascade = CascadeType.ALL,orphanRemoval = true) // 자신이 연관관계의 주인이 아님
	private List<UserLoanHistory> userLoanHistories = new ArrayList<>();
	protected User() { // jpa를 사용하기 위해서는 기본 생성자가 필요하다
	}

	public User(String name, Integer age) {
		if (name == null || name.isBlank()) {
			throw new IllegalArgumentException(String.format("잘못된 name(%s)이 들어왔습니다"));
		}
		this.name = name;
		this.age = age;
	}

	public void updateName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public Long getId() { return id;}

	public Integer getAge() {
		return age;
	}

	public void loanBook(String bookName) {
		this.userLoanHistories.add(new UserLoanHistory(this,bookName));
	}// 도메인 계츨에 비즈니스 로직이 들어갔다고 표현하기도 한다
	//이미 반납한것도 히스토리에 저장되어있을텐데
	// 만약 히스토리에 책이름 유저이름 반납완료 0 이면 어떡할건데?
	public void returnBook(String bookName) {
		UserLoanHistory targetHistory = this.userLoanHistories.stream()
			.filter(history -> history.getBookName().equals(bookName))
			.findFirst()
			.orElseThrow(IllegalAccessError::new);  //findFist()는 옵셔널로 반홚나다
		targetHistory.doReturn();
	}
}
