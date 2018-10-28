package com.tm.tvshows.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.tm.tvshows.entity.Category;
import com.tm.tvshows.entity.OmdbResponse;
import com.tm.tvshows.entity.Show;
import com.tm.tvshows.entity.User;
import com.tm.tvshows.entity.UserPrincipal;
import com.tm.tvshows.repository.CategoryRepository;
import com.tm.tvshows.repository.ShowRepository;
import com.tm.tvshows.response.ShowResponse;
import com.tm.tvshows.service.api.OmdbService;
import com.tm.tvshows.service.api.ShowService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShowServiceImpl implements ShowService {

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
			OmdbResponse omdbResponse = omdbService.getOmdbResponse(title);
			showOptional = showRepository.findByTitleContaining(omdbResponse.getTitle());
			/**
			 * Ha alapból az omdb api-n keresztül kapott címet adnám meg, akkor lehet, hogy feleslegesen hívok be oda
			 * ezzel növelve a kérések számát. Így először a user által megadott címmel kérdezek le az adatbázisból, ha
			 * nem találja a cím alapján, akkor bekérdezek az omdb cím alapján is, mivel ez eltérhet.
			 */
			showResponse = showOptional.isPresent() ? showOptional.get() : setShow(omdbResponse);
		}
		return showResponse;
	}

	private Show setShow(OmdbResponse omdbResponse) {
		Show showResponse = new Show();
		showResponse.setTitle(omdbResponse.getTitle());
		showResponse.setDescription(omdbResponse.getPlot());
		showResponse.setReleased(omdbResponse.getReleased());
		showResponse.setWriter(omdbResponse.getWriter());
		showResponse.setAwards(omdbResponse.getAwards());
		showResponse.setImdbRating(omdbResponse.getImdbRatingInDouble());
		showResponse.setImdbVotes(omdbResponse.getImdbVotes());
		showResponse.setImdbId(omdbResponse.getImdbID());
		showResponse.setApproved(false);
		showResponse.setPosterUrl(omdbResponse.getPoster());
		showResponse.setSeasons(omdbResponse.getSeasonsInLong());
		showResponse.setCategories(getCategories(omdbResponse.getGenre()));
		showResponse.setUsers(null);
		showRepository.save(showResponse);
		return showResponse;
	}

	// TODO: bekérdez repoba, hogy létezik-e már a kategória, ha nem elmenti és hozzáadja a set-hez, ha igen akkor csak
	// hozzáadja a sethez
	private Set<Category> getCategories(String categoriesInString) {
		Set<Category> categories = new HashSet();
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
		// TODO: dobjon konkret exceptiont
		throw new Exception("Ezzel az id-val nincs sorozat!");
	}

	@Override
	public Page<Show> getOrderedShows(String order, Integer page, Integer count, UserPrincipal currentUser) {
		Page<Show> show = null;
		if (order.toLowerCase().equals("abc_asc")) {
			Pageable pageable = PageRequest.of(page, count, Sort.by("title").ascending());
			show = showRepository.findAll(pageable);
		} else if (order.toLowerCase().equals("abc_desc")) {
			Pageable pageable = PageRequest.of(page, count, Sort.by("title").descending());
			show = showRepository.findAll(pageable);
		} else if (order.toLowerCase().equals("date_asc")) {
			Pageable pageable = PageRequest.of(page, count, Sort.by("id").ascending());
			show = showRepository.findAll(pageable);
		} else if (order.toLowerCase().equals("date_desc")) {
			Pageable pageable = PageRequest.of(page, count, Sort.by("id").descending());
			show = showRepository.findAll(pageable);
		}
		return show;
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

}
