package convenientQueue.logic;

import convenientQueue.logic.exception.InvalidLoginRequestException;
import convenientQueue.logic.model.LoginRequest;
import convenientQueue.logic.repository.IUserRepository;

public class LoginService {
    private IUserRepository userRepository;

    public LoginService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void Login(LoginRequest loginRequest) throws InvalidLoginRequestException{
        int userId = userRepository.GetUserId(loginRequest);
        if(userId == -1)
            throw new InvalidLoginRequestException();
        Session.USER_ID = userId;
    }
}
