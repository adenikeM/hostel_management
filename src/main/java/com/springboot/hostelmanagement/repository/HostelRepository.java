package com.springboot.hostelmanagement.repository;

import com.springboot.hostelmanagement.model.Hostel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HostelRepository extends JpaRepository<Hostel, Integer> {
}
