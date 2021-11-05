package ec.edu.luisrogerio.service.crud;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ec.edu.luisrogerio.common.AppException;
import ec.edu.luisrogerio.domain.User;
import ec.edu.luisrogerio.persistence.UserRepository;
import ec.edu.luisrogerio.service.GenericCRUDServiceImpl;

@Service
public class UserService extends GenericCRUDServiceImpl<User, Long> {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserRepository entityRepository;

	@Override
	public User guardar(User entity) {
		Optional<User> optional = buscar(entity);
		if (optional.isPresent()) {
			throw new AppException(String.format("Usuario ya registrado en el sistema", entity));
		} else {
			entity.setPassword(passwordEncoder.encode(entity.getPassword()));
			return entityRepository.save(entity);
		}
	}

	@Override
	public Optional<User> buscar(User entity) {
		return entityRepository.findByUsername(entity.getUsername());
	}

}
