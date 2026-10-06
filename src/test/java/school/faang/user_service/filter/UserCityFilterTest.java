package school.faang.user_service.filter;

import org.junit.jupiter.api.Test;
import school.faang.user_service.dto.user.SearchUserDto;
import school.faang.user_service.entity.User;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class UserCityFilterTest {

    private final UserCityFilter userCityFilter = new UserCityFilter();

    @Test
    public void testIsApplicableTrue() {
        boolean result = userCityFilter.isApplicable(new SearchUserDto(null, "city", null));
        assertTrue(result);
    }

    @Test
    public void testIsApplicableFalse() {
        boolean result = userCityFilter.isApplicable(new SearchUserDto(null, null, null));
        assertFalse(result);
    }

    @Test
    public void testIsApplicableFalseWhenCityIsBlank() {
        boolean result = userCityFilter.isApplicable(new SearchUserDto(null, "  ", null));
        assertFalse(result);
    }

    @Test
    public void testIsApplicableFalseWhenCityIsEmpty() {
        boolean result = userCityFilter.isApplicable(new SearchUserDto(null, "", null));
        assertFalse(result);
    }

    @Test
    public void testApply() {
        Stream<User> users = Stream.of(
                User.builder().city("Brussels").build(),
                User.builder().city("Paris").build()
        );
        Stream<User> usersFromBrussels = userCityFilter.apply(users,
                new SearchUserDto(null, "Brussels", null));

        List<User> userList = usersFromBrussels.toList();
        assertEquals(1, userList.size());
        assertEquals("Brussels", userList.get(0).getCity());
    }

    @Test
    public void testApplyIgnoreCase() {
        Stream<User> users = Stream.of(
                User.builder().city("Brussels").build(),
                User.builder().city("brussels").build()
        );
        Stream<User> usersFromBrussels = userCityFilter.apply(users,
                new SearchUserDto(null, "BruSseLs", null));

        List<User> userList = usersFromBrussels.toList();
        assertEquals(2, userList.size());
        assertEquals("brussels", userList.get(0).getCity().toLowerCase());
        assertEquals("brussels", userList.get(1).getCity().toLowerCase());
    }

    @Test
    public void testApplyNoSuitableUsers() {
        Stream<User> users = Stream.of(
                User.builder().city("Antwerpen").build(),
                User.builder().city("Paris").build()
        );
        Stream<User> usersFromBrussels = userCityFilter.apply(users,
                new SearchUserDto(null, "Brussels", null));

        List<User> userList = usersFromBrussels.toList();
        assertEquals(0, userList.size());
    }
}
