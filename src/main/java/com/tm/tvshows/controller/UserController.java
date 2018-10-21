package com.tm.tvshows.controller;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.tm.tvshows.common.View;
import com.tm.tvshows.entity.CurrentUser;
import com.tm.tvshows.entity.User;
import com.tm.tvshows.entity.UserPrincipal;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
@Slf4j
public class UserController {

	@GetMapping(value = "/me")
	@ResponseBody
	@JsonView(View.User.class)
	public ResponseEntity<User> getCurrentUser(@CurrentUser UserPrincipal currentUser) {
		try {
			User user = new User(currentUser);
			return ResponseEntity.ok(user);
		} catch (InternalServerErrorException e) {
			log.error("Nem sikerült a felhasználó lekérdezés: {}", "/api/user/me");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (BadRequestException e) {
			log.error("Nem sikerült a felhasználó lekérdezés: {}", "/api/user/me");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (NotFoundException e) {
			log.error("Nem sikerült a felhasználó lekérdezés: {}", "/api/user/me");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
