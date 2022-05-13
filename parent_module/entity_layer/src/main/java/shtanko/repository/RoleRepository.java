package shtanko.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shtanko.entity.user.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
