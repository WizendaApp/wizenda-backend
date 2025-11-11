package ao.wiza.backend.security;

import ao.wiza.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class SecurityUserDetailsService implements UserDetailsService {
  private final UserRepository repository;

  @Override
  public @NonNull UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {
    var user = repository.findByUsernameOrEmail(username);

    if (user == null) {
      throw new IllegalStateException("User not found");
    }

    var roles = new ArrayList<GrantedAuthority>();

    if (user.getRole() != null) {
      roles.add(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
    }

    return new User(
        user.getUsername(),
        user.getPassword(),
        roles
    );
  }
}
