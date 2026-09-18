package com.javier.movier.cache;

import com.javier.movier.category.CategoryDto;
import com.javier.movier.movie.MovieResponseDto;
import com.javier.movier.source.SourceDto;
import com.javier.movier.utils.PageWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.JacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.JavaType;
import tools.jackson.databind.ObjectMapper;

import java.time.Duration;
import java.util.List;
import java.util.Map;

@Configuration
@EnableCaching
@RequiredArgsConstructor
public class RedisConfig {

    private final ObjectMapper objectMapper;

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory factory) {

        Map<String, RedisCacheConfiguration> configs = Map.of(
                "categories",
                configFor(
                        new TypeReference<List<CategoryDto>>() {},
                        Duration.ofDays(1)
                ),

                "sources",
                configFor(
                        new TypeReference<List<SourceDto>>() {},
                        Duration.ofDays(1)
                ),

                "movies",
                configFor(
                        MovieResponseDto.class,
                        Duration.ofHours(10)
                ),

                "moviesList",
                configFor(
                        PageWrapper.class,
                        Duration.ofHours(10)
                )
        );

        return RedisCacheManager.builder(factory)
                .cacheDefaults(defaultConfig(Duration.ofDays(1)))
                .withInitialCacheConfigurations(configs)
                .build();
    }

    private RedisCacheConfiguration configFor(
            Class<?> type,
            Duration ttl
    ) {
        return defaultConfig(ttl)
                .serializeValuesWith(
                        serializationPair(
                                new JacksonJsonRedisSerializer<>(
                                        objectMapper,
                                        type
                                )
                        )
                );
    }

    private RedisCacheConfiguration configFor(
            TypeReference<?> typeRef,
            Duration ttl
    ) {
        JavaType javaType =
                objectMapper.getTypeFactory()
                        .constructType(typeRef);

        return defaultConfig(ttl)
                .serializeValuesWith(
                        serializationPair(
                                new JacksonJsonRedisSerializer<>(
                                        objectMapper,
                                        javaType
                                )
                        )
                );
    }

    private RedisCacheConfiguration defaultConfig(Duration ttl) {
        return RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(ttl)
                .disableCachingNullValues();
    }

    private <T> RedisSerializationContext.SerializationPair<T> serializationPair(
            RedisSerializer<T> serializer
    ) {
        return RedisSerializationContext.SerializationPair.fromSerializer(serializer);
    }
}