package com.qaprosoft.zafira.bo;


import lombok.*;

import java.util.List;

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

    List groups;

    List permissions;

    String registrationDateTime;
}

