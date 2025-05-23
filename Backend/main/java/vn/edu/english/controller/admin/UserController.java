package vn.edu.english.controller.admin;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vn.edu.english.dto.request.UserCreateRequest;
import vn.edu.english.dto.request.UserUpdateRequest;
import vn.edu.english.entity.User;
import vn.edu.english.service.admin.RoleService;
import vn.edu.english.service.admin.UserService;

import java.util.List;

@RestController
@RequestMapping("/admin/users")
public  class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/register" )
    User createUser(@RequestBody @Validated UserCreateRequest request){
        return userService.UserCreate(request);
    }

    @GetMapping("")
    List<User> users(){
        return userService.UserGetAll();
    }

    @GetMapping("/{userId}")
    User getUser(@PathVariable("userId") Long userId){
        return userService.GetUserById(userId);
    }
    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable Long userId, @RequestBody @Valid UserUpdateRequest request) {
        User updatedUser = userService.UpdateUser(userId, request);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    String deleteUser(@PathVariable("id") Long userId){
        userService.DeleteUser(userId);
        return "Đã xóa tài khoản thành công";
    }
}