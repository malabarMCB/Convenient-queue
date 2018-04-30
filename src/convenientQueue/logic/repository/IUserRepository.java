package convenientQueue.logic.repository;

import convenientQueue.logic.model.LoginRequest;

public interface IUserRepository {
    int GetUserId(LoginRequest loginRequest);
}
