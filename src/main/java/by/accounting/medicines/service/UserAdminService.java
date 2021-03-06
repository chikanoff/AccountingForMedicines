package by.accounting.medicines.service;

import by.accounting.medicines.exception.DataExistException;
import by.accounting.medicines.exception.ItemNotFoundException;
import by.accounting.medicines.mapper.UserMapper;
import by.accounting.medicines.model.dto.request.user.CreateUserRequest;
import by.accounting.medicines.model.dto.request.user.PatchUserRequest;
import by.accounting.medicines.model.dto.request.user.UpdateUserRequest;
import by.accounting.medicines.model.dto.response.UserResponse;
import by.accounting.medicines.model.entity.User;
import by.accounting.medicines.model.entity.UserRole;
import by.accounting.medicines.repository.UserRepository;
import by.accounting.medicines.util.ErrorResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserAdminService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    /**
     * Creates user in database
     *
     * @param req CreateUserRequest object
     */
    public void createUser(CreateUserRequest req) {
        checkUsernameNotExist(req.getUsername());
        checkEmailNotExist(req.getEmail());
        User user = userMapper.createUserRequestToUser(req);
        user.setRole(UserRole.valueOf(req.getRole()));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    /**
     * Finds a user with the given id and update its fields
     *
     * @param id  user id
     * @param req UpdateUserRequest object
     */
    public void update(Long id, UpdateUserRequest req) {
        User user = findUserOrThrow(id);
        userMapper.updateUserWithUpdateUserRequest(req, user);
        userRepository.save(user);
    }

    /**
     * Finds a user with the given id and change its password
     *
     * @param id  user id
     * @param req PatchUserRequest
     */
    public void changePassword(Long id, PatchUserRequest req) {
        User user = findUserOrThrow(id);
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        userRepository.save(user);
    }

    /**
     * Returns User with the given id
     *
     * @param id
     * @return User
     */
    public UserResponse getById(Long id) {
        return userMapper.userToUserResponse(findUserOrThrow(id));
    }

    /**
     * Deletes User with the given id
     *
     * @param id user id
     */
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    /**
     * Returns page of User meeting the paging restriction provided in the Pageable object.
     *
     * @param pageable Pageable object with page, size and sort params
     * @return a page of User entities
     */
    public Page<UserResponse> getPage(Pageable pageable) {
        List<UserResponse> users = userRepository.findAll().stream().map(userMapper::userToUserResponse).toList();
        return new PageImpl<>(users, pageable, users.size());
    }

    /**
     * Checks if a user exists with the specified email
     *
     * @param email String
     * @throws DataExistException if user with specified email already exist
     */
    private void checkEmailNotExist(String email) throws DataExistException {
        if (userRepository.existsByEmail(email)) {
            throw new DataExistException("User with this email already exist", ErrorResponseUtil.USER_WITH_EMAIL_EXIST);
        }
    }

    /**
     * Checks if a user exists with the specified username
     *
     * @param username String
     * @throws DataExistException if user with specified username already exist
     */
    private void checkUsernameNotExist(String username) throws DataExistException {
        if (userRepository.existsByUsername(username)) {
            throw new DataExistException("User with this username already exist", ErrorResponseUtil.USER_WITH_USERNAME_EXIST);
        }
    }

    /**
     * Returns user by id
     *
     * @param id user id
     * @return User object
     */
    private User findUserOrThrow(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Item not found with id " + id, ErrorResponseUtil.USER_NOT_FOUND));
    }

    public List<UserResponse> getAll() {
        return userRepository.findAll().stream().map(userMapper::userToUserResponse).toList();
    }
}
