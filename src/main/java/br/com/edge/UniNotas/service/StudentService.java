package br.com.edge.UniNotas.service;

import br.com.edge.UniNotas.config.TokenProvider;
import br.com.edge.UniNotas.database.model.ClassEntity;
import br.com.edge.UniNotas.database.model.CollegeEntity;
import br.com.edge.UniNotas.database.model.RefreshTokenEntity;
import br.com.edge.UniNotas.database.model.StudentEntity;
import br.com.edge.UniNotas.database.repository.IClassRepository;
import br.com.edge.UniNotas.database.repository.ICollegeRepository;
import br.com.edge.UniNotas.database.repository.IStudentyRepository;
import br.com.edge.UniNotas.dto.LoginDto;
import br.com.edge.UniNotas.dto.RefreshTokenRequestDto;
import br.com.edge.UniNotas.dto.StudentDto;
import br.com.edge.UniNotas.dto.TokenDto;
import br.com.edge.UniNotas.exception.BadRequestException;
import br.com.edge.UniNotas.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final IStudentyRepository studentyRepository;
    private final IClassRepository classRepository;
    private final ICollegeRepository collegeRepository;
    private final TokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final PasswordEncoder passwordEncoder;

    public TokenDto login(LoginDto loginDto) throws NotFoundException, BadRequestException {
        StudentEntity student = studentyRepository.findByEmail(loginDto.getEmail());

        if (student == null) {
            throw new NotFoundException("Usuário não encontrado com o e-mail informado");
        }

        if (!passwordEncoder.matches(loginDto.getPassword(), student.getPassword())) {
            throw new BadRequestException("Senha incorreta");
        }

        String accessToken = tokenProvider.gerarToken(student.getEmail());
        RefreshTokenEntity refreshToken = refreshTokenService.createRefreshToken(student);

        return TokenDto.builder()
                .token(accessToken)
                .refreshToken(refreshToken.getToken())
                .build();
    }

    public TokenDto refreshToken(RefreshTokenRequestDto requestDto) throws NotFoundException, BadRequestException {
        RefreshTokenEntity refreshToken = refreshTokenService.findByToken(requestDto.getRefreshToken())
                .orElseThrow(() -> new NotFoundException("Refresh token não encontrado no sistema"));

        refreshToken = refreshTokenService.verifyExpiration(refreshToken);

        StudentEntity student = refreshToken.getStudent();
        String newAccessToken = tokenProvider.gerarToken(student.getEmail());

        return TokenDto.builder()
                .token(newAccessToken)
                .refreshToken(refreshToken.getToken())
                .build();
    }

    public void saveStudent(StudentDto studentDto) throws NotFoundException, BadRequestException {
        StudentEntity studentEntity = studentyRepository.findByEmail(studentDto.getEmail());

        if (studentEntity != null) {
            throw new BadRequestException("Aluno já cadastrado no sistema");
        }


        CollegeEntity collegeEntity = collegeRepository.findByName(studentDto.getCollege());
        if (collegeEntity == null) {
            throw new NotFoundException("Faculdade informada não está cadastrada no sistema");
        }


        ClassEntity classEntity = classRepository.findByNameAndCourse(studentDto.getCollege(), studentDto.getCourse());
        if (classEntity == null) {
            throw new NotFoundException("Turma/Curso não cadastrado para esta faculdade");
        }

        studentEntity = StudentEntity.builder()
                .name(studentDto.getName())
                .email(studentDto.getEmail())
                .password(passwordEncoder.encode(studentDto.getPassword()))
                .matricula(studentDto.getMatricula())
                .college(studentDto.getCollege())
                .course(studentDto.getCourse())
                .clas(classEntity)
                .build();

        studentyRepository.save(studentEntity);
    }

    public List<StudentDto> findAllStudents() {
        return studentyRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    public void updateStudent(StudentDto studentDto, Integer id) throws NotFoundException, BadRequestException {
        StudentEntity studentEntity = studentyRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Estudante não cadastrado"));

        CollegeEntity collegeEntity = collegeRepository.findByName(studentDto.getCollege());
        if (collegeEntity == null) {
            throw new NotFoundException("Faculdade não cadastrada");
        }

        ClassEntity classEntity = classRepository.findByNameAndCourse(studentDto.getCollege(), studentDto.getCourse());
        if (classEntity == null) {
            throw new NotFoundException("Curso não cadastrado na faculdade");
        }

        studentEntity.setName(studentDto.getName());
        studentEntity.setEmail(studentDto.getEmail());
        if (studentDto.getPassword() != null && !studentDto.getPassword().isBlank()) {
            studentEntity.setPassword(passwordEncoder.encode(studentDto.getPassword()));
        }
        studentEntity.setMatricula(studentDto.getMatricula());
        studentEntity.setCollege(studentDto.getCollege());
        studentEntity.setCourse(studentDto.getCourse());
        studentEntity.setClas(classEntity);

        studentyRepository.save(studentEntity);
    }

    public void deleteStudent(Integer id) throws NotFoundException {
        StudentEntity studentEntity = studentyRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Aluno não cadastrado"));

        if (studentEntity.getClas() != null && studentEntity.getClas().getStudentList() != null) {
            studentEntity.getClas().getStudentList().remove(studentEntity);
        }
        studentyRepository.delete(studentEntity);
    }

    private StudentDto toDto(StudentEntity studentEntity) {
        return StudentDto.builder()
                .id(studentEntity.getId())
                .name(studentEntity.getName())
                .email(studentEntity.getEmail())
                .matricula(studentEntity.getMatricula())
                .college(studentEntity.getCollege())
                .course(studentEntity.getCourse())
                .build();
    }
}
