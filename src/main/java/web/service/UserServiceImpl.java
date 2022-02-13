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
    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public void editUser(User user) {
        User userForEdit = userRepository.getUserById(user.getUserId());
        userForEdit.setUserId(user.getUserId());
        userForEdit.setFirstname(user.getFirstname());
        userForEdit.setLastname(user.getLastname());
        userForEdit.setAge(user.getAge());
        userForEdit.setEmail(user.getEmail());
        userForEdit.setPassword(passwordEncoder.encode(user.getPassword()));
        userForEdit.setRoles(user.getRoles());
        userRepository.saveAndFlush(user);
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
        return userRepository.getUserById(userId);
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
