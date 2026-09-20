package school.faang.user_service.repository;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.repository.CrudRepository;
import school.faang.user_service.entity.Country;

import java.util.List;
import java.util.Set;

public interface CountryRepository extends CrudRepository<Country, Long> {

    List<Country> findByTitleIn(Set<String> titles);

    default Country getByIdOrThrow(long countryId) {
        return findById(countryId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Country with id %d not found", countryId)));
    }
}