package school.faang.user_service.service.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import school.faang.user_service.config.context.UserContext;
import school.faang.user_service.dto.user.CreateUserDto;
import school.faang.user_service.dto.user.SearchUserDto;
import school.faang.user_service.dto.user.UpdateUserDto;
import school.faang.user_service.dto.user.UserDto;
import school.faang.user_service.entity.Country;
import school.faang.user_service.entity.User;
import school.faang.user_service.entity.exception.DataValidationException;
import school.faang.user_service.entity.exception.ForbiddenException;
import school.faang.user_service.filter.UserFilter;
import school.faang.user_service.mapper.UserMapper;
import school.faang.user_service.repository.CountryRepository;
import school.faang.user_service.repository.UserRepository;

import java.util.List;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Value("${user.password.min.length}")
    private int minPasswordLength;
    private final UserRepository userRepository;
    private final CountryRepository countryRepository;
    private final UserMapper userMapper;
    private final UserContext userContext;
    private final List<UserFilter> userFilters;

    @Override
    public List<UserDto> getUsers(SearchUserDto searchUserDto) {
        Stream<User> filteredUsers = userRepository.findAll().stream();

        for (UserFilter userFilter : userFilters) {
            if (userFilter.isApplicable(searchUserDto)){
                filteredUsers = userFilter.apply(filteredUsers, searchUserDto);
            }
        }

        return filteredUsers
                .map(userMapper::toUserDto)
                .toList();
    }

    @Override
    public UserDto create(CreateUserDto userDto) {
        if (userDto.password().length() < minPasswordLength) {
            throw new DataValidationException("Password should be at least 6 characters");

        }
        User user = userMapper.toUser(userDto);
        Country country = countryRepository.getByIdOrThrow(userDto.countryId());
        user.setCountry(country);
        user = userRepository.save(user);
        log.info("User {} created", user.getId());
        return userMapper.toUserDto(user);
    }

    @Override
    public UserDto update(long userId, UpdateUserDto userDto) {
        long requestedId = userContext.getUserId();
        if (requestedId != userId) {
            throw new ForbiddenException("User " + requestedId + " does not match profile owner!");
        }
        User user = userRepository.getByIdOrThrow(userId);
        userMapper.update(userDto, user);
        Country country = countryRepository.getByIdOrThrow(userDto.countryId());
        user.setCountry(country);
        user = userRepository.save(user);
        log.info("User {} updated", user.getId());
        return userMapper.toUserDto(user);
    }

    @Override
    public UserDto getById(long userId) {
        User user = userRepository.getByIdOrThrow(userId);
        return userMapper.toUserDto(user);
    }
}
