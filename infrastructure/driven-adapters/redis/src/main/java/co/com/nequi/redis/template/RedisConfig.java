package co.com.nequi.redis.template;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public ReactiveRedisOperations<String, UserRedis> redisOperations(ReactiveRedisConnectionFactory factory) {
        Jackson2JsonRedisSerializer<UserRedis> serializer = new Jackson2JsonRedisSerializer<>(UserRedis.class);

        RedisSerializationContext.RedisSerializationContextBuilder<String, UserRedis> builder =
                RedisSerializationContext.newSerializationContext(new StringRedisSerializer());

        RedisSerializationContext<String, UserRedis> context = builder.value(serializer).build();

        return new ReactiveRedisTemplate<>(factory, context);
    }
}
