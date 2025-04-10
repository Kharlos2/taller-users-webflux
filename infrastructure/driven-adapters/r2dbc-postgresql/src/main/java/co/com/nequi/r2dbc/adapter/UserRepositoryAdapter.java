package co.com.nequi.r2dbc.adapter;

import co.com.nequi.model.user.User;
import co.com.nequi.model.user.gateways.IUserDBGateway;
import co.com.nequi.r2dbc.mapper.IUserAdapterMapper;
import co.com.nequi.r2dbc.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class UserRepositoryAdapter implements IUserDBGateway {

    private final IUserRepository userRepository;
    private final IUserAdapterMapper userAdapterMapper;

    @Override
    public Mono<User> findByIdApi(Long id) {
        return userRepository.findByIdApi(id)
                .map(userAdapterMapper::toUser);
    }

    @Override
    public Mono<User> save(User user) {
        return userRepository.save(userAdapterMapper.toUserEntity(user))
                .map(userAdapterMapper::toUser);
    }

    @Override
    public Flux<User> findAll() {
        return userRepository.findAll()
                .map(userAdapterMapper::toUser);
    }

    @Override
    public Flux<User> filterByName(String name) {
        return userRepository.findByFirstNameContainingIgnoreCase(name)
                .map(userAdapterMapper::toUser);
    }
}
