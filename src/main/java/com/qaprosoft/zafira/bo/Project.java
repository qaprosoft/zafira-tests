package com.qaprosoft.zafira.bo;

import groovy.util.logging.Log;
import lombok.*;

@Log
@Getter
@Setter
@NoArgsConstructor(force = true)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class Project {
    private Integer id;
    private String createdAt;
    private String name;
    private String key;
    private String logoKey;
    private String description;
    private boolean publiclyAccessible;
    private Lead lead;

    @Data
    @NoArgsConstructor
       public static class Lead {
        private Integer id;
        private String username;
        private String email;
        private String firstName;
        private String lastName;
        private String photoUrl;

    }


}
