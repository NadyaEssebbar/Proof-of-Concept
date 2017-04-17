package be.kdg.repositories;

import be.kdg.domain.Avatar;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by nadya on 31/03/2017.
 */
public interface AvatarRepository  extends CrudRepository<Avatar,Integer>, AvatarRepositoryCustom
{

}
