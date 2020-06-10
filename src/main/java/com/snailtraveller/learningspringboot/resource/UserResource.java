package com.snailtraveller.learningspringboot.resource;

import com.snailtraveller.learningspringboot.model.User;
import com.snailtraveller.learningspringboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(
        path = "/api/v1/users"
)
public class UserResource {
    private UserService userService;

    @Autowired
    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<User> fetchUsers() {
        return userService.getAllUsers();
    }

    @RequestMapping(method = RequestMethod.GET,
            path = "{userUid}")
    public ResponseEntity<?> fetchUser(@PathVariable("userUid") UUID userUid) {
        Optional<User> userOptional = userService.getUser(userUid);
        if (userOptional.isPresent()) {
            return ResponseEntity.ok(userOptional.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorMessage("user " + userUid + " was not found"));
    }

    class ErrorMessage {
        String errorMessage;

        public ErrorMessage(String message) {
            this.errorMessage = message;
        }

        public String getErrorMessage() {
            return errorMessage;
        }

        public void setErrorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
        }

    }
}
