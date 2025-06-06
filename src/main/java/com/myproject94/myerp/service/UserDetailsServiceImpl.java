package com.myproject94.myerp.service;

import com.myproject94.myerp.domain.Tecnico;
import com.myproject94.myerp.repositories.TecnicoRepository;
import com.myproject94.myerp.security.UserSS;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final TecnicoRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Tecnico> user = repository.findByEmail(email);
        if (user.isPresent()) {
            return new UserSS(user.get().getId(), user.get().getEmail(), user.get().getSenha(),user.get().getPerfis());
        }
        throw new UsernameNotFoundException(email);
    }
}
