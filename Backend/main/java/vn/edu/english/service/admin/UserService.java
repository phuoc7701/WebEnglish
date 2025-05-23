package vn.edu.english.service.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.english.dto.request.UserCreateRequest;
import vn.edu.english.dto.request.UserUpdateRequest;
import vn.edu.english.entity.Role;
import vn.edu.english.entity.User;
import vn.edu.english.repository.RoleRepository;
import vn.edu.english.repository.UserRepository;

import java.util.List;

@Service
public  class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;


    public User UserCreate(UserCreateRequest request){
        User user = new User();

        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        if(userRepository.existsByEmail(request.getEmail())){
            throw new RuntimeException("Email đã được đăng kí");
        }
        user.setDob(request.getDob());
        user.setSex(request.getSex());
        user.setPhone(request.getPhone());
        user.setPassword(request.getPassword());
        Role role = roleRepository.findById(request.getRoleId())
                .orElseThrow(() -> new RuntimeException("Role not found"));
        user.setRole(role);
        return userRepository.save(user);
    }

    public List<User> UserGetAll(){
        return userRepository.findAll();
    }

    public User UpdateUser(Long userId, UserUpdateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User không tồn tại với id: " + userId));

        if (request.getFullName() != null) user.setFullName(request.getFullName());
        if (request.getEmail() != null) user.setEmail(request.getEmail());
        if (request.getPhone() != null) user.setPhone(request.getPhone());
        if (request.getAccountStatus() != null) user.setAccountStatus(request.getAccountStatus());

        if (request.getRoleId() != null) {
            Role role = roleRepository.findById(request.getRoleId())
                    .orElseThrow(() -> new RuntimeException("Role không tồn tại với id: " + request.getRoleId()));
            user.setRole(role);
        }

        return userRepository.save(user);
    }

    public void DeleteUser(Long id){
        User user = GetUserById(id);
//        user.setRole();
        userRepository.deleteById(id);
    }

    public User GetUserById(Long id){
        return userRepository.findById(id).orElseThrow(()->new RuntimeException("Tài khoản không tồn tài"));
    }


}