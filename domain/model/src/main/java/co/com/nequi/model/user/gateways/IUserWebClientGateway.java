package co.com.nequi.model.user.gateways;

import co.com.nequi.model.user.User;
import reactor.core.publisher.Mono;

public interface IUserWebClientGateway {

    Mono<User> findById(Long id);

}
