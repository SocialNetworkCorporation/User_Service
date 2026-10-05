package school.faang.user_service.service.user;

import school.faang.user_service.dto.user.CreateUserDto;
import school.faang.user_service.dto.user.SearchUserDto;
import school.faang.user_service.dto.user.UpdateUserDto;
import school.faang.user_service.dto.user.UserDto;

import java.util.List;

public interface UserService {

    UserDto create(CreateUserDto userDto);

    UserDto update(long userId, UpdateUserDto userDto);

    UserDto getById(long userId);

    List<UserDto> getUsers(SearchUserDto searchUserDto);
}
