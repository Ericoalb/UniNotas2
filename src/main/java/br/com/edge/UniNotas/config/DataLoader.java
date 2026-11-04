package br.com.edge.UniNotas.config;

import br.com.edge.UniNotas.database.model.RolesEntity;
import br.com.edge.UniNotas.database.model.StudentEntity;
import br.com.edge.UniNotas.database.repository.IStudentyRepository;
import br.com.edge.UniNotas.database.repository.RolesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final RolesRepository rolesRepository;
    private final IStudentyRepository studentRepository;

    @Override
    public void run(String... args) throws Exception {

        RolesEntity adminRole = rolesRepository.findByName("ADMIN")
                .orElseGet(() -> rolesRepository.save(
                        RolesEntity.builder().id(1).name("ADMIN").build()
                ));

        RolesEntity alunoRole = rolesRepository.findByName("ALUNO")
                .orElseGet(() -> rolesRepository.save(
                        RolesEntity.builder().id(2).name("ALUNO").build()
                ));


        if (studentRepository.findByEmail("admin@uninotas.com") == null) {
            Set<RolesEntity> roles = new HashSet<>();
            roles.add(adminRole);

            StudentEntity admin = StudentEntity.builder()
                    .name("Administrador")
                    .email("admin@uninotas.com")
                    .matricula(1001)
                    .college("UniNotas")
                    .course("Administração")
                    .roles(roles)
                    .build();

            studentRepository.save(admin);
        }


        List<StudentEntity> alunosExistentes = studentRepository.findAll();
        for (StudentEntity aluno : alunosExistentes) {
            if (!"admin@uninotas.com".equals(aluno.getEmail()) &&
                    (aluno.getRoles() == null || aluno.getRoles().isEmpty())) {
                aluno.getRoles().add(alunoRole);
                studentRepository.save(aluno);
            }
        }
    }
}
