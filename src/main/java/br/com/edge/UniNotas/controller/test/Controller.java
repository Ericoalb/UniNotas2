//package br.com.edge.UniNotas.controller.test;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/v1/controller")
//public class Controller {
//
//    /*
//    @GetMapping(value = "/{id}")
//    @ResponseStatus(HttpStatus.OK)
//    public String getHello(@PathVariable("id") String numb)
//    {
//        return "Hello World!" + numb;
//    }
//     */
//
//
//    //Parâmetros dos métodos
//    @GetMapping(value = "/{id}")
//    @ResponseStatus(HttpStatus.OK)
//    public String getHello(@PathVariable("id") String numb)
//    {
//        return "Hello World! " + numb;
//    }
//
//
//    @GetMapping()
//    @ResponseStatus(HttpStatus.OK)
//    public String getHello2(@RequestParam(value = "name", required = false) String name2)
//    {
//        return "Hello World " + name2 + "Não Obrigatório!";
//    }
//
//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    public String setHello(@RequestBody String saldacao)
//    {
//        return "Saldação Atualizada para " + saldacao;
//    }
//
//}
