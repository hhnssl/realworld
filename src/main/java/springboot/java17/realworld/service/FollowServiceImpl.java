package springboot.java17.realworld.service;

import java.util.List;
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

    @Override
    public void unfollowUser(UserEntity me, UserEntity followingUser) {
        FollowEntity followEntity = followRepository.findByUserAndFollowing(me, followingUser)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 결과입니다."));

        followRepository.deleteById(followEntity.getId());
    }

    @Override
    public List<FollowEntity> getFollowingList(UserEntity me) {

        List<FollowEntity> followingList = followRepository.findAllByUser(me);

        return followingList;
    }
}
