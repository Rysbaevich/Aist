package kg.rysbaevich.aist.model.response.customer;

public record LoginResponse(
        String accessToken,
        String refreshToken,
        String userId
) {
}
