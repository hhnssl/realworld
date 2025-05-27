package springboot.java17.realworld.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springboot.java17.realworld.api.dto.profileDtos.response.ProfileResponseDto;
import springboot.java17.realworld.service.ProfileService;

@RestController
@RequestMapping("/api/profiles")
public class ProfileController {
    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/{username}")
    public ResponseEntity<ProfileResponseDto> getProfile(@PathVariable("username") String username){

        ProfileResponseDto profileDto = profileService.getProfileByUsername(username);

        return ResponseEntity.ok(profileDto);
    }

    @PostMapping("/{username}/follow")
    public ResponseEntity<ProfileResponseDto> followUser(@PathVariable("username") String username){

        ProfileResponseDto profileDto = profileService.followProfileByUsername(username);

        return ResponseEntity.ok(profileDto);
    }

}
