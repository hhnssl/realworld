package springboot.java17.realworld.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springboot.java17.realworld.api.dto.userDtos.request.LoginUserRequestDto;
import springboot.java17.realworld.api.dto.userDtos.request.NewUserRequestDto;
import springboot.java17.realworld.api.dto.userDtos.request.UpdateUserRequestDto;
import springboot.java17.realworld.api.dto.userDtos.response.UserResponseDto;
import springboot.java17.realworld.service.UserService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;


    @PostMapping("/users")
    public ResponseEntity<UserResponseDto> signUp(@RequestBody NewUserRequestDto requestDto) {
        UserResponseDto userDto = userService.save(requestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(userDto);
    }

    @PostMapping("/users/login")
    public ResponseEntity<UserResponseDto> signIn(@Valid @RequestBody LoginUserRequestDto request) {
        UserResponseDto response = userService.login(request);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/user")
    public ResponseEntity<UserResponseDto> getCurrentUser(){
        UserResponseDto userDto = userService.findUser();

        return ResponseEntity.status(HttpStatus.OK).body(userDto);
    }

    @PutMapping("/user")
    public ResponseEntity<UserResponseDto> updateUser(@RequestBody UpdateUserRequestDto dto){
        UserResponseDto userDto = userService.updateUser(dto);

        return ResponseEntity.status(HttpStatus.OK).body(userDto);
    }

}
