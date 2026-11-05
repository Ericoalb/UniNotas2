package br.com.edge.UniNotas.service;

import br.com.edge.UniNotas.config.TokenProvider;
import br.com.edge.UniNotas.database.model.ClassEntity;
import br.com.edge.UniNotas.database.model.CollegeEntity;
import br.com.edge.UniNotas.database.model.StudentEntity;
import br.com.edge.UniNotas.database.repository.IClassRepository;
import br.com.edge.UniNotas.database.repository.ICollegeRepository;
import br.com.edge.UniNotas.database.repository.IStudentyRepository;
import br.com.edge.UniNotas.dto.LoginDto;
import br.com.edge.UniNotas.dto.StudentDto;
import br.com.edge.UniNotas.dto.TokenDto;
import br.com.edge.UniNotas.exception.BadRequestException;
import br.com.edge.UniNotas.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final IStudentyRepository studentyRepository;
    private final IClassRepository classRepository;
    private final ICollegeRepository collegeRepository;
    private final TokenProvider tokenProvider;

    public TokenDto login(LoginDto loginDto) throws NotFoundException {
        StudentEntity student = studentyRepository.findByEmail(loginDto.getEmail());

        if (student == null) {
            throw new NotFoundException("Usuário não encontrado com o e-mail informado");
        }

        String token = tokenProvider.gerarToken(student.getEmail());
        return new TokenDto(token);
    }

    public void saveStudent(StudentDto studentDto) throws NotFoundException, BadRequestException {
        StudentEntity studentEntity = studentyRepository.findByEmail(studentDto.getEmail());

        if (studentEntity != null) {
            throw new BadRequestException("Aluno já cadastrado no sistema");
        }

        // Verifica se a faculdade existe
        CollegeEntity collegeEntity = collegeRepository.findByName(studentDto.getCollege());
        if (collegeEntity == null) {
            throw new NotFoundException("Faculdade informada não está cadastrada no sistema");
        }

        // Verifica se a turma/curso existe nessa faculdade
        ClassEntity classEntity = classRepository.findByNameAndCourse(studentDto.getCollege(), studentDto.getCourse());
        if (classEntity == null) {
            throw new NotFoundException("Turma/Curso não cadastrado para esta faculdade");
        }

        studentEntity = StudentEntity.builder()
                .name(studentDto.getName())
                .email(studentDto.getEmail())
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
