package com.bgr.genesis.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefreshTokenResponse extends BaseResponse {

    private String accessToken;
    private String refreshToken;
}
