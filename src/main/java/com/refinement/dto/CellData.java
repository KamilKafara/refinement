package com.refinement.dto;

public enum CellData {
    CLIENT_NAME(0),
    CODE_1(1),
    CODE_2(2),
    CELL_DATA(3);

    private final int columnId;

    CellData(int columnId) {
        this.columnId = columnId;
    }
}
