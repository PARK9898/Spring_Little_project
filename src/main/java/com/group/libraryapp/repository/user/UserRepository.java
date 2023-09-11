package com.group.libraryapp.repository.user;

import org.springframework.jdbc.core.JdbcTemplate;

public class UserRepository {
	public boolean isUserNotExist(JdbcTemplate jdbcTemplate, long id) {
		String readSql = " SELECT * FROM user WHERE id = ?"; // 조회됐을때 결과가 있으면 무조건 0을 반환 // 밑에 쿼리는 리스트가 결과로 나오게 되는데 결과가 있으면 0으로 된 리스트 결과가 없다면  비어있는 리스트가 나옴
		return jdbcTemplate.query(readSql,(rs, rowNum) -> 0 , id).isEmpty();
	}
}
