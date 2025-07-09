package springboot.java17.realworld.api.dto.userDtos.request;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
@JsonRootName("user")
public class LoginUserRequestDto {

    private String email;

    private String passwrod;


}
