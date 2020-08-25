package lu.practice.Invoicer.controller;

import lu.practice.Invoicer.Services.BillerDetailsService;
import lu.practice.Invoicer.model.Biller.Biller;
import lu.practice.Invoicer.repo.BillerRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import lu.practice.Invoicer.configuration.JwtTokenUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.http.ResponseEntity;


import lu.practice.Invoicer.model.Biller.BillerJwtLoginRequest;
import lu.practice.Invoicer.model.Biller.BillerJwtLoginResponse;


import java.util.List;
@CrossOrigin
@RestController
public class BillerController {

    @Autowired
    private BillerRepo billerRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private BillerDetailsService billerDetailsService;

//    GET API------------------------------------------------------------------
    @GetMapping(value = "/api/getAll")
    public List<Biller> getAll(){
        return billerRepo.findAll();
    }


    @GetMapping(value = "/api/getBillerById/{id}")
    public Biller getOne(@PathVariable String id){
        return billerRepo.findById(id).get();
    }

    @PostMapping(value = "/api/userAuth")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody BillerJwtLoginRequest authenticationRequest) throws Exception {
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        final UserDetails userDetails = billerDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails.getUsername());
        return ResponseEntity.ok(new BillerJwtLoginResponse(token));
    }
    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }


//    public Boolean checkUser(@RequestBody Biller biller){
//        Biller billerInfo = billerRepo.findByEmail(biller.getEmail());
//        if(passwordEncoder.matches(biller.getPassword(),billerInfo.getPassword())){
//            return true;
//        }
//        else{
//            return false;
//        }
//    }


//    POST API-----------------------------------------------------------------
    @PostMapping(value = "/api/registerBiller")
    public Biller saveBiller(@RequestBody Biller biller){
        biller.setPassword(passwordEncoder.encode(biller.getPassword()));
        System.out.println(biller);
        return billerRepo.save(biller);
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
