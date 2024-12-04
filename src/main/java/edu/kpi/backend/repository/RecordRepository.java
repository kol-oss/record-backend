package edu.kpi.backend.repository;

import edu.kpi.backend.entity.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface RecordRepository extends JpaRepository<Record, UUID>, JpaSpecificationExecutor<Record> {}
