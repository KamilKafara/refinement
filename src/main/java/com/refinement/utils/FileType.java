package com.refinement.utils;

public enum FileType {
    CSV("csv"),
    XLSX("xlsx");

    private final String type;

    FileType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
