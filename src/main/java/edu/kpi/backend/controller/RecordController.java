package edu.kpi.backend.controller;

import edu.kpi.backend.dto.CreateRecordDTO;
import edu.kpi.backend.entity.Record;
import edu.kpi.backend.service.RecordService;
import jakarta.validation.Valid;
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
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User or category id must be specified for get all request");
        }

        Optional<List<Record>> filtered = this.recordService.getAllRecords(userId.orElse(null), categoryId.orElse(null));

        if (filtered.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User or category with such id is not found");
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
    public ResponseEntity<Record> createRecord(@Valid @RequestBody CreateRecordDTO createRecordDTO) {
        Optional<Record> created = this.recordService.createRecord(createRecordDTO);

        if (created.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User or category with such id is not found");
        }

        return ResponseEntity.ok(created.get());
    }

    @DeleteMapping("{record_id}")
    public ResponseEntity<Record> deleteRecord(@PathVariable("record_id") UUID recordId) {
        Optional<Record> deleted = this.recordService.deleteRecordById(recordId);

        return deleted
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

}
