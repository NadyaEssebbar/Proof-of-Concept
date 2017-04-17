package be.kdg.services.api;

import be.kdg.dom.Avatar;

import java.util.List;

/**
 * Created by nadya on 16/04/2017.
 */
public interface AvatarService
{
    Avatar createAvatar(String name);
    List<Avatar> findAvatars();
}
