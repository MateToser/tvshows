package com.tm.tvshows.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.ws.rs.BadRequestException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.tm.tvshows.entity.Category;
import com.tm.tvshows.entity.Show;
import com.tm.tvshows.entity.User;
import com.tm.tvshows.entity.UserPrincipal;
import com.tm.tvshows.repository.CategoryRepository;
import com.tm.tvshows.repository.ShowRepository;
import com.tm.tvshows.response.OmdbResponse;
import com.tm.tvshows.response.OmdbSeriesResponse;
import com.tm.tvshows.response.ShowDTO;
import com.tm.tvshows.response.ShowResponse;
import com.tm.tvshows.service.api.OmdbService;
import com.tm.tvshows.service.api.ShowService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShowServiceImpl implements ShowService {

	private static final int SHOWS_COUNT = 10;
	private final ShowRepository showRepository;
	private final CategoryRepository categoryRepository;
	private final OmdbService omdbService;

	@Override
	public Show getShowFromDatabase(String title) {
		Show showResponse = new Show();
		Optional<Show> showOptional = showRepository.findByTitleContaining(title);
		if (showOptional.isPresent()) {
			showResponse = showOptional.get();
		} else {
			OmdbResponse omdbSeriesResponse = omdbService.getOmdbResponse(title);
			showOptional = showRepository.findByTitleContaining(omdbSeriesResponse.getShow().getTitle());
			/**
			 * Ha alapból az omdb api-n keresztül kapott címet adnám meg, akkor lehet, hogy feleslegesen hívok be oda
			 * ezzel növelve a kérések számát. Így először a user által megadott címmel kérdezek le az adatbázisból, ha
			 * nem találja a cím alapján, akkor bekérdezek az omdb cím alapján is, mivel ez eltérhet.
			 */
			showResponse = showOptional.isPresent() ? showOptional.get() : setShow(omdbSeriesResponse.getShow());
		}
		return showResponse;
	}

	private Show setShow(OmdbSeriesResponse omdbSeriesResponse) {
		Show showResponse = new Show();
		showResponse.setTitle(omdbSeriesResponse.getTitle());
		showResponse.setDescription(omdbSeriesResponse.getPlot());
		showResponse.setReleased(omdbSeriesResponse.getReleased());
		showResponse.setWriter(omdbSeriesResponse.getWriter());
		showResponse.setAwards(omdbSeriesResponse.getAwards());
		showResponse.setImdbRating(omdbSeriesResponse.getImdbRatingInDouble());
		showResponse.setImdbVotes(omdbSeriesResponse.getImdbVotes());
		showResponse.setImdbId(omdbSeriesResponse.getImdbID());
		showResponse.setApproved(false);
		showResponse.setPosterUrl(omdbSeriesResponse.getPoster());
		showResponse.setTotalSeasons(omdbSeriesResponse.getSeasonsInLong());
		showResponse.setCategories(getCategories(omdbSeriesResponse.getGenre()));
		showResponse.setUsers(null);
		showRepository.save(showResponse);
		return showResponse;
	}

	// TODO: bekérdez repoba, hogy létezik-e már a kategória, ha nem elmenti és hozzáadja a set-hez, ha igen akkor csak
	// hozzáadja a sethez
	private Set<Category> getCategories(String categoriesInString) {
		Set<Category> categories = new HashSet<>();
		String[] categoryArray = categoriesInString.split(",\\s+");
		for (String category : categoryArray) {
			Optional<Category> categoryOptional = categoryRepository.findByType(category);
			if (categoryRepository.findByType(category).isPresent()) {
				categories.add(categoryOptional.get());
			} else {
				Category categoryAdd = new Category();
				categoryAdd.setType(category);
				categoryRepository.save(categoryAdd);
				categories.add(categoryAdd);
			}
		}
		return categories;
	}

	@Override
	public Boolean likeShow(Integer id, UserPrincipal currentUser) throws Exception {
		Optional<Show> show = showRepository.findById(id);
		if (show.isPresent()) {
			if (show.get().getUsers() != null) {
				if (show.get().getUsers().stream().anyMatch(u -> currentUser.getId().equals(u.getId()))) {
					User user = new User(currentUser);
					show.get().getUsers().remove(user);
					showRepository.save(show.get());
					return false;
				}
			}
			User user = new User(currentUser);
			show.get().setUsers(new HashSet<>());
			show.get().getUsers().add(user);
			showRepository.save(show.get());
			return true;
		}
		throw new BadRequestException("Ezzel az id-val nincs sorozat!");
	}

	@Override
	public ShowDTO getOrderedShows(String order, Integer page, UserPrincipal currentUser) {
		ShowDTO showDTO = new ShowDTO();
		List<ShowResponse> showResponses = new ArrayList<>();
		Page<Show> show = null;
		if (order.toLowerCase().equals("abc_asc")) {
			Pageable pageable = PageRequest.of(page, SHOWS_COUNT, Sort.by("title").ascending());
			show = showRepository.findAll(pageable);
		} else if (order.toLowerCase().equals("abc_desc")) {
			Pageable pageable = PageRequest.of(page, SHOWS_COUNT, Sort.by("title").descending());
			show = showRepository.findAll(pageable);
		} else if (order.toLowerCase().equals("date_asc")) {
			Pageable pageable = PageRequest.of(page, SHOWS_COUNT, Sort.by("id").ascending());
			show = showRepository.findAll(pageable);
		} else if (order.toLowerCase().equals("date_desc")) {
			Pageable pageable = PageRequest.of(page, SHOWS_COUNT, Sort.by("id").descending());
			show = showRepository.findAll(pageable);
		}
		for (Show item : show) {
			ShowResponse showAdd = new ShowResponse(item);
			if (item.getUsers().stream().anyMatch(u -> currentUser.getId().equals(u.getId()))) {
				showAdd.setIsLiked(true);
			} else {
				showAdd.setIsLiked(false);
			}
			showResponses.add(showAdd);
		}
		showDTO.setShows(showResponses);
		showDTO.setTotalPage(show.getTotalPages());
		return showDTO;
	}

	@Override
	public List<ShowResponse> getAllShows(UserPrincipal currentUser) {
		List<Show> shows = (List<Show>) showRepository.findAll();
		List<ShowResponse> response = new ArrayList<>();
		for (Show show : shows) {
			ShowResponse showResponse = new ShowResponse(show);
			if (show.getUsers().stream().anyMatch(u -> currentUser.getId().equals(u.getId()))) {
				showResponse.setIsLiked(true);
			} else {
				showResponse.setIsLiked(false);
			}
			response.add(showResponse);
		}
		return response;
	}

	@Override
	public ShowResponse getShowById(Integer id, UserPrincipal currentUser) {
		Optional<Show> showOptional = showRepository.findById(id);
		if (showOptional.isPresent()) {
			ShowResponse showResponse = new ShowResponse(showOptional.get());
			if (showResponse.getShow().getUsers().stream().anyMatch(u -> currentUser.getId().equals(u.getId()))) {
				showResponse.setIsLiked(true);
			} else {
				showResponse.setIsLiked(false);
			}
			return showResponse;
		}
		return null;
	}

}
