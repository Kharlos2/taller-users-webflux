package co.com.nequi.sqs.listener;

import co.com.nequi.model.user.User;
import co.com.nequi.usecase.user.UserUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.services.sqs.model.Message;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class SQSProcessor implements Function<Message, Mono<Void>> {
    private final UserUseCase userUseCase;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Mono<Void> apply(Message message) {
        return Mono.just(message.body())
                .flatMap(this::mapToUser)
                .flatMap(userUseCase::saveTraceability)
                .doOnNext(user -> System.out.println("Usuario guardado desde SQS: " + user))
                .then();
    }

    private Mono<User> mapToUser(String body) {
        try {
            User user = objectMapper.readValue(body, User.class);

            return Mono.just(User.builder()
                            .id(user.getId())
                            .idApi(user.getIdApi())
                    .firstName(user.getFirstName().toUpperCase())
                    .lastName(user.getLastName().toUpperCase())
                    .email(user.getEmail().toUpperCase())
                    .avatar(user.getAvatar().toUpperCase())
                    .build());

        } catch (Exception e) {
            return Mono.error(new RuntimeException("Error parseando mensaje a User", e));
        }
    }

}
