package inf.unideb.webbackend.dto;

import inf.unideb.webbackend.model.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserResponseDTO {

  private Integer id;
  private String username;
  private String email;
  List<Role> roles;

}
