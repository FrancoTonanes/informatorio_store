package com.informatorio.storeinformatorio.repository;

import com.informatorio.storeinformatorio.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    public List<User> findByCityName(String city);
}
