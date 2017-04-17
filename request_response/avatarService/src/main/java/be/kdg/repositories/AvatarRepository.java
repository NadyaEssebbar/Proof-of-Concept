package be.kdg.repositories;

import be.kdg.domain.Avatar;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by nadya on 31/03/2017.
 */
@Repository
public interface AvatarRepository  extends CrudRepository<Avatar,Integer>, AvatarRepositoryCustom
{

}
