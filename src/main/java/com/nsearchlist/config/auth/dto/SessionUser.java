package com.nsearchlist.config.auth.dto;


import com.nsearchlist.domain.user.Role;
import com.nsearchlist.domain.user.User;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {
    private Long id;
    private Role role;
    private String name;
    private String email;
    private String picture;

    public SessionUser(User user) {
        this.id = user.getId();
        this.role = user.getRole();
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }
}