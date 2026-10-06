package school.faang.user_service.dto.user;

import jakarta.validation.constraints.NotBlank;

public record SearchUserDto(@NotBlank(message = "Info should be present!") String aboutMe,
                            @NotBlank(message = "City should be present!") String city,
                            @NotBlank(message = "Experience should be present!") Integer experience) {
}