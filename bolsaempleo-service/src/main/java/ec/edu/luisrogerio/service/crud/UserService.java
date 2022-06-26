package ec.edu.luisrogerio.service.crud;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.edu.insteclrg.domain.User;
import ec.edu.insteclrg.persistence.UserRepository;
import ec.edu.insteclrg.service.GenericCRUDServiceImpl;

@Service
public class UserService extends GenericCRUDServiceImpl<User, Long> {

	@Autowired
	private UserRepository entityRepository;

	
	@Override
	public Optional<User> buscar(User dto) {
		return entityRepository.findByUsername(dto.getUsername());
	}

	
}
