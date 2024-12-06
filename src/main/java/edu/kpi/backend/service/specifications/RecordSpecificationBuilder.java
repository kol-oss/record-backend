package edu.kpi.backend.service.specifications;

import edu.kpi.backend.entity.Record;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public class RecordSpecificationBuilder {
    public static Specification<Record> hasUser(UUID userId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("user").get("id"), userId);
    }

    public static Specification<Record> hasCategory(UUID categoryId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("category").get("id"), categoryId);
    }
}
