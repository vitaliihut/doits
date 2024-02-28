package ua.insultape.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.insultape.store.entity.ProcessedAgentDataDb;

@Repository
public interface ProcessedAgentDataRepository extends JpaRepository<ProcessedAgentDataDb, Integer> {
}
