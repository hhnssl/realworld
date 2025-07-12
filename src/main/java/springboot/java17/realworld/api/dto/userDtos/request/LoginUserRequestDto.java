package springboot.java17.realworld.api.dto.userDtos.request;

import com.fasterxml.jackson.annotation.JsonRootName;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
@JsonRootName("user")
public class LoginUserRequestDto {

    @NotBlank(message = "email이 입력되지 않았습니다.")
    private String email;

    @NotBlank(message = "password가 입력되지 않았습니다.")
    private String password;


}
