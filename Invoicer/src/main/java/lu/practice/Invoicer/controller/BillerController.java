package lu.practice.Invoicer.controller;

import lu.practice.Invoicer.model.Biller;
import lu.practice.Invoicer.repo.BillerRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import java.util.List;
@CrossOrigin
@RestController
public class BillerController {

    @Autowired
    private BillerRepo billerRepo;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

//    GET API------------------------------------------------------------------
    @GetMapping(value = "/getAll")
    public List<Biller> getAll(){
        return billerRepo.findAll();
    }


    @GetMapping(value = "getBillerById/{id}")
    public Biller getOne(@PathVariable String id){
        return billerRepo.findById(id).get();
    }


//    POST API-----------------------------------------------------------------
    @PostMapping(value = "/saveBiller")
    public Biller saveBiller(@RequestBody Biller biller){
        biller.setPassword(passwordEncoder.encode(biller.getPassword()));
        System.out.println(biller);
        return billerRepo.save(biller);
    }
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


//    PUT API-------------------------------------------------------------------
    @PutMapping(value = "/update/{id}")
    public Biller update(@PathVariable ObjectId id, @RequestBody Biller biller){
        return billerRepo.save(biller);
    }


//    DELETE API------------------------------------------------------------------
    @DeleteMapping(value = "/delete/{id}")
    public void delete(@PathVariable ObjectId id){
        Biller biller1 = billerRepo.findBy_id(id);
        billerRepo.delete(biller1);
    }



//    RANDOM--------------------------------------------------------------------
//    @GetMapping(value = "/add")
//    public int add(){
//        return 5;
//    }
//
//    @GetMapping(value = "/add/{num1}/{num2}")
//    public int addTwo(@PathVariable("num1") int a, @PathVariable("num2") int b){
//        return a+b;
//    }
//
//    @GetMapping(value = "/addThird")
//    public  int addThird(@RequestParam( value = "num1") int a, @RequestParam(value = "num2") int b){
//        return a+b;
//    }
}
