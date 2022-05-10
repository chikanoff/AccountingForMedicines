package by.accounting.medicines.service;

import by.accounting.medicines.auth.jwt.model.UserDetailsImpl;
import by.accounting.medicines.exception.ItemNotFoundException;
import by.accounting.medicines.mapper.UserMapper;
import by.accounting.medicines.model.dto.request.user.PatchUserRequest;
import by.accounting.medicines.model.dto.response.UserResponse;
import by.accounting.medicines.model.entity.User;
import by.accounting.medicines.repository.UserRepository;
import by.accounting.medicines.util.ErrorResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    /**
     * Change password of current user
     *
     * @param req
     */
    public void changePassword(PatchUserRequest req) {
        User user = getUserFromJwt();
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        userRepository.save(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow();
    }

    /**
     * Returns User from jwt authentication
     *
     * @return User object
     */
    public User getUserFromJwt() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null) {
            return null;
        }
        String username = authentication.getName();
        return userRepository.findByUsername(username).orElseThrow(ItemNotFoundException::new);
    }

    public User findByIdOrThrow(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Item not found with id " + id, ErrorResponseUtil.USER_NOT_FOUND));
    }

    public UserResponse getUserResponse() {
        return userMapper.userToUserResponse(getUserFromJwt());
    }
}
