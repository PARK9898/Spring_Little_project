package com.group.libraryapp.service.book;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.group.libraryapp.domain.book.Book;
import com.group.libraryapp.domain.book.BookRepository;
import com.group.libraryapp.domain.user.User;
import com.group.libraryapp.domain.user.UserRepository;
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistory;
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistoryRepository;
import com.group.libraryapp.dto.book.request.BookCreateRequest;
import com.group.libraryapp.dto.book.request.BookLoanRequest;
import com.group.libraryapp.dto.book.request.BookReturnRequest;

@Service
public class BookService {
	private final BookRepository bookRepository;
	private final UserLoanHistoryRepository userLoanHistoryRepository;
	private final UserRepository userRepository;
	public BookService(
		BookRepository bookRepository,
		UserLoanHistoryRepository userLoanHistoryRepository,
		UserRepository userRepository) {
		this.bookRepository = bookRepository;
		this.userLoanHistoryRepository = userLoanHistoryRepository;
		this.userRepository = userRepository;
	}
	@Transactional
	public void saveBook(BookCreateRequest request) {
		bookRepository.save(new Book(request.getName()));
	}
	@Transactional
	public void loanBook(BookLoanRequest request) {
		// 1. 책 정보를 가져온다
		// 2. 대출기록 정보를 확인해서 대출중인지 확인
		// 3. 만약에 확인했는데 대출중이라면 에러를 발생시킨다
		Book book = bookRepository.findByName(request.getBookName())
			.orElseThrow(IllegalArgumentException::new);
		if(userLoanHistoryRepository.existsByBookNameAndIsReturn(book.getName(),false)) {
			throw new IllegalArgumentException("진작 대출되어 있는 책입니다");
		}
		User user = userRepository.findByName(request.getUserName())
			.orElseThrow(() -> new IllegalArgumentException());

		userLoanHistoryRepository.save(new UserLoanHistory(user.getId(),book.getName(),false));
		//4. 유저 정보를 가져온다
		//5. 유저 정보와 책 정보를 기반으로 히스토리 작성
	}
	@Transactional
	public void returnBook(BookReturnRequest request) {
		User user = userRepository.findByName(request.getUserName())
			.orElseThrow(IllegalArgumentException::new);

		UserLoanHistory userLoanHistory = userLoanHistoryRepository.findByUserIdAndBookName(user.getId(),request.getBookName())
			.orElseThrow(IllegalArgumentException::new);


		userLoanHistory.doReturn(); // 이런 이제 자신의 필드 값 자체를 바꾸는 행위는 보통 엔티티 딴에서 메소드를 만들어준다 왜냐면 외부에서 setter 나 getter를 쓰는것은 안좋기 때문

		//영속성 컨텍스트 떄문에 자동 업데이트 때문에 userLoanHistoryRepository.save(history) 안해줘도 된다
	}

}
