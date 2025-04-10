package co.com.nequi.model.user.gateways;

import co.com.nequi.model.user.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IUserDBGateway {

    Mono<User> findByIdApi(Long id);
    Mono<User> save(User user);
    Flux<User> findAll();
    Flux<User> filterByName(String name);

}
