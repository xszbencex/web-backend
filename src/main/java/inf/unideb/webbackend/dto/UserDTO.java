package inf.unideb.webbackend.dto;

import inf.unideb.webbackend.model.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserDTO {

  private String username;
  private String email;
  private String password;
  List<Role> roles;

}
