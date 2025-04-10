package co.com.nequi.webclient.dto;

public record UserApiResponse(
        Long id,
        String email,
        String first_name,
        String last_name,
        String avatar
) {
}
