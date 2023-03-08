package org.portfolio.sample.repository;

import java.util.Optional;

import org.portfolio.sample.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {

	@Query("SELECT u FROM User u WHERE u.email = :email")
	Optional<User> getByEmail(@Param("email") String email);
	
	@Query("SELECT u FROM User u WHERE u.id = :id")
	Optional<User> getByPK(@Param("id") Long id);
	
	@Query("SELECT u FROM User u WHERE u.nickname = :nickname")
	Optional<User> checkByNickname(@Param("nickname") String nickname);
	
}
