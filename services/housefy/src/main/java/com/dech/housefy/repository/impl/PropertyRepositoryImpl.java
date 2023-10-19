package com.dech.housefy.repository.impl;

import com.dech.housefy.domain.Property;
import com.dech.housefy.domain.SubProperty;
import com.dech.housefy.error.DuplicateDataException;
import com.dech.housefy.repository.IPropertyRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;


@Repository
@RequiredArgsConstructor
public class PropertyRepositoryImpl implements IPropertyRepositoryImpl {
    private final MongoTemplate mongoTemplate;
    private final Logger logger = LoggerFactory.getLogger(PropertyRepositoryImpl.class);

    @Override
    public Property addSubProperty(String propertyId, SubProperty subProperty) {
        logger.info("Add sub property to property with id: " + propertyId);
        Update update = new Update();
        update.push("subProperties", subProperty);
        Query query = new Query(Criteria.where("_id").is(propertyId));
        mongoTemplate.upsert(query, update, Property.class);
        return mongoTemplate.findById(propertyId, Property.class);
    }

    @Override
    public Property updateSubProperty(String propertyId, SubProperty subProperty) {
        logger.info("Update sub property with sub id: " + subProperty.getId());
        Update update = new Update();
        Query query = new Query(Criteria.where("_id").is(propertyId).and("subProperties.id").is(subProperty.getId()));
        update.set("subProperties.$", subProperty);
        mongoTemplate.upsert(query, update, Property.class);
        return mongoTemplate.findById(propertyId, Property.class);
    }

    @Override
    public void deleteSubProperty(String propertyId, String subPropertyId) {
        logger.info("Delete sub property with sub id: " + subPropertyId + "from property Id: " + propertyId);
        Update update = new Update();
        Query query = new Query(Criteria.where("_id").is(propertyId).and("subProperties.id").is(subPropertyId));
        update.pull("subProperties", new Query(Criteria.where("_id").is(subPropertyId)));
        mongoTemplate.upsert(query, update, Property.class);
    }

    @Override
    public Property findSubPropertyByCode(String code) {
        //Todo: it should be find by propertyId and code
        Query findQuery = new Query(Criteria.where("subProperties.code").is(code));
        return mongoTemplate.findOne(findQuery, Property.class);
    }

    @Override
    public Property findByPropertyIdAndSubPropertyId(String propertyId, String subPropertyId) {
        Query findQuery = new Query(Criteria.where("id").is(propertyId).and("subProperties.id").is(subPropertyId));
        return mongoTemplate.findOne(findQuery, Property.class);
    }
}
