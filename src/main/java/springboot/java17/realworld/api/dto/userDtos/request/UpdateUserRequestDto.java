package springboot.java17.realworld.api.dto.userDtos.request;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonRootName("user")
public class UpdateUserRequestDto {

    private String username;

    private String password;

    private String image;

    private String bio;
}
