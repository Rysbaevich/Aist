package kg.rysbaevich.aist.model.request.customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FirebaseRefreshRequest {
    private String grantType;
    private String refreshToken;
}
