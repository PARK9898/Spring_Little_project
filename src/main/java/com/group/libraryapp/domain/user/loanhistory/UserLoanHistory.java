package com.group.libraryapp.domain.user.loanhistory;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class UserLoanHistory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id = null;

	private long userId;

	protected UserLoanHistory() {

	}
	public UserLoanHistory(long userId, String bookName, boolean isReturn) {
		this.userId = userId;
		this.bookName = bookName;
		this.isReturn = isReturn;
	}

	public void doReturn() {
		this.isReturn = true;
	}
	private String bookName;

	private boolean isReturn; // boolean으로 처리하면 tinyint에 잘 매핑된다

}
