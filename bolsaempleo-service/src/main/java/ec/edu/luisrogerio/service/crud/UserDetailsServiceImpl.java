package ec.edu.luisrogerio.service.crud;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ec.edu.luisrogerio.common.enums.UserRole;
import ec.edu.luisrogerio.domain.Authority;
import ec.edu.luisrogerio.domain.DatosAdmin;
import ec.edu.luisrogerio.domain.DatosCandidato;
import ec.edu.luisrogerio.domain.DatosEmpleador;
import ec.edu.luisrogerio.dto.AppWebUser;
import ec.edu.luisrogerio.persistence.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	DatosCandidatoService candidatoService;

	@Autowired
	DatosEmpleadorService empleadorService;

	@Autowired
	DatosAdminService adminService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		AppWebUser appWebUser = null;

		ec.edu.luisrogerio.domain.User appUser = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("No existe usuario"));

		List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
		for (Authority authority : appUser.getAuthority()) {
			GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(authority.getAuthority());
			grantList.add(grantedAuthority);
		}

		// UserDetails user = (UserDetails) new User(appUser.getUsername(),
		// appUser.getPassword(), grantList);

		String authority = "";
		if (!grantList.isEmpty()) {
			authority = grantList.get(0).getAuthority();
		}

		String nombreCompleto = null;
		if (authority.equals(UserRole.ROLE_CANDIDATO.toString())) {
			Optional<DatosCandidato> candidatoOptional = candidatoService.buscarPorCedula(username);
			if (candidatoOptional.isPresent()) {
				nombreCompleto = candidatoOptional.get().getNombre() + " " + candidatoOptional.get().getApellido();
			}
		} else if (authority.equals(UserRole.ROLE_EMPLEADOR.toString())) {
			Optional<DatosEmpleador> empleadorOptional = empleadorService.buscarPorRuc(username);
			if (empleadorOptional.isPresent()) {
				nombreCompleto = empleadorOptional.get().getNombreEmpresa();
			}

		} else if (authority.equals(UserRole.ROLE_ADMIN.toString())) {
			Optional<DatosAdmin> adminOptional = adminService.buscarPorCedula(username);
			if (adminOptional.isPresent()) {
				nombreCompleto = adminOptional.get().getNombre() + " " + adminOptional.get().getApellido();
			}

		}
		appWebUser = new AppWebUser(appUser.getUsername(), appUser.getPassword(), appUser.isEnabled(), true, true, true,
				grantList, nombreCompleto);
		return appWebUser;
	}
}