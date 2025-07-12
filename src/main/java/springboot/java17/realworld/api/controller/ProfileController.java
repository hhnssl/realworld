package springboot.java17.realworld.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springboot.java17.realworld.api.dto.profileDtos.response.ProfileResponseDto;
import springboot.java17.realworld.service.CustomUserDetails;
import springboot.java17.realworld.service.ProfileService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/profiles")
public class ProfileController {

    private final ProfileService profileService;


    @GetMapping("/{username}")
    public ResponseEntity<ProfileResponseDto> getUserProfile(
        @PathVariable("username") String username) {

        ProfileResponseDto response = profileService.getProfileByUsername(username);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/{username}/follow")
    public ResponseEntity<ProfileResponseDto> followUser(@PathVariable("username") String username,
        @AuthenticationPrincipal CustomUserDetails userDetails) {

        ProfileResponseDto response = profileService.followProfileByUsername(
            userDetails.getUsername(), username);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{username}/follow")
    public ResponseEntity<ProfileResponseDto> unfollowUser(@PathVariable("username") String username,
        @AuthenticationPrincipal UserDetails userDetails) {

        ProfileResponseDto response = profileService.unfollowProfileByUsername(userDetails.getUsername(), username);

        return ResponseEntity.ok(response);
    }
}
