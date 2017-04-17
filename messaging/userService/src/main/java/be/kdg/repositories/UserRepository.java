package be.kdg.repositories;

import be.kdg.domain.User;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by nadya on 30/03/2017.
 */
public interface UserRepository extends CrudRepository<User,Long>
{


}
