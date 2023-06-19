package dev.onurcakir.ecommerce.payload.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginPayload {
    @NonNull
    private String email;
    @NonNull
    private String password;

}
