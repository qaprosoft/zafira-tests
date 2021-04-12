package com.qaprosoft.zafira.bo;

import groovy.util.logging.Log;
import lombok.*;

import java.util.List;
@Log
@Getter
@Setter
@NoArgsConstructor(force = true)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class User {

    Integer id;
    String username;
    String email;
    String firstName;
    String lastName;
    String source;
    String status;
    List<Group> groups;
    Boolean onboardingCompleted;
    List<Permission> permissions;
    String registrationDateTime;
}

