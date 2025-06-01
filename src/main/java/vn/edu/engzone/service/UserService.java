package vn.edu.engzone.service;

import vn.edu.engzone.dto.request.ChangePasswordRequest;
import vn.edu.engzone.dto.request.UserCreationRequest;
import vn.edu.engzone.dto.request.UserProfileRequest;
import vn.edu.engzone.dto.request.UserUpdateRequest;
import vn.edu.engzone.dto.response.UserProfileResponse;
import vn.edu.engzone.dto.response.UserResponse;
import vn.edu.engzone.entity.User;
import vn.edu.engzone.enums.Role;
import vn.edu.engzone.exception.AppException;
import vn.edu.engzone.exception.ErrorCode;
import vn.edu.engzone.mapper.UserMapper;
import vn.edu.engzone.repository.RoleRepository;
import vn.edu.engzone.repository.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserService {

    UserRepository userRepository;
    UserMapper userMapper;
    RoleRepository roleRepository;
    PasswordEncoder passwordEncoder;

    public UserResponse createUser(UserCreationRequest request){
        if (userRepository.existsByUsername(request.getUsername()))
            throw new AppException(ErrorCode.USER_EXISTED);

        User user = userMapper.toUser(request);

        // mã hóa mật khẩu cho user
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        HashSet<vn.edu.engzone.entity.Role> roles = new HashSet<>();
        roleRepository.findById(Role.USER.name()).ifPresent(roles::add);

        user.setRoles(roles);

        return userMapper.toUserResponse(userRepository.save(user));
    }

    public UserResponse getMyInfo() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();


        User user = userRepository.findByUsername(name).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTED));

        return userMapper.toUserResponse(user);
    }

    public UserResponse updateUser(String userId, UserUpdateRequest request){
//        //1. Lấy thông tin User
//        User user = getUser(userId);
//
//        //2. Map dữ liệu mới vào cho User
//        user.setPassword(request.getPassword());
//        user.setFirstName(request.getFirstName());
//        user.setLastName(request.getLastName());
//        user.setDob(request.getDob());
//
//        //3. Gọi userRepository để lưu lại entity này
//        return userRepository.save(user);


        //---> Sử dụng mapper để map dữ liệu
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        userMapper.updateUser(user, request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        var roles = roleRepository.findAllById(request.getRoles());
        user.setRoles(new HashSet<>(roles));

        return userMapper.toUserResponse(userRepository.save(user));
    }

    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }

    @PreAuthorize("hasRole('ADMIN')") //kiểm tra trước khi vào method
//@PreAuthorize("hasAuthority('APPROVE_POST')")
    public List<UserResponse> getUsers(){
        log.info("In method get Users");
        return userRepository.findAll().stream()
                .map(userMapper::toUserResponse).toList();
    }

    @PostAuthorize("returnObject.username == authentication.name")// kiểm tra sau khi method thực hiện xong
    public UserResponse getUser(String id){
        log.info("In method get user by Id");
        return userMapper.toUserResponse(userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found")));
    }

    public UserProfileResponse getProfile(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        return userMapper.toUserProfileResponse(user);
    }

    public UserProfileResponse updateProfile(String userId, UserProfileRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        user.setFullname(request.getFullname());
        user.setDob(request.getDob());
        user.setAvatarUrl(request.getAvatarUrl());
        user.setGender(request.getGender());

        return userMapper.toUserProfileResponse(userRepository.save(user));
    }

    public String changePassword(ChangePasswordRequest request) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new AppException(ErrorCode.INVALID_PASS); // cần define ErrorCode này nếu chưa có
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);

        return "Đổi mật khẩu thành công";
    }

}