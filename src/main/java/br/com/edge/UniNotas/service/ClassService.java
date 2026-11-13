package br.com.edge.UniNotas.service;

import br.com.edge.UniNotas.database.model.ClassEntity;
import br.com.edge.UniNotas.database.model.CollegeEntity;
import br.com.edge.UniNotas.database.model.StudentEntity;
import br.com.edge.UniNotas.database.repository.IClassRepository;
import br.com.edge.UniNotas.database.repository.ICollegeRepository;
import br.com.edge.UniNotas.dto.ClassDto;
import br.com.edge.UniNotas.dto.StudentDto;
import br.com.edge.UniNotas.exception.BadRequestException;
import br.com.edge.UniNotas.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClassService {

    private final ICollegeRepository collegeRepository;
    private final IClassRepository classRepository;

    @Transactional
    public void saveClass(ClassDto classDto) throws BadRequestException, NotFoundException {

        CollegeEntity collegeEntity = collegeRepository.findByName(classDto.getName());
        if (collegeEntity == null) {
            throw new NotFoundException("Não é possível cadastrar uma turma em uma faculdade não registrada no sistema");
        }

        List<ClassEntity> listClass = collegeEntity.getClassList();
        if (listClass == null) listClass = new ArrayList<>();

        Optional<ClassEntity> find = listClass.stream()
                .filter(item -> classDto.getCourse().equalsIgnoreCase(item.getCourse()))
                .findFirst();

        if (find.isPresent()) {
            throw new BadRequestException("Turma já cadastrada nesta faculdade");
        }

        ClassEntity classEntity = ClassEntity.builder()
                .name(classDto.getName())
                .course(classDto.getCourse())
                .college(collegeEntity)
                .build();

        classRepository.save(classEntity);
    }

    public void deleteClass(Integer id) throws NotFoundException {
        ClassEntity classEntity = classRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Turma informada não cadastrada no sistema"));

        if (classEntity.getCollege() != null) {
            classEntity.getCollege().getClassList().remove(classEntity);
        }

        classRepository.delete(classEntity);
    }

    public List<ClassDto> findAllClasses() {
        return classRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    public void updateClass(Integer id, ClassDto classDto) throws NotFoundException {
        ClassEntity classEntity = classRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Classe não cadastrada"));


        CollegeEntity collegeEntity = collegeRepository.findByName(classDto.getName());
        if (collegeEntity == null) {
            throw new NotFoundException("Faculdade informada não está cadastrada no sistema");
        }

        classEntity.setName(classDto.getName());
        classEntity.setCourse(classDto.getCourse());
        classEntity.setCollege(collegeEntity);
        classRepository.save(classEntity);
    }

    public List<StudentDto> listStudentsByClassId(Integer id) throws NotFoundException {
        ClassEntity classEntity = classRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Turma não cadastrada"));

        List<StudentEntity> studentEntityList = classEntity.getStudentList();

        if (studentEntityList == null || studentEntityList.isEmpty()) {
            throw new NotFoundException("Nenhum estudante cadastrado na turma");
        }

        return studentEntityList.stream()
                .map(student -> StudentDto.builder()
                        .id(student.getId())
                        .name(student.getName())
                        .email(student.getEmail())
                        .matricula(student.getMatricula())
                        .college(student.getCollege())
                        .course(student.getCourse())
                        .build())
                .toList();
    }

    private ClassDto toDto(ClassEntity classEntity) {
        return ClassDto.builder()
                .id(classEntity.getId())
                .name(classEntity.getName())
                .course(classEntity.getCourse())
                .build();
    }
}
