package be.kdg.persistence;

import be.kdg.dom.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by nadya on 30/03/2017.
 */
@Repository
public interface UserRepository extends CrudRepository<User,Long>
{

}
