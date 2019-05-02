package com.qaprosoft.zafira.service.builder;

import com.qaprosoft.zafira.exception.BuilderException;
import com.qaprosoft.zafira.models.dto.user.UserType;

public class UserBuilder extends BaseBuilder {

    static UserType buildUser() {
        return buildItem(client -> client.createUser(generateUser())).orElseThrow(() -> new BuilderException("User was not created"));
    }

    private static UserType generateUser() {
        String username = generateRandomString();
        String email = generateRandomString() + "@qaprosoft.com";
        String firstName = generateRandomString();
        String lastName = generateRandomString();
        return new UserType(username, email, firstName, lastName);
    }

}
