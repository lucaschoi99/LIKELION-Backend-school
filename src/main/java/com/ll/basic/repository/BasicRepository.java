package com.ll.basic.repository;

import com.ll.basic.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BasicRepository extends JpaRepository<Person, Long> {
}
