package book.demo.java.service;

import java.util.Set;

public interface PermissionService {

    Set<String> getPermissionNamesByRoleId(int roleId);

}
