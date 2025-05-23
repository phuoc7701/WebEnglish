package vn.edu.english.controller.admin;
import vn.edu.english.dto.request.RoleCreateRequest;
import vn.edu.english.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.english.entity.User;
import vn.edu.english.service.admin.RoleService;

import java.util.List;

@RestController
@RequestMapping("admin/roles")
public class RoleController {
    @Autowired
    private RoleService  roleService;

    @GetMapping
    public List<Role> getAllRoles() {
        return roleService.getAllRoles();
    }

    @GetMapping("/{id}")
    Role getRole(@PathVariable("id") Long roleId){
        return roleService.getRoleById(roleId);
    }

    @PostMapping
    public Role createRole(@RequestBody RoleCreateRequest  role) {
        return roleService.createRole(role);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Role> updateRole(@PathVariable Long id, @RequestBody Role role) {
        Role updated = roleService.updateRole(id, role);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    String deleteUser(@PathVariable("id") Long roleId){
        roleService.deleteRole(roleId);
        return "Đã xóa câu hỏi thành công";
    }
}
