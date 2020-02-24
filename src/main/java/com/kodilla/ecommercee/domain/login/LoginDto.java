package com.kodilla.ecommercee.domain.login;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginDto {

    private Boolean loggedIn;
    private Long sessionId;

}
