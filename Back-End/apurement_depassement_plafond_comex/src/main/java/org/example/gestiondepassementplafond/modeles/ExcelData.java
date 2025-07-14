package org.example.gestiondepassementplafond.modeles;

import org.apache.poi.ss.usermodel.Sheet;

import java.util.List;

public class ExcelData {

    public final Sheet sheet;
    public final List<String> headers;
    public final List<List<Integer>> chunks;

    public ExcelData(Sheet sheet, List<String> headers, List<List<Integer>> chunks) {
        this.sheet = sheet;
        this.headers = headers;
        this.chunks = chunks;
    }

}
