package kg.rysbaevich.aist.model.response.billing;

public record YooKassaErrorResponse(
        String type,
        String id,
        String code,
        String description
) {
}
