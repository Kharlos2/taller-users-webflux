package co.com.nequi.r2dbc;

import co.com.nequi.model.user.User;
import co.com.nequi.model.user.gateways.IUserDBGateway;
import co.com.nequi.r2dbc.entity.UserEntity;
import co.com.nequi.r2dbc.helper.ReactiveAdapterOperations;
import co.com.nequi.r2dbc.repository.IUserRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class UserRepositoryAdapter extends ReactiveAdapterOperations<
        User,
        UserEntity,
        Long,
        IUserRepository
>
implements IUserDBGateway {
    public UserRepositoryAdapter(IUserRepository repository, ObjectMapper mapper) {
        /**
         *  Could be use mapper.mapBuilder if your domain model implement builder pattern
         *  super(repository, mapper, d -> mapper.mapBuilder(d,ObjectModel.ObjectModelBuilder.class).build());
         *  Or using mapper.map with the class of the object model
         */
        super(repository, mapper, userEntity -> mapper.map(userEntity, User.class));
    }

    @Override
    public Mono<User> findByIdApi(Long id) {
        return repository.findByIdApi(id)
                .map(this::toEntity);
    }

    @Override
    public Flux<User> filterByName(String name) {
        return repository.findByFirstNameContainingIgnoreCase(name)
                .map(this::toEntity);
    }
}
