package br.com.edge.UniNotas.service;


import br.com.edge.UniNotas.database.model.ClassEntity;
import br.com.edge.UniNotas.database.model.CollegeEntity;
import br.com.edge.UniNotas.database.model.StudentEntity;
import br.com.edge.UniNotas.database.repository.IClassRepository;
import br.com.edge.UniNotas.database.repository.ICollegeRepository;
import br.com.edge.UniNotas.database.repository.IStudentyRepository;
import br.com.edge.UniNotas.dto.StudentDto;
import br.com.edge.UniNotas.exception.BadRequestException;
import br.com.edge.UniNotas.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final IStudentyRepository studentyRepository;
    private final IClassRepository classRepository;
    private final ICollegeRepository collegeRepository;


    public void save(StudentDto studentDto) throws NotFoundException, BadRequestException
    {
        StudentEntity studentEntity = studentyRepository.findByEmail(studentDto.getEmail());

        if(studentEntity != null)
        {
            throw new BadRequestException("Aluno já cadastrado no sistema");
        }

        ClassEntity classEntity = classRepository.findByNameAndCourse(studentDto.getCollege(), studentDto.getCourse());

        if(classEntity == null)
        {
            throw new BadRequestException("Faculdade ou Curso não está cadastrado no sistema");
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

    public List<StudentDto> findAll()
    {
        List<StudentEntity> studentEntityList = studentyRepository.findAll();
        List <StudentDto> studentDtosList = new ArrayList<>();

        for(int i = 0; i < studentEntityList.size(); i++)
        {
            StudentEntity studentEntity = studentEntityList.get(i);
            StudentDto studentDto = StudentDto.builder()
                    .name(studentEntity.getName())
                    .email(studentEntity.getEmail())
                    .matricula(studentEntity.getMatricula())
                    .college(studentEntity.getCollege())
                    .course(studentEntity.getCourse())
                    .build();

            studentDtosList.add(studentDto);
        }

        return studentDtosList;

    }

    public void update(StudentDto studentDto, Integer id) throws NotFoundException, BadRequestException {

        StudentEntity studentEntity = studentyRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Estudante não cadastrada"));

        CollegeEntity collegeEntity = collegeRepository.findByName(studentDto.getCollege());

        if(collegeEntity == null)
        {
            throw new BadRequestException("Faculdade não cadastrada");
        }

        ClassEntity classEntity = classRepository.findByNameAndCourse(studentDto.getCollege(), studentDto.getCourse());

        if(classEntity == null)
        {
            throw new BadRequestException("Curso não cadastrado na faculdade");
        }

        studentEntity.setName(studentDto.getName());
        studentEntity.setEmail(studentDto.getEmail());
        studentEntity.setMatricula(studentDto.getMatricula());
        studentEntity.setCollege(studentDto.getCollege());
        studentEntity.setCourse(studentDto.getCourse());
        studentEntity.setClas(classEntity);

        studentyRepository.save(studentEntity);
    }


    public void delete(Integer id) throws NotFoundException {
        StudentEntity studentEntity = studentyRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Aluno não cadastrado"));

        if(studentEntity.getCollege() != null) {
            studentEntity.getClas().getStudentList().remove(studentEntity);
        }
        studentyRepository.delete(studentEntity);
    }
}
