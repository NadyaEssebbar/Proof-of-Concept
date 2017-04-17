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
    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    MongoOperations mongoOperations;

    @Override
    public Avatar getAvatarOfUser(Long userId) {
        return mongoTemplate.findOne
                (new Query(Criteria.where("userId").is(userId)), Avatar.class);
    }


}
