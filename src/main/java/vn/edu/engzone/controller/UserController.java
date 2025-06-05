package vn.edu.engzone.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.engzone.dto.request.*;
import vn.edu.engzone.dto.response.CloudinaryResponse;
import vn.edu.engzone.dto.response.UserProfileResponse;
import vn.edu.engzone.dto.response.UserResponse;
import vn.edu.engzone.service.UserService;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j

public class UserController {
    UserService userService;

    //api tạo thông tin user
    @PostMapping
    ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest request) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.createUser(request))
                .build();
    }

    //2 api get thông tin user (getAll và get theo Id)
    @GetMapping
    ApiResponse<List<UserResponse>> getUsers() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        log.info("Username: {}", authentication.getName());
        authentication.getAuthorities().forEach(grantedAuthority -> log.info(grantedAuthority.getAuthority()));

        return ApiResponse.<List<UserResponse>>builder()
                .result(userService.getUsers())
                .build();
    }

    @GetMapping("/{userId}")
    ApiResponse<UserResponse> getUser(@PathVariable("userId") String userId) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.getUser(userId))
                .build();
    }

    @PutMapping("/{userId}")
    ApiResponse<UserResponse> updateUser(
            @PathVariable String userId,
            @RequestBody @Valid UserUpdateRequest request
    ) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.updateUser(userId, request))
                .build();
    }

    @DeleteMapping("/{userId}")
    ApiResponse<String> deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
        return ApiResponse.<String>builder().result("User has been deleted").build();
    }

    @GetMapping("/myInfo")
    ApiResponse<UserResponse> getMyInfo() {
        return ApiResponse.<UserResponse>builder()
                .result(userService.getMyInfo())
                .build();
    }

    //api update thông tin
    @GetMapping("/{userId}/profile")
    ApiResponse<UserProfileResponse> getProfile(@PathVariable String userId) {
        return ApiResponse.<UserProfileResponse>builder()
                .result(userService.getProfile(userId))
                .build();
    }

    @PutMapping("/{userId}/profile")
    ApiResponse<UserProfileResponse> updateProfile(@PathVariable String userId,
                                                   @RequestBody @Valid UserProfileRequest request) {
        return ApiResponse.<UserProfileResponse>builder()
                .result(userService.updateProfile(userId, request))
                .build();
    }

    @PutMapping("/change-password")
    ApiResponse<String> changePassword(@RequestBody @Valid ChangePasswordRequest request) {
        return ApiResponse.<String>builder()
                .result(userService.changePassword(request))
                .build();
    }

    @PutMapping("/forgot-password/{email}")
    ApiResponse<String> forgotPassword(@PathVariable String email) {
        return ApiResponse.<String>builder()
                .result(userService.forgotPassword(email))
                .build();
    }

    @GetMapping("/avatar")
    public ApiResponse<Map<String, String>> getCurrentUserAvatar() {
        String avatarUrl = userService.getCurrentUserAvatarUrl();
        Map<String, String> result = new HashMap<>();
        result.put("avatarUrl", avatarUrl);
        return ApiResponse.<Map<String, String>>builder()
                .result(result)
                .build();
    }

    @PostMapping("/upload-avatar")
    ApiResponse<UserResponse> uploadAvatar(@RequestParam("avatarFile") MultipartFile avatarFile) throws IOException {
        return ApiResponse.<UserResponse>builder()
                .result(userService.uploadAvatar(avatarFile))
                .build();
    }

}
