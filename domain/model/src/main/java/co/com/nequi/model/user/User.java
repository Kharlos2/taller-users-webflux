package co.com.nequi.model.user;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder(toBuilder = true)
@NoArgsConstructor
public class User {
    private Long id;
    private Long idApi;
    private String email;
    private String firstName;
    private String lastName;
    private String avatar;
}
