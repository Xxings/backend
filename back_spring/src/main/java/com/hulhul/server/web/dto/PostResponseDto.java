package com.hulhul.server.web.dto;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.hulhul.server.domain.anonymous.AnonymousStatus;
import com.hulhul.server.domain.post.Post;
import com.hulhul.server.domain.post.PostStatus;
import com.hulhul.server.domain.talk.Talk;
import com.hulhul.server.domain.time.TimeEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
//@Builder
//@AllArgsConstructor
//@NoArgsConstructor
public class PostResponseDto {
	private Long id;
	private String title;
	private String contents;
	private String nickName;
	private List<TalkResponseDto> talks; 

	@Enumerated(EnumType.STRING)
	private PostStatus status; // POST 상태

	@Enumerated(EnumType.STRING)
	private AnonymousStatus anonymous; // 유저 익명 상태
	
	private LocalDateTime createdAt;
	
	private LocalDateTime modifiedAt;

	
	// TODO : 닉네임 변경 - 비공개인경우
	@Builder
	public PostResponseDto(Post post) {
		this.id = post.getId();
		this.title = post.getTitle();
		this.contents = post.getContents();
		this.status = post.getStatus();
		this.anonymous = post.getAnonymous();
		this.nickName = doAnonymous(post);
		this.createdAt = post.getCreatedAt();
		this.modifiedAt = post.getModifiedAt();
		System.out.println("first");
	}	

	// TODO : HASHSET RECODE
	public String doAnonymous(Post post) {
		// post_id / user_id
		if (post.getAnonymous() == anonymous)
			return "익명";
		else
			return post.getUser().getNickname();
	}

}
