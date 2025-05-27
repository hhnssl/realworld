package springboot.java17.realworld.service;

import springboot.java17.realworld.entity.UserEntity;

public interface FollowService {

    void followUser(UserEntity me, UserEntity followingUser);

}
