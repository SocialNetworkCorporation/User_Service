package school.faang.user_service.filter;

import school.faang.user_service.dto.user.SearchUserDto;
import school.faang.user_service.entity.User;

import java.util.stream.Stream;

public interface UserFilter {

    Stream<User> apply(Stream<User> userStream, SearchUserDto searchUserDto);

    boolean isApplicable(SearchUserDto searchUserDto);
}
