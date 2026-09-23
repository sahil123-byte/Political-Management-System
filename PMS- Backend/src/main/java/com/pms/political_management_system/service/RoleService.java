package com.pms.political_management_system.service;

import com.pms.political_management_system.entity.Role;
import com.pms.political_management_system.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public Role getRoleById(Long id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role not found"));
    }

    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    public Role updateRole(Long id, Role role) {

        Role existingRole = getRoleById(id);

        existingRole.setRoleName(role.getRoleName());

        return roleRepository.save(existingRole);
    }

    public void deleteRole(Long id) {

        Role role = getRoleById(id);

        roleRepository.delete(role);
    }

}