package springboot.java17.realworld.service;

import java.util.List;
import springboot.java17.realworld.entity.FollowEntity;
import springboot.java17.realworld.entity.UserEntity;

public interface FollowService {

    void followUser(UserEntity me, UserEntity followingUser);

    void unfollowUser(UserEntity me, UserEntity followingUser);

    List<FollowEntity> getFollowingList(UserEntity me);

}
