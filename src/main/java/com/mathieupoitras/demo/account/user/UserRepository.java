package com.mathieupoitras.demo.account.user;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by Mathieu Poitras on 2016-06-21.
 */
public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
    User findByUserUid(String userUid);
}
