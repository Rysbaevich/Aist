package kg.rysbaevich.aist.model.response.customer;

public record FirebaseLoginResponse(
        String kind,
        String localId,
        String email,
        String displayName,
        String idToken,
        boolean registered,
        String refreshToken,
        String expiresIn
) {
}
