package br.com.edge.UniNotas.service;

import br.com.edge.UniNotas.database.model.ClassEntity;
import br.com.edge.UniNotas.database.model.CollegeEntity;
import br.com.edge.UniNotas.database.model.StudentEntity;
import br.com.edge.UniNotas.database.repository.IClassRepository;
import br.com.edge.UniNotas.database.repository.ICollegeRepository;
import br.com.edge.UniNotas.dto.ClassDto;
import br.com.edge.UniNotas.dto.CollegeDto;
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
    public void save(ClassDto classDto) throws BadRequestException, NotFoundException {

        CollegeEntity collegeEntity = collegeRepository.findByName(classDto.getName());

        if(collegeEntity == null)
        {
            throw new NotFoundException("Não é possível cadastrar uma turma em uma faculdade não registrada no sistema");
        }

        List<ClassEntity> listClass = collegeEntity.getClassList();
        if(listClass == null) listClass = new ArrayList<>();

        Optional<ClassEntity> find = listClass.stream()
                .filter(item -> classDto.getCourse().equals(item.getCourse()))
                .findFirst();

        if(find.isPresent())
        {
            throw new BadRequestException("Turma já cadastrada nesta faculdade");
        }

        ClassEntity classEntity = ClassEntity.builder()
                .name(classDto.getName())
                .course(classDto.getCourse())
                .college(collegeEntity).build();

        classRepository.save(classEntity);

    }

    public void delete(Integer id) throws NotFoundException {

       ClassEntity classEntity = classRepository.findById(id)
                .orElseThrow(() ->new NotFoundException("Turma informada não cadastrada no sistema"));


        if (classEntity.getCollege() != null) {
            classEntity.getCollege().getClassList().remove(classEntity);
        }

       classRepository.delete(classEntity);
    }

    public List<ClassDto> findAll()
    {
        List<ClassEntity> listClass = classRepository.findAll();
        List<ClassDto> listClassDto = new ArrayList<>();

        for(Integer i = 0; i < listClass.size(); i++)
        {
            ClassEntity classEntity = listClass.get(i);
            ClassDto classDto = ClassDto.builder()
                    .name(classEntity.getName())
                    .course(classEntity.getCourse())
                    .build();

            listClassDto.add(classDto);
        }

        return listClassDto;
    }


    public void update(Integer id, ClassDto classDto) throws NotFoundException {

        ClassEntity classEntity = classRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Classe não cadastrada"));


        CollegeEntity collegeEntity = collegeRepository.findByName(classDto.getName());

        classEntity.setName(classDto.getName());
        classEntity.setCourse(classDto.getCourse());
        classEntity.setCollege(collegeEntity);
        classRepository.save(classEntity);

    }


    public List<StudentDto> listStudent(Integer id) throws NotFoundException {
        ClassEntity classEntity = classRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Turma não cadastrada"));

        List<StudentEntity> studentEntityList = classEntity.getStudentList();

        if(studentEntityList.isEmpty())
        {
            throw new NotFoundException("Nenhum estudante cadastrado na turma");
        }

        List<StudentDto> studentDtoList = new ArrayList<>();

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

            studentDtoList.add(studentDto);
        }

        return studentDtoList;
    }
}
