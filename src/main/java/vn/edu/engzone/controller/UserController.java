package vn.edu.engzone.controller;

import lombok.extern.slf4j.Slf4j;
import vn.edu.engzone.dto.request.*;
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

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j

public class UserController {
    UserService userService;

    //api tạo thông tin user
    @PostMapping
//    User createUser(@RequestBody @Valid UserCreationRequest request) {
//        return userService.createUser(request);
//    }
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

    @PostMapping("/change-password")
    ApiResponse<String> changePassword(@RequestBody @Valid ChangePasswordRequest request) {
        return ApiResponse.<String>builder()
                .result(userService.changePassword(request))
                .build();
    }

}
