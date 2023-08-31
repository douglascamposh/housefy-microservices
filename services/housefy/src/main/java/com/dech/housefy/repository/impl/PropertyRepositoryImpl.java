package com.dech.housefy.repository.impl;

import com.dech.housefy.domain.Property;
import com.dech.housefy.domain.SubProperty;
import com.dech.housefy.error.DuplicateDataException;
import com.dech.housefy.repository.IPropertyRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;


@Repository
@RequiredArgsConstructor
public class PropertyRepositoryImpl implements IPropertyRepositoryImpl {
    private final MongoTemplate mongoTemplate;

    @Override
    public Property addSubProperty(String propertyId, SubProperty subProperty) {
        Update update = new Update();
        update.push("subProperties", subProperty);
        Query query = new Query(Criteria.where("_id").is(propertyId));
        mongoTemplate.upsert(query, update, "property");
        return mongoTemplate.findById(propertyId, Property.class);
    }

    @Override
    public Property findSubPropertyByCode(String code) {
        Query findQuery = new Query(Criteria.where("subProperties.code").is(code));
        return mongoTemplate.findOne(findQuery, Property.class);
    }
}
