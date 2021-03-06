package com.hulhul.server.domain.post;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepo extends JpaRepository<Post, Long> {
	// c : create
	// r : read
	// u : update
	// d : delete
	// repository : 저장소
	// <T, ID> : <타입, pk 자료형>

	// TODO : 공개 Status 검색
//	Post findByIdAndStatus(Long id, PostStatus status);

	@Query(value = "select * from posts	where sovled_id = ?1 and status = ?2", countQuery = "select count(*) from posts	where sovled_id = ?1 and status = ?2", nativeQuery = true)
	List<Post> findPostAnswerList(Long sovled_id, String status, Pageable pageable);

	@Query(value = "select count(*) from posts	where sovled_id = ?1 and status = ?2", nativeQuery = true)
	Integer findPostAnswerListSize(Long sovled_id, String status);
	
	/**
	 * u_id
	 * @param sovled_id
	 * @param status
	 * @param pageable
	 * @return
	 */
	@Query(value = "select * from posts	where u_id = ?1", countQuery = "select count(*) from posts	where u_id = ?1", nativeQuery = true)
	List<Post> findByUser_Id(Long sovled_id, Pageable pageable);
	
	@Query(value = "select count(*) from posts	where u_id = ?1", nativeQuery = true)
	Integer findByUser_IdSize(Long u_id);


}
