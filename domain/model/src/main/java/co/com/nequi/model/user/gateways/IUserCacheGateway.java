package co.com.nequi.model.user.gateways;

import co.com.nequi.model.user.User;
import reactor.core.publisher.Mono;

public interface IUserCacheGateway {
    Mono<User> findById(Long id);
    Mono<User> save (User user);
}
