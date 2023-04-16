package com.belrose.moviesservice.client;

import com.belrose.moviesservice.domain.Review;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;

@Component
public class ReviewsRestClient {

  private WebClient webClient;

  @Value("${restClient.reviews.url}")
  private String reviewsUrl;

  public ReviewsRestClient(WebClient webClient) {
    this.webClient = webClient;
  }

  public Flux<Review> retrieveReviews(String movieId){

    //Consume the endpoint with query param
    var url =  UriComponentsBuilder.fromHttpUrl(reviewsUrl)
        .queryParam("movieInfo", movieId)
        .buildAndExpand()
        .toUriString();

    return webClient.get()
        .uri(url,movieId)
        .retrieve()
        .bodyToFlux(Review.class)
        .log();

  }
}
