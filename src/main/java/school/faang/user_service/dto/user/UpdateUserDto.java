package school.faang.user_service.dto.user;

import jakarta.validation.constraints.NotBlank;

public record UpdateUserDto(String phone, String aboutMe, String city,
                            @NotBlank(message = "Username should be present!") String username,
                            @NotBlank(message = "Email should be present!") String email,
                            @NotBlank(message = "Country should be present!") Long countryId) {

}
