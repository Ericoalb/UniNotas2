package br.com.edge.UniNotas.database.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "class")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ClassEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String course;

    @ManyToOne
    @JoinColumn(name = "faculdade")
    private CollegeEntity college;

    @OneToMany(mappedBy = "clas", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @ToString.Exclude
    @Builder.Default
    private List<StudentEntity> studentList = new ArrayList<>();




}
