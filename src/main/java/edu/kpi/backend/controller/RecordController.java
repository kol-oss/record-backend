package edu.kpi.backend.controller;

import edu.kpi.backend.dto.CreateRecordDTO;
import edu.kpi.backend.dto.GetAllRecordsDTO;
import edu.kpi.backend.entity.Record;
import edu.kpi.backend.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<List<Record>> getAllRecords(@RequestBody GetAllRecordsDTO getAllRecordsDTO) {
        return ResponseEntity.ok(
                this.recordService.getAllRecords(getAllRecordsDTO)
        );
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
            return ResponseEntity.badRequest().build();
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
