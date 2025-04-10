package co.com.nequi.r2dbc.repository;

import co.com.nequi.r2dbc.entity.UserEntity;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IUserRepository extends ReactiveCrudRepository<UserEntity, Long>, ReactiveQueryByExampleExecutor<UserEntity> {

    Flux<UserEntity> findByFirstNameContainingIgnoreCase(String name);
    Mono<UserEntity> findByIdApi(Long id);

}
