package school.faang.user_service.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import school.faang.user_service.config.context.UserContext;
import school.faang.user_service.dto.user.SearchUserDto;
import school.faang.user_service.dto.user.UserDto;
import school.faang.user_service.entity.User;
import school.faang.user_service.filter.TestUserCityFilter;
import school.faang.user_service.filter.TestUserExperienceFilter;
import school.faang.user_service.filter.UserFilter;
import school.faang.user_service.mapper.UserMapperImpl;
import school.faang.user_service.repository.CountryRepository;
import school.faang.user_service.repository.UserRepository;
import school.faang.user_service.service.user.UserService;
import school.faang.user_service.service.user.UserServiceImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    private UserService userService;

    @Spy
    private UserMapperImpl userMapper;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CountryRepository  countryRepository;

    @Mock
    private UserContext userContext;

    private UserFilter userExperienceFilter = new TestUserExperienceFilter();
    private UserFilter userCityFilter = new TestUserCityFilter();

    @BeforeEach
    public void setUp() {
        userService = new UserServiceImpl(userRepository, countryRepository, userMapper, userContext, List.of(
                userExperienceFilter, userCityFilter
        ));
    }

    @Test
    public void testGetUser() {
        User user1 = User.builder().city("city").experience(2).build();
        User user2 = User.builder().city("testCity").experience(8).build();

        when(userRepository.findAll()).thenReturn(List.of(user1, user2));

        List<UserDto> result = userService.getUsers(new SearchUserDto(null, null, null));
        assertTrue(result.isEmpty());
    }

    @Test
    public void testGetUser2() {
        User user1 = User.builder().city("city").experience(8).build();
        User user2 = User.builder().city("testCity").experience(8).build();

        when(userRepository.findAll()).thenReturn(List.of(user1, user2));

        List<UserDto> result = userService.getUsers(new SearchUserDto(null, null, null));
        assertEquals(1, result.size());
    }
}
