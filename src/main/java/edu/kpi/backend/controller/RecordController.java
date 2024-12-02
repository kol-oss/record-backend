package edu.kpi.backend.controller;

import edu.kpi.backend.dto.CreateRecordDTO;
import edu.kpi.backend.entity.Record;
import edu.kpi.backend.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/records/")
public class RecordController {
    private final RecordService recordService;

    @Autowired
    public RecordController(RecordService recordService) {
        this.recordService = recordService;
    }

    @GetMapping
    public ResponseEntity<List<Record>> getAllRecords(
            @RequestParam(required = false, name = "user_id") Optional<UUID> userId,
            @RequestParam(required = false, name = "category_id") Optional<UUID> categoryId
    ) {
        if (userId.isEmpty() && categoryId.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User id or category id is required");
        }

        Optional<List<Record>> filtered = this.recordService.getAllRecords(userId.orElse(null), categoryId.orElse(null));

        if (filtered.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Input user id or category id is invalid");
        }

        return ResponseEntity.ok(filtered.get());
    }

    @GetMapping("{record_id}")
    public ResponseEntity<Record> getRecordById(@PathVariable("record_id") UUID recordId) {
        Optional<Record> record = this.recordService.getRecordById(recordId);

        return record
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping()
    public ResponseEntity<Record> createRecord(@RequestBody CreateRecordDTO createRecordDTO) {
        if (
            createRecordDTO.getUserId() == null ||
            createRecordDTO.getCategoryId() == null ||
            createRecordDTO.getAmount() == 0
        ) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Input params are invalid");
        }

        Optional<Record> created = this.recordService.createRecord(createRecordDTO);

        return created
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @DeleteMapping("{record_id}")
    public ResponseEntity<Record> deleteRecord(@PathVariable("record_id") UUID recordId) {
        Optional<Record> deleted = this.recordService.deleteRecordById(recordId);

        return deleted
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

}
