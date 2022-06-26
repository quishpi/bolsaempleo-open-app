package ec.edu.insteclrg.service.crud;

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

import ec.edu.insteclrg.common.enums.UserRole;
import ec.edu.insteclrg.domain.Authority;
import ec.edu.insteclrg.domain.admin.DatosAdmin;
import ec.edu.insteclrg.domain.candidato.DatosCandidato;
import ec.edu.insteclrg.domain.empleador.DatosEmpleador;
import ec.edu.insteclrg.dto.AppWebUserDTO;
import ec.edu.insteclrg.persistence.UserRepository;
import ec.edu.insteclrg.service.admin.DatosAdminService;
import ec.edu.insteclrg.service.candidato.DatosCandidatoService;
import ec.edu.insteclrg.service.empleador.DatosEmpleadorService;

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

		AppWebUserDTO appWebUser = null;

		ec.edu.insteclrg.domain.User appUser = userRepository.findByUsername(username)
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

		String fullname = null;
		byte[] foto = null;
		if (authority.equals(UserRole.ROLE_CANDIDATO.toString())) {
			Optional<DatosCandidato> candidatoOptional = candidatoService.buscarPorCedula(username);
			if (candidatoOptional.isPresent()) {
				fullname = candidatoOptional.get().getNombre() + " " + candidatoOptional.get().getApellido();
				foto = candidatoOptional.get().getFoto();
			}
		} else if (authority.equals(UserRole.ROLE_EMPLEADOR.toString())) {
			Optional<DatosEmpleador> empleadorOptional = empleadorService.buscarPorRuc(username);
			if (empleadorOptional.isPresent()) {
				fullname = empleadorOptional.get().getNombreEmpresa();
				foto = empleadorOptional.get().getLogo();
			}

		} else if (authority.equals(UserRole.ROLE_ADMIN.toString())) {
			Optional<DatosAdmin> adminOptional = adminService.buscarPorEmail(username);
			if (adminOptional.isPresent()) {
				fullname = adminOptional.get().getNombre() + " " + adminOptional.get().getApellido();
			}

		}
		appWebUser = new AppWebUserDTO(appUser.getUsername(), appUser.getPassword(), appUser.isEnabled(), true, true, true,
				grantList, appUser.getId(), fullname, foto);
		return appWebUser;
	}
}