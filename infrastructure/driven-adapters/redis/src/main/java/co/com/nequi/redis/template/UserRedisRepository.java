//package co.com.nequi.redis.template;
//
//import co.com.nequi.model.user.User;
//import co.com.nequi.model.user.gateways.IUserCacheGateway;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.redis.core.ReactiveRedisOperations;
//import org.springframework.stereotype.Component;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//
//@Component
//@RequiredArgsConstructor
//public class UserRedisRepository implements IUserCacheGateway {
//
//    private final ReactiveRedisOperations<String, UserRedis> redisOperations;
//    private final IUserRedisMapper userRedisMapper;
//
//    @Override
//    public Mono<User> findById(Long id) {
//        String key = generateKeyById(id);
//        System.out.println(key);
//        return redisOperations.opsForValue()
//                .get(key)
//                .map(userRedisMapper::toUser);
//    }
//
//    @Override
//    public Mono<User> save(User user) {
//        String keyById = generateKeyById(user.getIdApi());
//        String keyByName = generateKeyByName(user.getFirstName());
//        UserRedis userRedis = userRedisMapper.toUserJson(user);
//
//        return redisOperations.opsForValue()
//                .set(keyById, userRedis)
//                .then(redisOperations.opsForValue().set(keyByName, userRedis))
//                .thenReturn(user);
//    }
//
//    @Override
//    public Flux<User> filterByName(String name) {
//        String key = generateKeyByName(name);
//        return redisOperations.opsForValue()
//                .get(key)
//                .map(userRedisMapper::toUser)
//                .flux(); // Convert Mono<User> → Flux<User>
//    }
//
//    private String generateKeyById(Long id) {
//        return String.format("user:id:%d", id);
//    }
//
//    private String generateKeyByName(String name) {
//        return String.format("user:name:%s", name.toLowerCase());
//    }
//}
