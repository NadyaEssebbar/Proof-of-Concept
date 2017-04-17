package be.kdg.repositories;

import be.kdg.domain.Avatar;

/**
 * Created by nadya on 31/03/2017.
 */
public interface AvatarRepositoryCustom
{
    Avatar getAvatarOfUser(Long userId);
}
