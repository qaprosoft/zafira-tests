package com.qaprosoft.zafira.bo;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Group {

    private Integer id;
    private String name;
    private Boolean isDefault = false;
    private Boolean invitable = true;
}


