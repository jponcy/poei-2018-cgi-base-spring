
package hello.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import hello.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    @Override
    List<User> findAll();
}
