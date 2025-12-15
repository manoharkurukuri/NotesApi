package com.notesapi.Controller;


import com.notesapi.DTO.UserRequestDto;
import com.notesapi.DTO.UserResponseDto;
import com.notesapi.Document.User;
import com.notesapi.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


import static com.notesapi.util.AppConstants.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(AUTH_PREFIX)
public class UserController {

    private final UserService userService;

    @PostMapping(CREATE_USER)
    public ResponseEntity<?> createUser(@Valid @RequestBody UserRequestDto userRequestDto){

          userService.createUser(userRequestDto);
          return ResponseEntity.status(201).body("User created successfully");
    }

    @PostMapping(LOGIN_USER)
    public ResponseEntity<?> loginUser(@RequestBody User user){

      UserResponseDto responseDto = userService.loginUser(user);

        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/check-auth")
    public ResponseEntity<?> checkAuth(Authentication authentication){
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(user);
    }

    @PutMapping("/update-profile")
    public ResponseEntity<?> updateProfile(Authentication authentication,
                                            @RequestBody User userrequest) {
        User user = (User) authentication.getPrincipal();
      User updatedUser =  userService.updateProfile(user.getId(), userrequest);
        return ResponseEntity.ok(updatedUser);
    }
 }
