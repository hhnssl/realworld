package springboot.java17.realworld.service;

import org.springframework.stereotype.Service;
import springboot.java17.realworld.entity.FollowEntity;
import springboot.java17.realworld.entity.UserEntity;
import springboot.java17.realworld.repository.FollowRepository;

@Service
public class FollowServiceImpl implements FollowService{

    private final FollowRepository followRepository;

    public FollowServiceImpl(FollowRepository followRepository) {
        this.followRepository = followRepository;
    }

    @Override
    public void followUser( UserEntity me, UserEntity followingUser) {
        FollowEntity followRelation = FollowEntity.builder()
            .user(me)
            .following(followingUser)
            .build();

        followRepository.save(followRelation);
    }
}
