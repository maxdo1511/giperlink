package ru.espada.giperlink.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity getUserById(long id, String language) throws UserException {
        return userRepository.findById(id).orElseThrow(() -> new UserException("exception.user.not_found", language));
    }

    public List<UserEntity> getUsers(String lang, int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return userRepository.findAll(pageRequest).toList();
    }
}
