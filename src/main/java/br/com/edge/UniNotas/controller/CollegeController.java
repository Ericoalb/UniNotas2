package br.com.edge.UniNotas.controller;


import br.com.edge.UniNotas.database.model.ClassEntity;
import br.com.edge.UniNotas.dto.ClassDto;
import br.com.edge.UniNotas.dto.CollegeDto;
import br.com.edge.UniNotas.exception.NotFoundException;
import br.com.edge.UniNotas.service.CollegeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Validated
@RestController
@RequestMapping("/v1/college")
@RequiredArgsConstructor
public class CollegeController {

    private final CollegeService collegeService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CollegeDto> findAll()
    {
        return collegeService.findAll();
    }

    @GetMapping("/turmas/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ArrayList<String> listClass(@PathVariable Integer id) throws NotFoundException {
        return collegeService.listClass(id);
    }

    @GetMapping("/findbyid/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CollegeDto findByID(@PathVariable Integer id) throws NotFoundException {
        return collegeService.findById(id);
    }

    @GetMapping("/page")
    @ResponseStatus(HttpStatus.OK)
    public Page<CollegeDto> findAll(Pageable pageable) {
        return collegeService.findAllPages(pageable);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@Valid @RequestBody CollegeDto collegeDto)
    {
        collegeService.save(collegeDto);
    }

    @PostMapping("/update/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public void update(@PathVariable Integer id, @Valid@RequestBody CollegeDto collegeDto) throws NotFoundException {
        collegeService.update(id, collegeDto);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) throws NotFoundException {
        collegeService.delete(id);
    }


}
