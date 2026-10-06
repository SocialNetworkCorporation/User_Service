package school.faang.user_service.filter;

import org.springframework.stereotype.Component;
import school.faang.user_service.dto.user.SearchUserDto;
import school.faang.user_service.entity.User;

import java.util.stream.Stream;

@Component
public class UserCityFilter implements UserFilter {

    @Override
    public Stream<User> apply(Stream<User> users, SearchUserDto searchUserDto) {
        return users
                .filter(user -> searchUserDto.city().equalsIgnoreCase(user.getCity()));
    }

    @Override
    public boolean isApplicable(SearchUserDto searchUserDto) {
        return searchUserDto.city() != null && !searchUserDto.city().isBlank();
    }
}
