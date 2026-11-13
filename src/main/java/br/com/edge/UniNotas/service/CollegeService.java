package br.com.edge.UniNotas.service;

import br.com.edge.UniNotas.database.model.ClassEntity;
import br.com.edge.UniNotas.database.model.CollegeEntity;
import br.com.edge.UniNotas.database.repository.IClassRepository;
import br.com.edge.UniNotas.database.repository.ICollegeRepository;
import br.com.edge.UniNotas.dto.CollegeDto;
import br.com.edge.UniNotas.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CollegeService {

    private final ICollegeRepository collegeRepository;
    private final IClassRepository classRepository;

    public List<CollegeDto> findAllColleges() {
        return collegeRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    public void saveCollege(CollegeDto collegeDto) {
        CollegeEntity collegeEntity = CollegeEntity.builder()
                .name(collegeDto.getName())
                .cnpj(collegeDto.getCnpj())
                .build();

        collegeRepository.save(collegeEntity);
    }

    public List<String> listClassesByCollegeId(Integer id) throws NotFoundException {

        if (!collegeRepository.existsById(id)) {
            throw new NotFoundException("Faculdade não cadastrada no sistema");
        }


        List<String> listCourses = classRepository.findCoursesByCollegeId(id);

        if (listCourses.isEmpty()) {
            throw new NotFoundException("Nenhuma turma cadastrada nesta faculdade");
        }

        return listCourses;
    }

    public CollegeDto findCollegeById(Integer id) throws NotFoundException {
        CollegeEntity collegeEntity = collegeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Faculdade não cadastrada"));

        return toDto(collegeEntity);
    }

    public void updateCollege(Integer id, CollegeDto collegeDto) throws NotFoundException {
        CollegeEntity collegeEntity = collegeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Faculdade não cadastrada"));

        List<ClassEntity> listClass = classRepository.findAllByNamejpql(collegeEntity.getName());


        for (ClassEntity classEntity : listClass) {
            classEntity.setName(collegeDto.getName());
            classRepository.save(classEntity);
        }


        collegeEntity.setName(collegeDto.getName());
        collegeEntity.setCnpj(collegeDto.getCnpj());
        collegeRepository.save(collegeEntity);
    }

    public void deleteCollege(Integer id) throws NotFoundException {
        CollegeEntity collegeEntity = collegeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Faculdade informada não cadastrada no sistema"));

        collegeRepository.delete(collegeEntity);
    }

    public Page<CollegeDto> findAllCollegesPaged(Pageable pageable) {
        return collegeRepository.findAll(pageable)
                .map(this::toDto);
    }

    private CollegeDto toDto(CollegeEntity collegeEntity) {
        return CollegeDto.builder()
                .id(collegeEntity.getId())
                .name(collegeEntity.getName())
                .cnpj(collegeEntity.getCnpj())
                .build();
    }
}
