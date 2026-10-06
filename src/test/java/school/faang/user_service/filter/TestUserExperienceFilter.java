package school.faang.user_service.filter;

import school.faang.user_service.dto.user.SearchUserDto;
import school.faang.user_service.entity.User;

import java.util.stream.Stream;

public class TestUserExperienceFilter implements UserFilter {

    @Override
    public Stream<User> apply(Stream<User> userStream, SearchUserDto searchUserDto) {
        return userStream.filter(user -> user.getExperience().equals(8));
    }

    @Override
    public boolean isApplicable(SearchUserDto searchUserDto) {
        return true;
    }
}
