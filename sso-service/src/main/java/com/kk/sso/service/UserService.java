package com.kk.sso.service;

import java.util.Objects;
import java.util.UUID;

import com.kk.sso.service.JwtService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kk.sso.common.ConstanstCommon;
import com.kk.sso.entity.UserEntity;
import com.kk.sso.exception.NotFoundException;
import com.kk.sso.model.RecordToken;
import com.kk.sso.repository.UserRepository;
import com.kk.sso.request.LoginReq;
import com.kk.sso.request.RegisterReq;
import com.kk.sso.service.IService.AuthServiceImpl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements AuthServiceImpl {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final EntityManager entityManager;

    public UserEntity loadByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }

    @Override
    public RecordToken login(LoginReq req) {
//        EntityTransaction transaction = entityManager.getTransaction();
//         transaction.begin();
//         entityManager.persist(transaction);
//         transaction.rollback();
//         transaction.commit();
        UserEntity user = userRepository.findByUsername(req.username()).orElseThrow();
        if(!passwordEncoder.matches(req.password(), user.getPassword())) {
            throw new NotFoundException();
        }
        return jwtService.generatingToken(user);
    }

    @Override
    public RecordToken register(RegisterReq req) {
        if (!Objects.equals(req.comfirm_password(), req.password())) {
            throw new IllegalArgumentException("Password and Confirm Password are not equal");
        }
        log.info("handle register");
        UserEntity save = UserEntity.builder()
                .id(UUID.randomUUID())
                .username(req.username())
                .password(passwordEncoder.encode(req.password()))
                .variable(ConstanstCommon.ACTIVE)
                .build();
        userRepository.save(save);
        return jwtService.generatingToken(save);
    }
}
