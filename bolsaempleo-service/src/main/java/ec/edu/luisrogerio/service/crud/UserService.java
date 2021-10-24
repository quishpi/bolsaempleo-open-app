package ec.edu.luisrogerio.service.crud;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ec.edu.luisrogerio.domain.User;
import ec.edu.luisrogerio.dto.UserDTO;
import ec.edu.luisrogerio.persistence.UserRepository;
import ec.edu.luisrogerio.service.GenericCRUDServiceImpl;

@Service
public class UserService extends GenericCRUDServiceImpl<User, UserDTO> {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserRepository entityRepository;

	@Override
	public UserDTO build(User entity) {
		return null;
	}

	@Override
	public Optional<User> buscar(UserDTO dto) {
		return entityRepository.findByUsername(dto.getUsername());
	}

	@Override
	public User mapTo(UserDTO dto) {
		User user = new User();
		user.setActive(dto.isActive());
		user.setPassword(passwordEncoder.encode(dto.getPassword()));
		user.setRole(dto.getRole());
		user.setUsername(dto.getUsername());
		return user;
	}

}
