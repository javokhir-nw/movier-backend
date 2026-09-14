package com.javier.movier.cache;

import com.javier.movier.movie.Movie;
import com.javier.movier.movie.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ViewService {
    private final RedisTemplate<String, Object> redisTemplate;
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


    @Scheduled(fixedRate = 60000)
    public void syncViewsToDb() {
        Set<String> keys = redisTemplate.keys("movie:*:views");
        if (keys == null || keys.isEmpty()) return;

        for (String key : keys) {
            String movieIdStr = key.split(":")[1];
            UUID movieId = UUID.fromString(movieIdStr);

            Object valueObj = redisTemplate.opsForValue().get(key);
            int views = valueObj != null ? Integer.parseInt(valueObj.toString()) : 0;

            Movie movie = movieRepository.findById(movieId).orElse(null);
            if (movie != null) {
                movie.setViewCount(movie.getViewCount() + views);
                movieRepository.save(movie);
            }

            redisTemplate.delete(key);
        }
    }
}
