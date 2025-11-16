package com.homeheaven.repository;

import com.homeheaven.model.Property;
import com.homeheaven.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PropertyRepository extends JpaRepository<Property, Long> {
    @Query("SELECT p FROM Property p WHERE " +
           "(:city IS NULL OR LOWER(p.city) LIKE LOWER(CONCAT('%',:city,'%'))) " +
           "AND (:type IS NULL OR p.propertyType = :type) " +
           "AND (:minRent IS NULL OR p.rent >= :minRent) " +
           "AND (:maxRent IS NULL OR p.rent <= :maxRent)")
    List<Property> search(@Param("city") String city, @Param("type") String type,
                          @Param("minRent") Integer minRent, @Param("maxRent") Integer maxRent);

    List<Property> findByOwner(User owner);
}
