package springboot.java17.realworld.service;

import springboot.java17.realworld.api.dto.profileDtos.response.ProfileResponseDto;

public interface ProfileService {

    ProfileResponseDto getProfileByUsername(String username);

    ProfileResponseDto followProfileByUsername(String usernameToFollow);

    ProfileResponseDto unfollowProfileByUsername(String usernameToUnfollow);

}