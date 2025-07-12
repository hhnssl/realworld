package springboot.java17.realworld.service;

import springboot.java17.realworld.api.dto.profileDtos.response.ProfileResponseDto;

public interface ProfileService {

    ProfileResponseDto getProfileByUsername(String username);

    ProfileResponseDto followProfileByUsername(String currentUserEmail, String usernameToFollow);

    ProfileResponseDto unfollowProfileByUsername(String currentUserEmail, String usernameToUnfollow);

}
