package com.felipemcassiano.Mercatura.repositories;

import com.felipemcassiano.Mercatura.models.user.User;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends ListCrudRepository<User, Long> {
    UserDetails findByEmail(String email);
}
