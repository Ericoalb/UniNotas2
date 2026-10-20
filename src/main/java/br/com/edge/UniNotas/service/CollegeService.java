package br.com.edge.UniNotas.service;


import br.com.edge.UniNotas.database.model.ClassEntity;
import br.com.edge.UniNotas.database.model.CollegeEntity;
import br.com.edge.UniNotas.database.repository.IClassRepository;
import br.com.edge.UniNotas.database.repository.ICollegeRepository;
import br.com.edge.UniNotas.dto.CollegeDto;
import br.com.edge.UniNotas.exception.NotFoundException;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.NoResultException;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class CollegeService
{
    private final ICollegeRepository collegeRepository;
    private final IClassRepository classRepository;

    public List<CollegeDto> findAll()
    {
        List<CollegeEntity> listCollegeEntity = collegeRepository.findAll();
        List<CollegeDto> listCollegeDto = new ArrayList<>();

        for(Integer i = 0; i < listCollegeEntity.size(); i++)
        {

            CollegeEntity collegeEntity = listCollegeEntity.get(i);
            CollegeDto collegeDto = new CollegeDto().builder()
                    .name(collegeEntity.getName())
                    .cnpj(collegeEntity.getCnpj())
                    .build();

            listCollegeDto.add(collegeDto);
        }

        return listCollegeDto;
    }


    public void save(CollegeDto collegeDto)
    {
        CollegeEntity collegeEntity  = CollegeEntity.builder()
                .name(collegeDto.getName())
                .cnpj(collegeDto.getCnpj())
                .build();

        collegeRepository.save(collegeEntity);
    }


    public ArrayList<String> listClass(Integer id) throws NotFoundException {
        Optional<CollegeEntity> collegeEntity = collegeRepository.findById(id);

        List<ClassEntity> listClass = collegeEntity.get().getClassList();

        if(listClass.isEmpty())
        {
            throw new NotFoundException("Nenhuma turma cadastrada nesta faculdade");
        }

        ArrayList<String> list = new ArrayList<>();

        for(int i = 0; i < listClass.size(); i++)
        {
            ClassEntity classEntityFor = listClass.get(i);
            String temp = classEntityFor.getCourse();
            list.add(temp);
        }

        return list;
    }

    public CollegeDto findById(Integer id) throws NotFoundException {
        CollegeEntity collegeEntity = collegeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Faculdade não cadastrada"));

        CollegeDto collegeDto = CollegeDto.builder().name(collegeEntity
                .getName())
                .cnpj(collegeEntity.getCnpj())
                .build();

        return collegeDto;
    }


    public void update(Integer id, CollegeDto collegeDto) throws NotFoundException {

        CollegeEntity collegeEntity = collegeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Faculdade não cadastrada"));


        List<ClassEntity> listClass = classRepository.findAllByNamejpql(collegeEntity.getName());

        // Atualizando o nome na tabela de Turmas
        for (int i = 0; i < listClass.size(); i++)
        {
            ClassEntity classEntity = listClass.get(i);
            classEntity.setName(collegeDto.getName());

            classRepository.save(classEntity);
        }

        // Atualizando na propria tabela da faculdade
        collegeEntity.setName(collegeDto.getName());
        collegeEntity.setCnpj(collegeDto.getCnpj());
        collegeRepository.save(collegeEntity);
    }


    public void delete(Integer id) throws NotFoundException {

        CollegeEntity collegeEntity = collegeRepository.findById(id)
                .orElseThrow(() ->new NotFoundException("FAculdade informada não cadastrada no sistema"));
        collegeRepository.deleteById(id);
    }

    public Page<CollegeDto> findAllPages(Pageable pageable)
    {
        Page<CollegeEntity> collegeEntityPage = collegeRepository.findAll(pageable);

        return collegeEntityPage.map(collegeEntity -> CollegeDto.builder()
                .name(collegeEntity.getName())
                .cnpj(collegeEntity.getCnpj())
                .build());
    }

}
