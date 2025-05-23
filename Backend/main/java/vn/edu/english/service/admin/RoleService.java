package vn.edu.english.service.admin;

import vn.edu.english.dto.request.RoleCreateRequest;
import vn.edu.english.dto.request.UserCreateRequest;
import vn.edu.english.dto.request.UserUpdateRequest;
import vn.edu.english.entity.Role;
import vn.edu.english.entity.User;
import vn.edu.english.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.english.repository.UserRepository;

import java.util.List;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public Role getRoleById(Long id) {
        return roleRepository.findById(id).orElseThrow(()->new RuntimeException("Role Không tồn tài"));
    }

    public Role createRole(RoleCreateRequest request) {
        Role role = new Role();
        role.setRoleName(request.getRoleName());
        return roleRepository.save(role);
    }

    public Role updateRole(Long id, Role request){
        Role role = getRoleById(id);
        role.setRoleName(request.getRoleName());
        return roleRepository.save(role);
    }

    public boolean deleteRole(Long id) {
        if (roleRepository.existsById(id)) {
            roleRepository.deleteById(id);
            return true;
        }
        return false;
    }


}
