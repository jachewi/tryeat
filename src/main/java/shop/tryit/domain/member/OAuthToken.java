package shop.tryit.domain.member;

import lombok.Data;

@Data
public class OAuthToken {
    private String access_token;
    private String token_type;
    private String refresh_token;
    private int expires_in;
    private String scope;
    private int refresh_token_expires_in;


//    access_token: "kIh3g8nUgUPfNPwhdXSZQsW_HfEUIcpwvNOyXL3zCisM1AAAAYG0yC36",
//    token_type: "bearer",
//    refresh_token: "9ZFfJ_V-8t72Z0Ni3n20E_2jogbl1VEkiuft85ykCisM1AAAAYG0yC35",
//    expires_in: 21599,
//    scope: "account_email profile_nickname",
//    refresh_token_expires_in: 5183999
}
