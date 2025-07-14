package org.example.gestiondepassementplafond.record;

import java.util.List;

public class RowDto {
    private List<String> cells;

    public RowDto(List<String> cells) {
        this.cells = cells;
    }

    public List<String> getCells() {
        return cells;
    }

    public void setCells(List<String> cells) {
        this.cells = cells;
    }
}
