package kg.rysbaevich.aist.model.response.customer;

public record FirebaseRefreshResponse(
            String access_token,
            String expires_in,
            String token_type,
            String refresh_token,
            String id_token,
            String user_id,
            String project_id
) {
}
