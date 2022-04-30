package com.bgr.genesis.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest extends BaseRequest{

    private String email;
    private String password;
    private String name;
    private String lastname;
}
