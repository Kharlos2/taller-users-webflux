package co.com.nequi.usecase.user;

import co.com.nequi.model.user.User;
import co.com.nequi.model.user.exceptions.UserNotFoundException;
import co.com.nequi.model.user.gateways.IUserCacheGateway;
import co.com.nequi.model.user.gateways.IUserDBGateway;
import co.com.nequi.model.user.gateways.IUserDynamoTraceabilityGateway;
import co.com.nequi.model.user.gateways.IUserSendMessageGateway;
import co.com.nequi.model.user.gateways.IUserWebClientGateway;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static co.com.nequi.model.user.exceptions.ExceptionsEnum.USER_NOT_FOUND;

@RequiredArgsConstructor
public class UserUseCase {

    private final IUserCacheGateway userCacheGateway;
    private final IUserWebClientGateway userWebClientGateway;
    private final IUserDBGateway userDBGateway;
    private final IUserSendMessageGateway userSendMessageGateway;
    private final IUserDynamoTraceabilityGateway userDynamoTraceabilityGateway;

    public Mono<User> save(Long id) {
        return userDBGateway.findByIdApi(id)
                .switchIfEmpty(
                        userWebClientGateway.findById(id)
                                .flatMap(user -> userDBGateway.save(user)
                                        .flatMap(savedUser -> userCacheGateway.save(savedUser)
                                                .flatMap(cachedUser -> userSendMessageGateway.send(cachedUser)
                                                        .thenReturn(cachedUser)
                                                )
                                        )
                                )
                );
    }

    public Mono<User> findById(Long id){
        return userCacheGateway.findById(id)
                .switchIfEmpty(
                        userDBGateway.findByIdApi(id)
                                .switchIfEmpty(Mono.error(new UserNotFoundException(USER_NOT_FOUND.getMessage())))
                                .flatMap(userCacheGateway::save)
                );
    }

    public Flux<User> findAll(){
        return userDBGateway.findAll();
    }

    public Flux<User> filterByName(String name){
        return userDBGateway.filterByName(name);
    }

    public Mono<Void> saveTraceability(User user){
        return userDynamoTraceabilityGateway.save(user).then();
    }
}
