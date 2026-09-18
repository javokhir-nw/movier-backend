package com.javier.movier.cache;

import com.javier.movier.movie.Movie;
import com.javier.movier.movie.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ViewService {
    private final StringRedisTemplate redisTemplate;
    private final MovieRepository movieRepository;

    public void increment(String viewerId, String movieId) {
        String viewedKey = "viewed:" + viewerId + ":" + movieId;
        Boolean isNew = redisTemplate.opsForValue()
                .setIfAbsent(viewedKey, "1", Duration.ofHours(72));

        if (Boolean.TRUE.equals(isNew)) {
            String countKey = "movie:" + movieId + ":views";
            redisTemplate.opsForValue().increment(countKey);
        }
    }


    @Scheduled(fixedRate = 3_600_000)
    public void syncViewsToDb() {
        if (redisTemplate.getConnectionFactory() != null) {
            Set<String> keySet = redisTemplate.keys("movie:*:views");
            if (keySet == null || keySet.isEmpty()) return;

            List<String> redisKeys = new ArrayList<>(keySet);
            List<UUID> movieIds = redisKeys.stream()
                    .map(s -> s.split(":")[1])
                    .map(UUID::fromString)
                    .toList();

            List<String> values = redisTemplate.opsForValue().multiGet(redisKeys);

            Map<UUID, Movie> movies = movieRepository.findAllById(movieIds)
                    .stream().collect(Collectors.toMap(Movie::getId, Function.identity()));

            for (int i = 0; i < movieIds.size(); i++) {
                UUID movieId = movieIds.get(i);
                String value = values.get(i);
                if (value == null) continue;

                int views = Integer.parseInt(value);
                Movie movie = movies.get(movieId);
                if (movie != null) {
                    movie.setViewCount(movie.getViewCount() + views);
                }
            }

            movieRepository.saveAll(movies.values());
            redisTemplate.delete(redisKeys);
        }
    }
}
