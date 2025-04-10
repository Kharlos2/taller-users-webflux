package co.com.nequi.api;

import co.com.nequi.api.dto.ErrorDto;
import co.com.nequi.model.user.User;
import co.com.nequi.model.user.exceptions.UserAlreadyExistException;
import co.com.nequi.model.user.exceptions.UserNotFoundException;
import co.com.nequi.model.user.exceptions.UserWebClientNotFoundException;
import co.com.nequi.model.user.exceptions.UserWebClientServerErrorException;
import co.com.nequi.usecase.user.UserUseCase;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static co.com.nequi.api.utils.Constants.INVALID_LONG_PARAMETER;
import static co.com.nequi.api.utils.Constants.SERVER_ERROR;

@Component
@RequiredArgsConstructor
public class Handler {

    private static final Logger log = LoggerFactory.getLogger(Handler.class);

    private  final UserUseCase userUseCase;


    public Mono<ServerResponse> save(ServerRequest serverRequest) {
        return Mono.just(serverRequest.pathVariable("id"))
                .map(Long::parseLong)
                .flatMap(id ->
                        userUseCase.save(id).
                                flatMap(response -> ServerResponse.status(HttpStatus.CREATED)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(response))
                )
                .onErrorResume(UserAlreadyExistException.class, e ->
                        ServerResponse.status(HttpStatus.CONFLICT).bodyValue(new ErrorDto(e.getMessage())))
                .onErrorResume(UserWebClientNotFoundException.class, e ->
                        ServerResponse.status(HttpStatus.NOT_FOUND).bodyValue(new ErrorDto(e.getMessage())))
                .onErrorResume(NumberFormatException.class, e ->
                        ServerResponse.status(HttpStatus.BAD_REQUEST).bodyValue(new ErrorDto(INVALID_LONG_PARAMETER)))
                .onErrorResume(UserWebClientServerErrorException.class, e ->
                        ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).bodyValue(new ErrorDto(e.getMessage())))
                .onErrorResume(e ->{
                    log.error(e.getMessage(),e);
                    return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).bodyValue(new ErrorDto(SERVER_ERROR));
                });
    }

    public Mono<ServerResponse> findById (ServerRequest serverRequest) {
        return Mono.just(serverRequest.pathVariable("id"))
                .map(Long::parseLong)
                .flatMap(id ->
                        userUseCase.findById(id).
                                flatMap(response -> ServerResponse.status(HttpStatus.OK)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(response))
                )
                .onErrorResume(UserNotFoundException.class, e ->
                        ServerResponse.status(HttpStatus.NOT_FOUND).bodyValue(new ErrorDto(e.getMessage())))
                .onErrorResume(NumberFormatException.class, e ->
                        ServerResponse.status(HttpStatus.BAD_REQUEST).bodyValue(new ErrorDto(INVALID_LONG_PARAMETER)))
                .onErrorResume(e ->{
                    log.error(e.getMessage(),e);
                    return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).bodyValue(new ErrorDto(SERVER_ERROR));
                });
    }

    public Mono<ServerResponse> findAll (ServerRequest serverRequest) {
        return ServerResponse.ok().
                contentType(MediaType.APPLICATION_JSON)
                .body(userUseCase.findAll(), User.class)
                .onErrorResume(e ->{
                    log.error(e.getMessage(),e);
                    return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).bodyValue(new ErrorDto(SERVER_ERROR));
                });
    }

    public Mono<ServerResponse> filterByName(ServerRequest serverRequest) {
        return Mono.justOrEmpty(serverRequest.queryParam("name").orElse("name"))
                .flatMap(name ->
                        ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(userUseCase.filterByName(name), User.class)
                )
                .onErrorResume(e -> {
                    log.error(e.getMessage(), e);
                    return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .bodyValue(new ErrorDto(SERVER_ERROR));
                });
    }


}
