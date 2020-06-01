package com.hulhul.server.web.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.hulhul.server.domain.category.Category;
import com.hulhul.server.domain.category.CategoryRepo;
import com.hulhul.server.domain.post.Post;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true) // 데이터의 변경이 없는 읽기 전용 메서드에 사용, 속성 컨텍스트를 플러시 하지 않으므로 약간의 성능 향상(읽기 전용에는 다 적용)
@RequiredArgsConstructor // final, @NonNull이 붙은 필드를 파라미터로 받는 생성자를 만들어주는 에너테이션
public class CategoryService {

	// 객체를 필드주입이 아닌 생성자주입으로 넣는것이 좋다.
	private final CategoryRepo categoryRepo;

	public List<Category> getCategoryList() {
		return categoryRepo.findAll();
	}
}