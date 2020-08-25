package lu.practice.Invoicer.Services;

import lu.practice.Invoicer.model.Biller.Biller;
import lu.practice.Invoicer.repo.BillerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class BillerDetailsService implements UserDetailsService {
    @Autowired
    private BillerRepo billerRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Biller billerInfo = billerRepo.findByEmail(email);
        if(billerInfo != null){
            return new User(billerInfo.getEmail(),billerInfo.getPassword(), new ArrayList<>());
        }
        else{
            throw new UsernameNotFoundException("User not found!");
        }
    }
}
