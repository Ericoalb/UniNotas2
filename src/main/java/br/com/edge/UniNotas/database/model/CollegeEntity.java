package br.com.edge.UniNotas.database.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "college")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder

public class CollegeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false, unique = true)
    private String cnpj;

    @OneToMany(mappedBy = "college", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @ToString.Exclude
    @Builder.Default
    private List<ClassEntity> classList = new ArrayList<>();


}
