package br.com.edge.UniNotas.database.model;


import jakarta.persistence.*;
import lombok.*;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "roles")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder

public class RolesEntity implements GrantedAuthority {

    @Id
    private Integer id;
    private String name;

    @Override
    public @Nullable String getAuthority() {
        return name;
    }
}
