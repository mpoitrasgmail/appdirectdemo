package com.mathieupoitras.demo.account;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by Mathieu Poitras on 2016-06-20.
 */
public class AccountTest {

    @Test
    public void whenCreatingAnAccountWithoutSpecifyingDetailsThenItHasAGeneratedUUIDAndItIsFree(){
        Account newAccount = new Account();


        assertThat(newAccount.getAccountUid(), is(not(nullValue())));
        assertThat(newAccount.getType(), is(AccountType.FREE));
    }

    @Test
    public void whenCreatingAnAccountSpecifyingOnlyAccountTypeThenItHasAGeneratedUUIDAndItIsSpecifiedAccountType(){
        AccountType accountTypeSpecified = AccountType.FREE;


        Account newAccount = new Account(accountTypeSpecified);


        assertThat(newAccount.getAccountUid(), is(not(nullValue())));
        assertThat(newAccount.getType(), is(accountTypeSpecified));
    }

}