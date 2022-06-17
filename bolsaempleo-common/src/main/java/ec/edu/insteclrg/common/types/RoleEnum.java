package ec.edu.insteclrg.common.types;

import java.util.Optional;
import java.util.stream.Stream;


public enum RoleEnum {
	ROLE_ADMIN,
    ROLE_CANDIDATO,
	ROLE_EMPLEADOR;

    RoleEnum() {
    }

    public static Optional<RoleEnum> getFromRole(String role) {
        Optional<RoleEnum> op = Stream.of(RoleEnum.values())
                .filter(p -> p.name().compareTo(role) == 0)
                .findFirst();
        return op;
    }
}
