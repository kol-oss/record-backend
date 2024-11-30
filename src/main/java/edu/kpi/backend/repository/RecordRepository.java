package edu.kpi.backend.repository;

import edu.kpi.backend.entity.Record;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class RecordRepository {
    private final Set<Record> records;

    public RecordRepository() {
        this.records = new HashSet<>();
    }

    public List<Record> getAll() {
        return this.records.stream().toList();
    }

    public Optional<Record> getById(UUID id) {
        return this.records.stream()
                .filter(record -> record.getId().equals(id))
                .findFirst();
    }

    public Record create(UUID userId, UUID categoryId, int amount) {
        Optional<Record> stored = this.records.stream()
                .filter(record -> record.getUserId().equals(userId) && record.getCategoryId().equals(categoryId))
                .findFirst();

        if (stored.isPresent()) return stored.get();

        Record record = new Record(UUID.randomUUID(), userId, categoryId, amount);
        this.records.add(record);

        return record;
    }

    public Optional<Record> deleteById(UUID id) {
        Optional<Record> deleted = this.records.stream()
                .filter(record -> record.getId().equals(id))
                .findFirst();

        deleted.ifPresent(this.records::remove);

        return deleted;
    }

}
