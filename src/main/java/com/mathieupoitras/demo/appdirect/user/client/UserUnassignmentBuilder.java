package com.mathieupoitras.demo.appdirect.user.client;


import com.mathieupoitras.demo.appdirect.model.account.Account;
import com.mathieupoitras.demo.appdirect.user.User;

/**
 * Created by Mathieu Poitras on 2016-06-20.
 */
public class UserUnassignmentBuilder {
    private boolean needsAccountObject = false;
    private String accountAccountIdentifier;

    private boolean needsUserObject = false;
    private String userUuid;
    private String userEmail;

    public static UserUnassignmentBuilder init() {
        return new UserUnassignmentBuilder();
    }

    public UserUnassignmentBuilder withAccountIdentifier(String accountIdentifier) {
        needsAccountObject = true;
        this.accountAccountIdentifier = accountIdentifier;
        return this;
    }

    public UserUnassignmentBuilder withUserUuid(String userUuid) {
        needsUserObject = true;
        this.userUuid = userUuid;
        return this;
    }

    public UserUnassignmentBuilder withUserEmail(String userEmail) {
        needsUserObject = true;
        this.userEmail = userEmail;
        return this;
    }

    public UserUnassignment build() {
        UserUnassignment userUnassignment = new UserUnassignment();
        if(needsAccountObject){
            Account account = new Account();
            account.setAccountIdentifier(accountAccountIdentifier);
            userUnassignment.setAccount(account);
        }
        if(needsUserObject){
            User user = new User();
            user.setEmail(userEmail);
            user.setUuid(userUuid);
            userUnassignment.setUser(user);
        }
        return userUnassignment;
    }
}
