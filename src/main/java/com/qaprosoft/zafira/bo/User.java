package com.qaprosoft.zafira.bo;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(force = true)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class User {

    int id;

    String username;

    String email;

    String firstName;

    String lastName;

    String source;

}

