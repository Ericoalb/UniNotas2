//package br.com.edge.UniNotas.service.test;
//
//import br.com.edge.UniNotas.database.model.CollegeEntity;
//import br.com.edge.UniNotas.dto.CollegeDto;
//
//import org.springframework.stereotype.Service;
//@Service
//
//public class CollegeServiceTest {
//
//    public CollegeEntity createCollege(CollegeDto collegeDto)
//    {
//        CollegeEntity collegeEntity = CollegeEntity.builder()
//                .id(0)
//                .name(collegeDto.getName())
//                .cnpj(collegeDto.getCnpj())
//                .build();
//        return collegeEntity;
//    }
//
//    public CollegeEntity updateCollege(CollegeDto collegeDto, Integer id)
//    {
//        CollegeEntity collegeEntity = CollegeEntity.builder().id(id).name(collegeDto.getName()).cnpj(collegeDto.getCnpj()).build();
//
//        return collegeEntity;
//    }
//
//
//    public void removeCollege(Integer id)
//    {
//
//    }
//
//}
