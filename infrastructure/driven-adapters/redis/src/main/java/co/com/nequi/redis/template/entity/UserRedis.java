package co.com.nequi.redis.template.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
