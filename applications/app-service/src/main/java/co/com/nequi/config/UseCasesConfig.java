package co.com.nequi.config;

import co.com.nequi.model.user.gateways.IUserCacheGateway;
import co.com.nequi.model.user.gateways.IUserDBGateway;
import co.com.nequi.model.user.gateways.IUserWebClientGateway;
import co.com.nequi.r2dbc.repository.IUserRepository;
import co.com.nequi.r2dbc.UserRepositoryAdapter;
import co.com.nequi.redis.template.ReactiveRedisTemplateAdapter;
import co.com.nequi.usecase.user.UserUseCase;
import co.com.nequi.webclient.adapter.UserWebClientAdapter;
import co.com.nequi.webclient.mappers.IUserWebClientMapper;
import lombok.RequiredArgsConstructor;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@RequiredArgsConstructor
public class UseCasesConfig {

        private final WebClient webClient;
        private final IUserWebClientMapper userWebClientMapper;

        @Bean
        public IUserWebClientGateway userWebClientGateway(){
                return new UserWebClientAdapter(webClient,userWebClientMapper);
        }

        @Bean
        public IUserDBGateway userDBGateway(IUserRepository repository, ObjectMapper mapper) {
                return new UserRepositoryAdapter(repository, mapper);
        }

        @Bean
        IUserCacheGateway userCacheGateway(ReactiveRedisConnectionFactory connectionFactory,
                                           ObjectMapper mapper){
                return new ReactiveRedisTemplateAdapter(connectionFactory, mapper);
        }
        @Bean
        public UserUseCase userUseCase(IUserDBGateway userDBGateway, IUserWebClientGateway userWebClientGateway, IUserCacheGateway userCacheGateway){
                return new UserUseCase(userCacheGateway,userWebClientGateway,userDBGateway);
        }
}
