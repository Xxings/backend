package com.hulhul.server.domain.user;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

//@Repository //안써도 된다.. 내부에 Repository 이미 선언되어있음
public interface UserRepo extends JpaRepository<User, Long> {
	// c : create
	// r : read
	// u : update
	// d : delete
	// repository : 저장소
	// <T, ID> : <타입, pk 자료형>

	// 함수 이름 규칙대로
	public Optional<User> findByEmail(String email);

	// 닉네임 중복체크 
	public Optional<User> findByNickname(String nickname);

	public List<User> findByNicknameLike(String nickname);

}
