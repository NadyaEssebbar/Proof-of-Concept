package be.kdg.persistence;

import be.kdg.dom.Avatar;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import sun.security.x509.AVA;

/**
 * Created by nadya on 16/04/2017.
 */
@Repository
public interface AvatarRepository extends CrudRepository<Avatar, Integer>
{

}
