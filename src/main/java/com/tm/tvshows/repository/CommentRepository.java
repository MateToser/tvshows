package com.tm.tvshows.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.tm.tvshows.entity.Comment;

@Repository
public interface CommentRepository extends PagingAndSortingRepository<Comment, Integer> {

	List<Comment> findByShowIdOrderByDateAsc(Integer id);

}
