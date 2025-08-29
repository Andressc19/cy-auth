package co.com.pragma.model.userrole.exceptions;

import co.com.pragma.model.exceptions.DomainException;

public class RoleNotExistsException extends DomainException {
	public RoleNotExistsException(Short roleId) {
		super("Role does not exist: " + roleId);
	}
}
