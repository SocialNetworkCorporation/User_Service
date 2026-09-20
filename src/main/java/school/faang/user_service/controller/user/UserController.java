package school.faang.user_service.controller.user;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import school.faang.user_service.dto.user.CreateUserDto;
import school.faang.user_service.dto.user.UpdateUserDto;
import school.faang.user_service.dto.user.UserDto;
import school.faang.user_service.entity.exception.DataValidationException;
import school.faang.user_service.service.user.UserService;

import java.util.Set;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    public UserDto create(CreateUserDto userDto) {
        DtoValidator.validate(userDto);
        return userService.create(userDto);
    }

    public UserDto update(long userId, UpdateUserDto userDto) {
        DtoValidator.validate(userDto);
        return userService.update(userId, userDto);
    }

    public UserDto getByID(long userId) {
        return userService.getById(userId);
    }

    private static class DtoValidator {
        private static final Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

        public static <T> void validate(T dto){
            Set<ConstraintViolation<T>> violations = VALIDATOR.validate(dto);

            if(!violations.isEmpty()){
                String errorMessage = violations.iterator().next().getMessage();
                throw new DataValidationException(errorMessage);
            }
        }
    }
}
