package com.devsuperior.movieflix.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.movieflix.entities.User;
import com.devsuperior.movieflix.repositories.UserRepository;
import com.devsuperior.movieflix.services.exceptions.ForbiddenException;
import com.devsuperior.movieflix.services.exceptions.UnauthorizedException;

@Service
public class AuthService {

	@Autowired
	private UserRepository userRepository;
	
	@Transactional(readOnly = true)
	 public User authenticated(){
        //Usuário logado no SpringSecurity
        try {
            String userName = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userRepository.findByEmail(userName);
            return user;
        }catch (Exception e){
            throw new UnauthorizedException("Invalid user"); //Somente por segurança
        }
    }
	
	 public void validateSelfOrAdmin(Long userId) {
	        User user = authenticated();
	        if(!user.hasHole("ROLE_MEMBER")) {
	            throw new ForbiddenException("Acesso Negado");
	        }
	    }
}
