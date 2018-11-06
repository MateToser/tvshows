package com.tm.tvshows.controller;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.tm.tvshows.common.View;
import com.tm.tvshows.entity.CurrentUser;
import com.tm.tvshows.entity.UserPrincipal;
import com.tm.tvshows.response.SearchResponse;
import com.tm.tvshows.response.ShowDTO;
import com.tm.tvshows.response.ShowResponse;
import com.tm.tvshows.service.api.ShowService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/show")
@Slf4j
public class ShowController {

	private final ShowService showService;

	@PostMapping(value = "/{title}")
	@ResponseBody
	@JsonView(View.Show.class)
	public ResponseEntity<Integer> addShow(@PathVariable(value = "title") String title) {
		try {
			Integer showId = showService.addShowToDatabase(title);
			if (showId == null) {
				log.error("Nincs ilyen sorozat: {}", "/api/show/" + title);
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			return ResponseEntity.ok(showId);
		} catch (InternalServerErrorException e) {
			log.error("Nem sikerült a sorozat hozzáadása: {}", "/api/show/" + title);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (BadRequestException e) {
			log.error("Nem sikerült a sorozat hozzáadása: {}", "/api/show/" + title);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (NotFoundException e) {
			log.error("Nem sikerült a sorozat hozzáadása: {}", "/api/show/" + title);
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(value = "/id/{id}")
	@ResponseBody
	@JsonView(View.Show.class)
	public ResponseEntity<ShowResponse> getShowById(@PathVariable(value = "id") Integer id,
			@CurrentUser UserPrincipal currentUser) {
		try {
			ShowResponse showResponse = showService.getShowById(id, currentUser);
			if (showResponse == null) {
				log.error("Nincs ilyen sorozat: {}", "/api/show/id/" + id);
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			return ResponseEntity.ok(showResponse);
		} catch (InternalServerErrorException e) {
			log.error("Nem sikerült a sorozat lekérdezés: {}", "/api/show/id/" + id);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (BadRequestException e) {
			log.error("Nem sikerült a sorozat lekérdezés: {}", "/api/show/id/" + id);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (NotFoundException e) {
			log.error("Nem sikerült a sorozat lekérdezés: {}", "/api/show/id/" + id);
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(value = "/all")
	@ResponseBody
	@JsonView(View.Show.class)
	public ResponseEntity<List<ShowResponse>> getAllShow(@CurrentUser UserPrincipal currentUser) {
		try {
			List<ShowResponse> showsResponse = showService.getAllShows(currentUser);
			if (showsResponse == null) {
				log.error("Nincsenek sorozatok: {}", "/api/show/all");
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			return ResponseEntity.ok(showsResponse);
		} catch (InternalServerErrorException e) {
			log.error("Nem sikerült a sorozatok lekérdezés: {}", "/api/show/all");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (BadRequestException e) {
			log.error("Nem sikerült a sorozatok lekérdezés: {}", "/api/show/all");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (NotFoundException e) {
			log.error("Nem sikerült a sorozatok lekérdezés: {}", "/api/show/all");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(value = "/search/{title}")
	@ResponseBody
	public ResponseEntity<List<SearchResponse>> searchShows(@PathVariable(value = "title") String title) {
		try {
			List<SearchResponse> showsResponse = new ArrayList<>();
			showsResponse = showService.searchShows(title);
			return ResponseEntity.ok(showsResponse);
		} catch (InternalServerErrorException e) {
			log.error("Nem sikerült a sorozatok lekérdezés: {}", "/api/show/search");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (BadRequestException e) {
			log.error("Nem sikerült a sorozatok lekérdezés: {}", "/api/show/search");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (NotFoundException e) {
			log.error("Nem sikerült a sorozatok lekérdezés: {}", "/api/show/search");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping(value = "/like/{id}")
	@ResponseBody
	public ResponseEntity<Boolean> likeShow(@CurrentUser UserPrincipal currentUser,
			@PathVariable(value = "id") Integer id) throws Exception {
		try {
			Boolean likeResponse = showService.likeShow(id, currentUser);
			return ResponseEntity.ok(likeResponse);
		} catch (InternalServerErrorException e) {
			log.error("Nem sikerült a sorozat kedvelése: {}", "/api/show/like/" + id);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (BadRequestException e) {
			log.error("Nem sikerült a sorozat kedvelése: {}", "/api/show/like/" + id);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (NotFoundException e) {
			log.error("Nem sikerült a sorozat kedvelése: {}", "/api/show/like/" + id);
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(value = "/{order}/{page}")
	@ResponseBody
	@JsonView(View.Show.class)
	public ResponseEntity<ShowDTO> getOrderedShows(@CurrentUser UserPrincipal currentUser,
			@PathVariable(value = "order") String order, @PathVariable(value = "page") Integer page) {
		try {
			ShowDTO shows = showService.getOrderedShows(order, page, currentUser);
			return ResponseEntity.ok(shows);
		} catch (InternalServerErrorException e) {
			log.error("Nem sikerült a sorozatok lekérdezés: /api/show/{}/{}/{}", order, page);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (BadRequestException e) {
			log.error("Nem sikerült a sorozatok lekérdezés: /api/show/{}/{}/{}", order, page);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (NotFoundException e) {
			log.error("Nem sikerült a sorozatok lekérdezés: /api/show/{}/{}/{}", order, page);
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

}
