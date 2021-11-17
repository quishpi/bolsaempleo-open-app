package ec.edu.insteclrg.service.crud;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ec.edu.insteclrg.common.AppException;
import ec.edu.insteclrg.domain.User;
import ec.edu.insteclrg.dto.PasswordDTO;
import ec.edu.insteclrg.persistence.UserRepository;
import ec.edu.insteclrg.service.GenericCRUDServiceImpl;

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

	public Optional<User> buscarUsuarioAndPassword(User entity) {
		entity.setPassword(passwordEncoder.encode(entity.getPassword()));
		return entityRepository.findByUsernameAndPassword(entity.getUsername(), entity.getPassword());
		/*
		 * if (optional.isPresent()) { return optional; } else { throw new
		 * AppException(String.format("Usuario no registrado en el sistema", entity)); }
		 */
	}

	public void actualizarPassword(String username, PasswordDTO passwordDTO) {
		if (passwordDTO.getOldPassword().compareTo(passwordDTO.getNewPassword()) == 0) {
			throw new AppException("La nueva contraseña no puede ser igual a la anterior");
		}
		String newEncodedPassword = passwordEncoder.encode(passwordDTO.getNewPassword());
		User usuario = new User();
		usuario.setUsername(username);
		Optional<User> optionalUser = buscar(usuario);
		if (!optionalUser.isPresent()) {
			throw new AppException(String.format("El usuario %s no se encuentra registrado", username));
		}
		User user = optionalUser.get();
		String storedPassword = user.getPassword();
		if (!passwordEncoder.matches(passwordDTO.getOldPassword(), storedPassword)) {
			throw new AppException(String.format("La contraseña actual del usuario %s no es correcto", username));
		}
		user.setPassword(newEncodedPassword);
		entityRepository.save(user);
	}
}
