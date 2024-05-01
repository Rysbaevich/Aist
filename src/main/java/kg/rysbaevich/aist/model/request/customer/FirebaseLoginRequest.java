package kg.rysbaevich.aist.model.request.customer;

public record FirebaseLoginRequest(
        String email,
        String password,
        boolean returnSecureToken
) {
}
