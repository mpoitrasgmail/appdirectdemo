package com.mathieupoitras.demo.account;

import com.mathieupoitras.demo.account.user.User;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * Created by Mathieu Poitras on 2016-06-19.
 */
@Entity
public class Account {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    private String accountUid;
    private AccountType type;
    @OneToMany(mappedBy="account",targetEntity=User.class, fetch= FetchType.EAGER)
    private Set<User> users = new TreeSet<>();

    public Account() {
        this.accountUid = UUID.randomUUID().toString();
        this.type = AccountType.FREE;
    }

    public Account(String accountUid) {
        this.accountUid = accountUid;
    }

    public Account(AccountType accountType) {
        this.accountUid = UUID.randomUUID().toString();
        this.type = accountType;
    }

    public String getAccountUid() {
        return accountUid;
    }

    public AccountType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", accountUid='" + accountUid + '\'' +
                ", type=" + type +
                '}';
    }

    public void setAccountType(AccountType type) {
        this.type = type;
    }

    public void addUser(User userToAdd) {
        users.add(userToAdd);
    }

    public void removeUser(User userToRemove) {
        users.remove(userToRemove);
    }
}
