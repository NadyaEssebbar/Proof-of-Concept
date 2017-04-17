package be.kdg.repositories;

import be.kdg.domain.Avatar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;

/**
 * Created by nadya on 5/02/2017.
 */

public class AvatarRepositoryImpl implements AvatarRepositoryCustom
{
    private final
    MongoTemplate mongoTemplate;

    private final
    MongoOperations mongoOperations;

    @Autowired
    public AvatarRepositoryImpl(MongoTemplate mongoTemplate, MongoOperations mongoOperations) {
        this.mongoTemplate = mongoTemplate;
        this.mongoOperations = mongoOperations;
    }

    @Override
    public Avatar getAvatarOfUser(Long userId) {
        return mongoTemplate.findOne
                (new Query(Criteria.where("userId").is(userId)), Avatar.class);
    }


}
