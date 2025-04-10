package co.com.nequi.webclient.adapter;

import co.com.nequi.model.user.User;
import co.com.nequi.model.user.exceptions.UserWebClientNotFoundException;
import co.com.nequi.model.user.exceptions.UserWebClientServerErrorException;
import co.com.nequi.model.user.gateways.IUserWebClientGateway;
import co.com.nequi.webclient.dto.Data;
import co.com.nequi.webclient.dto.UserApiResponse;
import co.com.nequi.webclient.mappers.IUserWebClientMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import reactor.core.publisher.Mono;
import org.springframework.web.reactive.function.client.WebClient;

import static co.com.nequi.model.user.exceptions.ExceptionsEnum.ERROR_USER_WEB_CLIENT;
import static co.com.nequi.model.user.exceptions.ExceptionsEnum.USER_WEB_CLIENT_NOT_FOUND;

@RequiredArgsConstructor
public class UserWebClientAdapter implements IUserWebClientGateway {

    private final WebClient webClient;
    private final IUserWebClientMapper userWebClientMapper;

    @Override
    public Mono<User> findById(Long id) {
        return  webClient.get().uri("users/" + id)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        response -> Mono.just(new UserWebClientNotFoundException(USER_WEB_CLIENT_NOT_FOUND.getMessage())))
                .onStatus(HttpStatusCode::is5xxServerError,
                        response -> Mono.just(new UserWebClientServerErrorException(ERROR_USER_WEB_CLIENT.getMessage())))
                .bodyToMono(Data.class)
                .map(userWebClientMapper::toUser);

    }
}
