package com.pms.political_management_system.controller;

import com.pms.political_management_system.entity.Role;
import com.pms.political_management_system.service.RoleService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    // Any logged-in user can read the role list (needed to populate
    // role dropdowns in forms like Add/Edit User and Profile).
    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public List<Role> getAllRoles() {
        return roleService.getAllRoles();
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public Role getRoleById(@PathVariable Long id) {
        return roleService.getRoleById(id);
    }

    // Only ADMIN can create/edit/delete roles - this controls what
    // permission levels exist in the system, so it's a sensitive operation.
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public Role saveRole(@RequestBody Role role) {
        return roleService.saveRole(role);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public Role updateRole(
            @PathVariable Long id,
            @RequestBody Role role) {

        return roleService.updateRole(id, role);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public String deleteRole(@PathVariable Long id) {

        roleService.deleteRole(id);

        return "Role deleted successfully";
    }

}
