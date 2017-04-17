package be.kdg.services;

import be.kdg.controllers.AvatarDTO;
import be.kdg.domain.Avatar;

import java.io.IOException;
import java.util.concurrent.Future;

/**
 * Created by nadya on 31/03/2017.
 */

public interface AvatarService
{
    Avatar getAvatar(Long UserId) throws IOException;
    void addAvatar(AvatarDTO avatarDTO) throws IOException;
    String callOtherService(String service, String mapping);
    void writeToFile(String text);
    void dropCollection();
}
