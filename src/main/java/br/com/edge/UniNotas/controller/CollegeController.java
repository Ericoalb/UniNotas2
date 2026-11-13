package br.com.edge.UniNotas.controller;

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

import java.util.List;

@Validated
@RestController
@RequestMapping("/v1/colleges")
@RequiredArgsConstructor
public class CollegeController {

    private final CollegeService collegeService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CollegeDto> findAllColleges() {
        return collegeService.findAllColleges();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CollegeDto findCollegeById(@PathVariable Integer id) throws NotFoundException {
        return collegeService.findCollegeById(id);
    }

    @GetMapping("/{id}/turmas")
    @ResponseStatus(HttpStatus.OK)
    public List<String> listClassesByCollegeId(@PathVariable Integer id) throws NotFoundException {
        return collegeService.listClassesByCollegeId(id);
    }

    @GetMapping("/page")
    @ResponseStatus(HttpStatus.OK)
    public Page<CollegeDto> findAllCollegesPaged(Pageable pageable) {
        return collegeService.findAllCollegesPaged(pageable);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void saveCollege(@Valid @RequestBody CollegeDto collegeDto) {
        collegeService.saveCollege(collegeDto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateCollege(@PathVariable Integer id, @Valid @RequestBody CollegeDto collegeDto) throws NotFoundException {
        collegeService.updateCollege(id, collegeDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCollege(@PathVariable Integer id) throws NotFoundException {
        collegeService.deleteCollege(id);
    }
}
