package springboot.java17.realworld.api.dto.userDtos.request;

import com.fasterxml.jackson.annotation.JsonRootName;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@JsonRootName("user")
@NoArgsConstructor
public class NewUserRequestDto {

    @NotBlank(message = "username은 필수 입력 값입니다.")
    private String username;

    @NotBlank(message = "email은 필수 입력 값입니다.")
    @Email(message = "이메일을 입력해 주세요.")
    private String email;

    @NotBlank(message = "password는 필수 입력 값입니다.")
    private String password;


}
