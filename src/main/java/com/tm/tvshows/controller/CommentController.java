package com.tm.tvshows.controller;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.tm.tvshows.common.View;
import com.tm.tvshows.entity.Comment;
import com.tm.tvshows.entity.CurrentUser;
import com.tm.tvshows.entity.UserPrincipal;
import com.tm.tvshows.service.api.CommentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comment")
@Slf4j
public class CommentController {

	@Autowired
	private CommentService commentService;

	@PostMapping(value = "/{showId}/{message}")
	@ResponseBody
	@JsonView(View.Comment.class)
	public ResponseEntity<Comment> addComment(@PathVariable(value = "showId") Integer showId,
			@PathVariable(value = "message") String message, @CurrentUser UserPrincipal currentUser) {
		try {
			Comment commentResponse = commentService.addComment(showId, message, currentUser);
			if (commentResponse == null) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			return ResponseEntity.ok(commentResponse);
		} catch (InternalServerErrorException e) {
			log.error("Nem sikerült a komment létrehozása: {}", "/api/comment/" + showId);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (BadRequestException e) {
			log.error("Nem sikerült a komment létrehozása: {}", "/api/comment/" + showId);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (NotFoundException e) {
			log.error("Nem sikerült a komment létrehozása: {}", "/api/comment/" + showId);
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

}
