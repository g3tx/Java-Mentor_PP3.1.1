package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.model.Role;
import web.model.User;
import web.repository.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public boolean saveUser(User newUser) {
        User userFromDB = userRepository.getUserByUsername(newUser.getUsername());
        if (userFromDB != null) {
            return false;
        }
        User userForSave = new User();
        userForSave.setPassword(passwordEncoder.encode(newUser.getPassword()));
        userForSave.setRoles(newUser.getRoles());
        userRepository.save(newUser);
        return true;
    }

    @Override
    public boolean editUser(User UserForEdit) {
        User userFromDB = userRepository.getUserByUsername(UserForEdit.getUsername());
        if (userFromDB == null) {
            return false;
        }
        User editedUser = userRepository.getUserByUsername(UserForEdit.getUsername());
        editedUser.setUserId(UserForEdit.getUserId());
        editedUser.setUsername(UserForEdit.getUsername());
        editedUser.setFirstname(UserForEdit.getFirstname());
        editedUser.setLastname(UserForEdit.getLastname());
        editedUser.setAge(UserForEdit.getAge());
        editedUser.setEmail(UserForEdit.getEmail());
        editedUser.setPassword(passwordEncoder.encode(UserForEdit.getPassword()));
        editedUser.setRoles(UserForEdit.getRoles());
        userRepository.saveAndFlush(editedUser);
        return true;
    }

    @Override
    public Set<Role> ListOfRolesToSet(List<String> rolesId) {
        Set<Role> roleSet = new HashSet<>();
        for (String id : rolesId) {
            roleSet.add(roleService.getRoleById(Long.parseLong(id)));
        }
        return roleSet;
    }

    @Override
    public boolean deleteUser(Long userId) {
        if (userRepository.findById(userId).isPresent()) {
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }

    @Override
    public User getUserById(Long userId) {
        Optional<User> userFromDb = userRepository.findById(userId);
        return userFromDb.orElse(new User());
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.getUserByUsername(username);
        if (user == null) throw new UsernameNotFoundException(username);
        return user;
    }
}
