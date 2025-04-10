package co.com.nequi.redis.template;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRedis implements Serializable {
    private Long id;
    private Long idApi;
    private String email;
    private String firstName;
    private String lastName;
    private String avatar;
}
