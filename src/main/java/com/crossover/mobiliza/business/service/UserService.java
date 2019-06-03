package com.crossover.mobiliza.business.service;

import com.crossover.mobiliza.business.entity.User;
import com.crossover.mobiliza.data.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Log4j2
@Service
@Transactional
public class UserService extends EntityServiceBase<User, Long, UserRepository> {

    public User findByGoogleId(String googleId) {
        return repository.findByGoogleId(googleId).orElse(null);
    }

}
