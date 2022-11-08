package com.refinement;

import com.google.common.collect.Lists;
import com.refinement.dto.CellData;
import com.refinement.dto.ClientDTO;
import com.refinement.dto.DataDTO;
import com.refinement.utils.FileType;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.FileSystemException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Stream;

import static com.refinement.utils.FileType.CSV;
import static com.refinement.utils.StringValidator.*;

public class FileReader {
    private static final Logger log = Logger.getLogger(FileReader.class.getName());

    private List<DataDTO> dataFromFile;
    private final String path;
    private static final int NUMBER_OF_SHEET = 0;
    private static final int STARTING_ROW = 1;
    private static final String CSV_SEPARATOR = ";";

    public FileReader(String path) {
        this.path = path;
    }

    public List<DataDTO> getDataFromFile() {
        return dataFromFile;
    }

    public void parseFile() throws IOException {
        log.info("Parsing started...");
        FileInputStream file = new FileInputStream(path);
        List<DataDTO> dataToSave = new ArrayList<>();
        if (path.contains(FileType.XLSX.getType())) {
            Workbook workbook = new XSSFWorkbook(file);
            dataToSave.addAll(readSheet(workbook));
        } else if (path.contains(CSV.getType())) {
            dataToSave.addAll(readAndSaveDataFromCSV(path));
        } else {
            throw new FileSystemException("This file type is not supported");
        }
        this.dataFromFile = dataToSave;
        log.info("Parsing finished...");
    }

    private List<DataDTO> readAndSaveDataFromCSV(String fileName) {
        List<DataDTO> dataDTOS = new ArrayList<>();

        try (Stream<String> lines = Files.lines(Path.of(fileName)).skip(STARTING_ROW)) {
            lines.forEach(line -> readRowFromCSV(dataDTOS, line));
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return dataDTOS;
    }

    private void readRowFromCSV(List<DataDTO> dataDTOS, String line) {
        String cellClientName = NULL_STRING;
        String cellCode1 = NULL_STRING;
        String cellCode2 = NULL_STRING;
        String cellCellData = NULL_STRING;
        String[] attributes = line.split(CSV_SEPARATOR);
        if (attributes.length == 1) {
            cellClientName = attributes[CellData.CLIENT_NAME.getId()];
        }
        if (attributes.length == 2) {
            cellCode1 = attributes[CellData.CODE_1.getId()];
        }
        if (attributes.length == 3) {
            cellCode2 = attributes[CellData.CODE_2.getId()];
        }
        if (attributes.length == 4) {
            cellCellData = attributes[CellData.CELL_DATA.getId()];
        }
        Optional<DataDTO> dataDTO = setupData(cellClientName, cellCode1, cellCode2, cellCellData);
        dataDTO.ifPresent(dataDTOS::add);
    }

    private List<DataDTO> readSheet(Workbook workbook) {
        Sheet sheet = workbook.getSheetAt(NUMBER_OF_SHEET);
        List<DataDTO> dtoList = new ArrayList<>();
        for (Row row : sheet) {
            if (row.getRowNum() >= STARTING_ROW) {
                Optional<DataDTO> dataDTO = prepareDataToSave(row);
                dataDTO.ifPresent(dtoList::add);
            }
        }
        return dtoList;
    }

    private Optional<DataDTO> prepareDataToSave(Row row) {
        String cellClientName = getAndNullifyCell(row, CellData.CLIENT_NAME);
        String cellCode1 = getAndNullifyCell(row, CellData.CODE_1);
        String cellCode2 = getAndNullifyCell(row, CellData.CODE_2);
        String cellCellData = getAndNullifyCell(row, CellData.CELL_DATA);

        return setupData(cellClientName, cellCode1, cellCode2, cellCellData);
    }

    private Optional<DataDTO> setupData(String cellClientName, String cellCode1, String cellCode2, String cellCellData) {
        if (codesAreNotNullAndEmpty(cellCode1, cellCode2)) {
            return Optional.empty();
        }

        DataDTO dataDTO = DataDTO.builder()
                .someData(cellCellData)
                .code1(cellCode1)
                .code2(cellCode2)
                .build();
        if (!cellClientName.equals(EMPTY_STRING)) {
            ClientDTO clientDTO = ClientDTO.builder()
                    .name(cellClientName)
                    .dataDTOList(Lists.newArrayList(dataDTO))
                    .build();
            dataDTO.setClientDTO(clientDTO);
        }
        return Optional.ofNullable(dataDTO);
    }

    private String getAndNullifyCell(Row context, CellData cellData) {
        Cell cell = context.getCell(cellData.ordinal());
        return String.valueOf(cell).replace(NULL_STRING, EMPTY_STRING);
    }
}
