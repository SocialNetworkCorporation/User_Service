package school.faang.user_service.dto.user;

import jakarta.validation.constraints.NotBlank;

public record CreateUserDto(@NotBlank(message = "Username should be present!") String username,
                            @NotBlank(message = "Email should be present!") String email,
                            @NotBlank(message = "Password should be present!") String password,
                            @NotBlank(message = "Country should be present!") Long countryId) {
}
