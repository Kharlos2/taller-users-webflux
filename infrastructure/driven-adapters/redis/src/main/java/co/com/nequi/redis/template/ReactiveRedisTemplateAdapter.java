package co.com.nequi.redis.template;

import co.com.nequi.model.user.User;
import co.com.nequi.model.user.gateways.IUserCacheGateway;
import co.com.nequi.redis.template.helper.ReactiveTemplateAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class ReactiveRedisTemplateAdapter extends ReactiveTemplateAdapterOperations<User, String, UserRedis>
        implements IUserCacheGateway
{
    public ReactiveRedisTemplateAdapter(ReactiveRedisConnectionFactory connectionFactory, ObjectMapper mapper) {
        /**
         *  Could be use mapper.mapBuilder if your domain model implement builder pattern
         *  super(repository, mapper, d -> mapper.mapBuilder(d,ObjectModel.ObjectModelBuilder.class).build());
         *  Or using mapper.map with the class of the object model
         */
        super(connectionFactory, mapper, userRedis -> mapper.map(userRedis, User.class));
    }

    @Override
    public Mono<User> findById(Long id) {
        String key = generateKeyById(id);
        return super.findById(key);
    }

    @Override
    public Mono<User> save(User user) {
        String key = generateKeyById(user.getIdApi());
        return super.save(key, user);
    }

    @Override
    public Flux<User> filterByName(String name) {
        String key = generateKeyByName(name);
        return super.findById(key).flux();
    }

    private String generateKeyById(Long id) {
        return String.format("user:id:%d", id);
    }

    private String generateKeyByName(String name) {
        return String.format("user:name:%s", name.toLowerCase());
    }
}
