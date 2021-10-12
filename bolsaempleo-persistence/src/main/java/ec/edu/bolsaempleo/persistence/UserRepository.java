package ec.edu.bolsaempleo.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ec.edu.bolsaempleo.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
