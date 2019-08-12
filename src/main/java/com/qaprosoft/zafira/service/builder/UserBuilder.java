package com.qaprosoft.zafira.service.builder;

import com.qaprosoft.zafira.exception.BuilderException;
import com.qaprosoft.zafira.models.dto.user.UserType;

public class UserBuilder extends BaseBuilder {

    static UserType buildUser() {
        UserType userToCreate = generateUser();
        UserType createdUser = callItem(client ->  client.createUser(userToCreate)).orElseThrow(() -> new BuilderException("User was not created"));
        createdUser.setPassword(userToCreate.getPassword());
        return createdUser;
    }

    private static UserType generateUser() {
        String username = generateRandomString();
        String email = generateRandomString() + "@qaprosoft.com";
        String firstName = generateRandomString();
        String lastName = generateRandomString();
        String password = generateRandomString();
        UserType userType =  new UserType(username, email, firstName, lastName);
        userType.setPassword(password);
        return userType;
    }

}
