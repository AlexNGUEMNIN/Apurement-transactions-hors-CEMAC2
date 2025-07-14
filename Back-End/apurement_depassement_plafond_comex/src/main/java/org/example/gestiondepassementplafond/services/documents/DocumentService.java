package org.example.gestiondepassementplafond.services.documents;

import org.example.gestiondepassementplafond.record.RowDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

public interface DocumentService {

    public Mono<Boolean> importDocument();
    public Mono<Void> saveDocument();
    public Flux<Map<String, Object>> readExcel();

    Flux<Map<String, Object>> readExcelFileParallel(int chunkSize);
}
