package com.hulhul.server.domain.post;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hulhul.server.domain.anonymous.AnonymousStatus;
import com.hulhul.server.domain.category.Category;
import com.hulhul.server.domain.talk.Talk;
import com.hulhul.server.domain.time.TimeEntity;
import com.hulhul.server.domain.user.User;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
//@Setter //Entity 클래스에서는 절대 Setter 메소드를 만들지 않는다. (자바빈 인스턴스 값 변경때문에라도...)
@RequiredArgsConstructor
@Entity
@Table(name = "Posts")
public class Post extends TimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // Entity PK (auto_increment) : GenerationType.IDENTITY)
	@Column(name = "p_id")
	private Long id;

	// N : 1 post -> user
	@ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
	@JoinColumn(name = "u_id", nullable = false, updatable = false) // 단방향
	private User user; // 작성자

	// 1 : N post <- Conversation
	// 순서 있어야함
	// ToMany는 default가 지연로딩이므로 설정 안해도 된다.
	// CascadeType.. persist에서 영속성 관련부분 - 참조 객체 !주의
	@OneToMany(mappedBy = "post", cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
	private List<Talk> talkList = new ArrayList<>();

	// N : 1 Post -> Category
	// CascadeType.. persist에서 영속성 관련부분 - 참조 객체 !주=GenerationType의
	// The unsaved transient entity must be saved in an operation prior to saving
	// these dependent entities
	@ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
	@JoinColumn(name = "ca_id", nullable = false)
	private Category category;

	@Column(length = 500, nullable = false)
	private String title;

	@Column(length = 3000, nullable = false)
	private String contents;

//	@Enumerated(EnumType.STRING)
//	private PostStatus status; // POST 상태

//	@Enumerated(EnumType.STRING)
//	private AnonymousStatus anonymous; // 유저 익명 상태

	// 연관관계 메서드
//	public void addConversation(Talk talk) {
//		talkList.add(talk);
//		Talk.setPost(this);
//	}

//	public void setCategory(Category category) {
//		this.category = category;
//	}

	@Builder
	public Post(String title, String contents, Category category, User user) {
		this.title = title;
		this.contents = contents;
		this.user = user;
		this.category = category;
	}

	// Test용 Lombok toString은 양방향 매핑때문에 무한루프 늪에 빠지더라..
	@Override
	public String toString() {
		return "Post [id=" + id + ", title=" + title + "contents=" + contents + "]";
	}

}
