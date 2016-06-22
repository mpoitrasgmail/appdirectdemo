package com.mathieupoitras.demo.account;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by Mathieu Poitras on 2016-06-19.
 */
public interface AccountRepository extends CrudRepository<Account, Long> {
    Account findByAccountUid(String accountUid);
}
