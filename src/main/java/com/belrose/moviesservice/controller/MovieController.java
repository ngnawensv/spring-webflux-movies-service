package com.belrose.moviesservice.controller;

import com.belrose.moviesservice.client.MoviesInfoRestClient;
import com.belrose.moviesservice.client.ReviewsRestClient;
import com.belrose.moviesservice.domain.Movie;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/movies")
@Slf4j
public class MovieController {

  private MoviesInfoRestClient moviesInfoRestClient;
  private ReviewsRestClient reviewsRestClient;

  public MovieController(MoviesInfoRestClient moviesInfoRestClient,
      ReviewsRestClient reviewsRestClient) {
    this.moviesInfoRestClient = moviesInfoRestClient;
    this.reviewsRestClient = reviewsRestClient;
  }


  @GetMapping("/{id}")
  public Mono<Movie> retrieveMovieById(@PathVariable("id") String movieId){
    return moviesInfoRestClient.retrieveMovieInfo(movieId)
        .flatMap(movieInfo -> {
         var reviewsListMono= reviewsRestClient.retrieveReviews(movieId).collectList();
         return reviewsListMono.map(reviews -> new Movie(movieInfo,reviews));
        });
  }

}
