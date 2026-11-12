package br.com.edge.UniNotas.config;

import br.com.edge.UniNotas.database.model.RolesEntity;
import br.com.edge.UniNotas.database.model.StudentEntity;
import br.com.edge.UniNotas.database.repository.IStudentyRepository;
import br.com.edge.UniNotas.database.repository.RolesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final RolesRepository rolesRepository;
    private final IStudentyRepository studentRepository;
    private final PasswordEncoder passwordEncoder;

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


        StudentEntity admin = studentRepository.findByEmail("admin@uninotas.com");
        if (admin == null) {
            Set<RolesEntity> roles = new HashSet<>();
            roles.add(adminRole);

            admin = StudentEntity.builder()
                    .name("Administrador")
                    .email("admin@uninotas.com")
                    .password(passwordEncoder.encode("admin123"))
                    .matricula(1001)
                    .college("UniNotas")
                    .course("Administração")
                    .roles(roles)
                    .build();

            studentRepository.save(admin);
        } else if (admin.getPassword() == null || admin.getPassword().isBlank()) {
            admin.setPassword(passwordEncoder.encode("admin123"));
            studentRepository.save(admin);
        }


        List<StudentEntity> alunosExistentes = studentRepository.findAll();
        for (StudentEntity aluno : alunosExistentes) {
            boolean updated = false;
            if (!"admin@uninotas.com".equals(aluno.getEmail()) &&
                    (aluno.getRoles() == null || aluno.getRoles().isEmpty())) {
                aluno.getRoles().add(alunoRole);
                updated = true;
            }
            if (aluno.getPassword() == null || aluno.getPassword().isBlank()) {
                aluno.setPassword(passwordEncoder.encode("123456"));
                updated = true;
            }
            if (updated) {
                studentRepository.save(aluno);
            }
        }
    }
}
