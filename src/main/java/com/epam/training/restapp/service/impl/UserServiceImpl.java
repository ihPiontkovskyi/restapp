package com.epam.training.restapp.service.impl;

import com.epam.training.restapp.dto.UserInfo;
import com.epam.training.restapp.entity.Role;
import com.epam.training.restapp.entity.User;
import com.epam.training.restapp.exception.ElementNotFoundException;
import com.epam.training.restapp.repository.UserRepository;
import com.epam.training.restapp.service.UserService;
import com.epam.training.restapp.service.mapper.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserServiceImpl implements UserService, UserDetailsService {
    private final PasswordEncoder encoder;
    private final UserRepository repository;
    private final UserMapper mapper;

    @Override
    public Page<UserInfo> getAll(Pageable pageable) {
        return repository.findAll(pageable)
                .map(mapper::mapToDto);
    }

    @Override
    public UserInfo create(UserInfo dto) {
        dto.setPassword(encoder.encode(dto.getPassword()));
        return mapper.mapToDto(repository.save(mapper.mapToEntity(dto)));
    }

    @Override
    public UserInfo update(Integer id, UserInfo dto) {
        return repository.findById(id)
                .map(mapper::mapToDto)
                .map(e -> {
                    e.setEmail(dto.getEmail());
                    e.setFullName(dto.getFullName());
                    if (encoder.matches(e.getPassword(), dto.getPassword())) {
                        e.setPassword(encoder.encode(dto.getPassword()));
                    }
                    e.setPostInfos(dto.getPostInfos());
                    return mapper.mapToDto(repository.save(mapper.mapToEntity(e)));
                }).orElseThrow(() -> new ElementNotFoundException("User id : " + id + " not found!"));
    }

    @Override
    public void delete(Integer id) {
        final Optional<User> post = repository.findById(id);
        if (!post.isPresent()) {
            throw new ElementNotFoundException("User id : " + id + " not found!");
        }
        post.ifPresent(repository::delete);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return repository.findByEmail(s)
                .orElseThrow(() -> new UsernameNotFoundException("User with " + s + " username doesn't exist"));
    }
}
