package springboot.java17.realworld.api.dto.userDtos.request;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@JsonRootName("user")
public class NewUserRequestDto {

    private String username;

    private String email;

    private String password;

}
