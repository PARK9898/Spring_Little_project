package com.group.libraryapp.domain.user.loanhistory;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.group.libraryapp.domain.user.User;

@Entity
public class UserLoanHistory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id = null;
	private boolean isReturn;

	@ManyToOne // 내가 다수이고 너가 하나 대출기록은 여러개이고 그 대출기록을 소유하고 있는 사용자는 한명이다
	private User user;

	protected UserLoanHistory() {

	}
	public UserLoanHistory(User user, String bookName) {
		this.user = user;
		this.bookName = bookName;
		this.isReturn = false;
	}

	public void doReturn() {
		this.isReturn = true;
	}

	private String bookName;

	public String getBookName() {
		return bookName;
	}

	public boolean isReturn() {
		return isReturn;
	}
	// boolean으로 처리하면 tinyint에 잘 매핑된다

}
