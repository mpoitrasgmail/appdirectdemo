package com.mathieupoitras.demo.appdirect.user.client;


import com.mathieupoitras.demo.appdirect.model.account.Account;
import com.mathieupoitras.demo.appdirect.user.User;

/**
 * Created by Mathieu Poitras on 2016-06-20.
 */
public class UserAssignmentBuilder {
    private boolean needsAccountObject = false;
    private String accountAccountIdentifier;

    private boolean needsUserObject = false;
    private String userUuid;
    private String userEmail;

    public static UserAssignmentBuilder init() {
        return new UserAssignmentBuilder();
    }

    public UserAssignmentBuilder withAccountIdentifier(String accountIdentifier) {
        needsAccountObject = true;
        this.accountAccountIdentifier = accountIdentifier;
        return this;
    }

    public UserAssignmentBuilder withUserUuid(String userUuid) {
        needsUserObject = true;
        this.userUuid = userUuid;
        return this;
    }

    public UserAssignmentBuilder withUserEmail(String userEmail) {
        needsUserObject = true;
        this.userEmail = userEmail;
        return this;
    }

    public UserAssignment build() {
        UserAssignment userAssignment = new UserAssignment();
        if(needsAccountObject){
            Account account = new Account();
            account.setAccountIdentifier(accountAccountIdentifier);
            userAssignment.setAccount(account);
        }
        if(needsUserObject){
            User user = new User();
            user.setEmail(userEmail);
            user.setUuid(userUuid);
            userAssignment.setUser(user);
        }
        return userAssignment;
    }
}
