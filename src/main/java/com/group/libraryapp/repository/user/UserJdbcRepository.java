package com.group.libraryapp.repository.user;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.group.libraryapp.dto.user.response.UserResponse;

@Repository
public class UserJdbcRepository {

	private final JdbcTemplate jdbcTemplate;

	public UserJdbcRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}


	public void updateUserName(String name, long id) {
		String sql = "UPDATE user SET name = ? WHERE id = ?";
		jdbcTemplate.update(sql,name,id);
	}

	public void deleteUser(String name) {
		String sql = "DELETE FROM user WHERE name = ?";
		jdbcTemplate.update(sql,name);
	}

	public boolean isUserNotExist(String name) {
		String readSql = " SELECT * FROM user WHERE id = ?";
		return jdbcTemplate.query(readSql,(rs, rowNum) -> 0 , name).isEmpty();
	}

	public void saveUser(String name, int age) {
		String sql = "INSERT INTO user (name , age) VALUE (?,?)"; // 물을표에 하나씩 들어간다
		jdbcTemplate.update(sql ,name , age);
	}

	public List<UserResponse> getUser() {
		String sql = "SELECT * FROM user"; //  user 정보를 우리가 선언한 타입으로 바꿔주는 함수
		return jdbcTemplate.query(sql, (rs, rowNum) -> {
			long id = rs.getLong("id");
			String name = rs.getString("name");
			int age = rs.getInt("age");
			return new UserResponse(id,name,age);
		});
	}

	public boolean isUserNotExist(long id) {
		String readSql = " SELECT * FROM user WHERE id = ?"; // 조회됐을때 결과가 있으면 무조건 0을 반환 // 밑에 쿼리는 리스트가 결과로 나오게 되는데 결과가 있으면 0으로 된 리스트 결과가 없다면  비어있는 리스트가 나옴
		return jdbcTemplate.query(readSql,(rs, rowNum) -> 0 , id).isEmpty();
	}
}
