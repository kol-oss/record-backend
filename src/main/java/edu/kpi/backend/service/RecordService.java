package edu.kpi.backend.service;

import edu.kpi.backend.dto.CreateRecordDTO;
import edu.kpi.backend.entity.Account;
import edu.kpi.backend.entity.Category;
import edu.kpi.backend.entity.Record;
import edu.kpi.backend.entity.User;
import edu.kpi.backend.repository.CategoryRepository;
import edu.kpi.backend.repository.RecordRepository;
import edu.kpi.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
        if (userId != null && this.userRepository.getById(userId).isEmpty()) {
            return Optional.empty();
        } else if (categoryId != null && this.categoryRepository.getById(categoryId).isEmpty()) {
            return Optional.empty();
        }

        return Optional.ofNullable(this.recordRepository.getAll(userId, categoryId));
    }

    public Optional<Record> getRecordById(UUID id) {
        return this.recordRepository.getById(id);
    }

    public Optional<Record> createRecord(CreateRecordDTO createRecordDTO) {
        Optional<User> user = this.userRepository.getById(createRecordDTO.getUserId());
        Optional<Category> category = this.categoryRepository.getById(createRecordDTO.getCategoryId());

        if (user.isEmpty() || category.isEmpty()) {
            return Optional.empty();
        }

        int amount = createRecordDTO.getAmount();
        Account account = user.get().getAccount();

        if (createRecordDTO.getAmount() > 0) {
            account.add(amount);
        } else {
            account.remove(Math.abs(amount));
        }

        return Optional.ofNullable(this.recordRepository.create(
                createRecordDTO.getUserId(),
                createRecordDTO.getCategoryId(),
                createRecordDTO.getAmount()
        ));
    }

    public Optional<Record> deleteRecordById(UUID id) {
        return this.recordRepository.deleteById(id);
    }
}
