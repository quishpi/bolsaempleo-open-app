package ec.edu.luisrogerio.service.crud;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.edu.luisrogerio.domain.User;
import ec.edu.luisrogerio.persistence.UserRepository;
import ec.edu.luisrogerio.service.GenericCRUDServiceImpl;

@Service
public class UserService extends GenericCRUDServiceImpl<User, Long> {

	@Autowired
	private UserRepository entityRepository;

	@Override
	public Optional<User> buscar(User domainObject) {
		return entityRepository.findByUsername(domainObject.getUsername());
	}

}
