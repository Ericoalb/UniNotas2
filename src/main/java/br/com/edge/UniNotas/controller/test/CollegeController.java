//package br.com.edge.UniNotas.controller.test;
//
//
//import br.com.edge.UniNotas.database.model.CollegeEntity;
//import br.com.edge.UniNotas.dto.CollegeDto;
//import br.com.edge.UniNotas.service.test.CollegeServiceTest;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/v1/mappingcollege")
//public class CollegeController {
//
//    private final CollegeServiceTest collegeService;
//
//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    public CollegeEntity addCollege(@RequestBody CollegeDto collegeDto)
//    {
//        return collegeService.createCollege(collegeDto);
//    }
//
//
//    @PutMapping(value = "/{id}")
//    @ResponseStatus(HttpStatus.CREATED)
//    public CollegeEntity updateCollege(@PathVariable("id") Integer id, @RequestBody CollegeDto collegeDto)
//    {
//        return collegeService.updateCollege(collegeDto, id);
//    }
//
//    @DeleteMapping(value = "/{id}")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void removeCollege(@PathVariable("id") Integer id)
//    {
//        collegeService.removeCollege(id);
//    }
//
//}
