package com.refood.api.repository;

import com.refood.api.entity.Doador;
import com.refood.api.entity.Ong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OngRepository extends JpaRepository<Ong, Long> {}
