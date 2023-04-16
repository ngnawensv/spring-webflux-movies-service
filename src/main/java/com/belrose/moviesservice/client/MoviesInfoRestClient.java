package com.belrose.moviesservice.client;

import com.belrose.moviesservice.domain.MovieInfo;
import com.belrose.moviesservice.exception.MovieInfoClientException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class MoviesInfoRestClient {

  private WebClient webClient;

  @Value("${restClient.moviesInfo.url}")
  private String moviesInfoUrl;

  public MoviesInfoRestClient(WebClient webClient) {
    this.webClient = webClient;
  }

  public Mono<MovieInfo> retrieveMovieInfo(String movieId) {
    var url = moviesInfoUrl.concat("/{id}");
    return webClient.get()
        .uri(url, movieId)
        .retrieve()
        .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> {
          log.info("Status code is : {}",clientResponse.statusCode().value());
          if (clientResponse.statusCode().equals(HttpStatus.NOT_FOUND)) {
            return Mono.error(new MovieInfoClientException(
                "There is no MovieInfo available for the passed in id " + movieId,
                clientResponse.statusCode().value()));
          }
          return clientResponse.bodyToMono(String.class)
              .flatMap(responseMessage -> Mono.error(new MovieInfoClientException(responseMessage,
                  clientResponse.statusCode().value())));
        })
        .bodyToMono(MovieInfo.class)
        .log();

  }
}
