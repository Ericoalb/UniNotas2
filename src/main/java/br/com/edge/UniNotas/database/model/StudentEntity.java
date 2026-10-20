package br.com.edge.UniNotas.database.model;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "student")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder

public class StudentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false,unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private Integer matricula;

    @Column(nullable = false)
    private String college;

    @Column(nullable = false)
    private String course;

    @ManyToOne
    @JoinColumn(name = "class")
    private ClassEntity clas;



}
