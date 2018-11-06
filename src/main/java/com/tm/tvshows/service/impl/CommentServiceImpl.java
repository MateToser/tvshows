package com.tm.tvshows.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tm.tvshows.entity.Comment;
import com.tm.tvshows.entity.Show;
import com.tm.tvshows.entity.User;
import com.tm.tvshows.entity.UserPrincipal;
import com.tm.tvshows.repository.CommentRepository;
import com.tm.tvshows.repository.ShowRepository;
import com.tm.tvshows.repository.UserRepository;
import com.tm.tvshows.service.api.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentRepository commentRepository;
	@Autowired
	private ShowRepository showRepository;
	@Autowired
	private UserRepository userRepository;

	@Override
	public Comment addComment(Integer showId, String message, UserPrincipal currentUser) {
		Show show = showRepository.findById(showId).get();
		User user = userRepository.findById(currentUser.getId()).get();
		Comment comment = new Comment();
		comment.setMessage(message);
		comment.setDate(new Date());
		comment.setShow(show);
		comment.setUser(user);
		commentRepository.save(comment);
		return comment;
	}

}
