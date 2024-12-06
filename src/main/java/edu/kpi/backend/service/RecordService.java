package edu.kpi.backend.service;

import edu.kpi.backend.dto.CreateRecordDTO;
import edu.kpi.backend.entity.Account;
import edu.kpi.backend.entity.Category;
import edu.kpi.backend.entity.Record;
import edu.kpi.backend.entity.User;
import edu.kpi.backend.repository.CategoryRepository;
import edu.kpi.backend.repository.RecordRepository;
import edu.kpi.backend.repository.UserRepository;
import edu.kpi.backend.service.specifications.RecordSpecificationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RecordService {
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final RecordRepository recordRepository;

    @Autowired
    public RecordService(UserRepository userRepository, CategoryRepository categoryRepository, RecordRepository recordRepository) {
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.recordRepository = recordRepository;
    }

    public Optional<List<Record>> getAllRecords(UUID userId, UUID categoryId) {
        if (userId != null && this.userRepository.findById(userId).isEmpty()) {
            return Optional.empty();
        } else if (categoryId != null && this.categoryRepository.findById(categoryId).isEmpty()) {
            return Optional.empty();
        }

        Specification<Record> filter = Specification.where(null);

        if (userId != null) {
            filter = filter.and(RecordSpecificationBuilder.hasUser(userId));
        }

        if (categoryId != null) {
            filter.and(RecordSpecificationBuilder.hasCategory(categoryId));
        }

        return Optional.of(this.recordRepository.findAll(filter));
    }

    public Optional<Record> getRecordById(UUID id) {
        return this.recordRepository.findById(id);
    }

    public Optional<Record> createRecord(CreateRecordDTO createRecordDTO) {
        Optional<User> user = this.userRepository.findById(createRecordDTO.getUserId());
        Optional<Category> category = this.categoryRepository.findById(createRecordDTO.getCategoryId());

        if (user.isEmpty() || category.isEmpty()) {
            return Optional.empty();
        }

        int amount = createRecordDTO.getAmount();
        Account account = user.get().getAccount();

        if (amount > 0) {
            account.add(amount);
        } else {
            account.remove(Math.abs(amount));
        }

        Record record = new Record();
        record.setUser(user.get());
        record.setCategory(category.get());
        record.setAmount(amount);

        return Optional.of(this.recordRepository.save(record));
    }

    public Optional<Record> deleteRecordById(UUID id) {
        Optional<Record> record = this.recordRepository.findById(id);

        this.recordRepository.deleteById(id);

        return record;
    }
}
