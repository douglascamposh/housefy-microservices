package com.dech.housefy.repository;

import com.dech.housefy.domain.Sale;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ISaleRepository extends MongoRepository<Sale, String> {
    Optional<Sale> findBySubPropertyIdAndDeletedIsFalse(String subPropertyId);
    List<Sale> findAllByPropertyId(String propertyId);
    List<Sale> findAllByReservationExpiresDateIsLessThanAndDeletedIsFalse(Long currentDate);
}
