package inf.unideb.webbackend.service;

import inf.unideb.webbackend.dto.UserDTO;
import inf.unideb.webbackend.dto.UserResponseDTO;
import inf.unideb.webbackend.exception.CustomException;
import inf.unideb.webbackend.model.Role;
import inf.unideb.webbackend.model.User;
import inf.unideb.webbackend.repository.UserRepository;
import inf.unideb.webbackend.security.JwtTokenProvider;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;

@Service
public class UserService extends BaseService<UserDTO, User> {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtTokenProvider jwtTokenProvider;
  private final AuthenticationManager authenticationManager;

  public UserService(UserRepository userRepository,
                     PasswordEncoder passwordEncoder,
                     JwtTokenProvider jwtTokenProvider,
                     AuthenticationManager authenticationManager) {
    super(UserDTO.class, User.class);
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.jwtTokenProvider = jwtTokenProvider;
    this.authenticationManager = authenticationManager;
  }

  public String signIn(String username, String password) {
    try {
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
      return jwtTokenProvider.createToken(username, userRepository.findByUsername(username).getRoles());
    } catch (AuthenticationException e) {
      throw new CustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
    }
  }

  public String signUp(UserDTO userDTO) {
    final User user = mapFromDTO(userDTO);
    if (!userRepository.existsByUsername(user.getUsername())) {
      user.setPassword(passwordEncoder.encode(user.getPassword()));
      if (user.getRoles().isEmpty()) {
        user.setRoles(new ArrayList<>(Collections.singletonList(Role.ROLE_CLIENT)));
      }
      userRepository.save(user);
      return jwtTokenProvider.createToken(user.getUsername(), user.getRoles());
    } else {
      throw new CustomException("Username is already in use", HttpStatus.UNPROCESSABLE_ENTITY);
    }
  }

  public void delete(String username) {
    userRepository.deleteByUsername(username);
  }

  public UserResponseDTO getUserInfo(HttpServletRequest req) {
    final User user = userRepository.findByUsername(jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req)));
    UserResponseDTO userResponseDTO = new UserResponseDTO();
    userResponseDTO.setUsername(user.getUsername());
    userResponseDTO.setEmail(user.getEmail());
    userResponseDTO.setRoles(user.getRoles());
    userResponseDTO.setId(user.getId());
    return userResponseDTO;
  }

  public String refresh(String username) {
    return jwtTokenProvider.createToken(username, userRepository.findByUsername(username).getRoles());
  }

}
