package co.com.nequi.r2dbc.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("public.users")
public class UserEntity {

    @Id
    private Long id;
    @Column("id_api")
    private Long idApi;
    private String email;
    @Column("first_name")
    private String firstName;
    @Column("last_name")
    private String lastName;
    private String avatar;

}
