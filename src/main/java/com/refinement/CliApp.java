package com.refinement;

import com.google.common.collect.Lists;
import com.refinement.dto.CellData;
import com.refinement.dto.ClientDTO;
import com.refinement.dto.DataDTO;
import com.refinement.persistence.PersistenceService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@SpringBootApplication
public class CliApp implements CommandLineRunner {
    private static final Logger log = Logger.getLogger(CliApp.class.getName());
    private static final String PATH = "C:\\JavaProjects\\RefinementTask\\target\\classes\\sheet.xlsx";
    private static final int NUMBER_OF_SHEET = 0;
    private static final int STARTING_ROW = 1;

    private final PersistenceService persistenceService;

    @Autowired
    public CliApp(PersistenceService persistenceService) {
        this.persistenceService = persistenceService;
    }

    public static void main(String[] args) {
        log.info("STARTING THE APPLICATION");
        SpringApplication.run(CliApp.class, args);
        log.info("APPLICATION FINISHED");
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("EXECUTING : command line runner");
        FileInputStream file = new FileInputStream(PATH);
        Workbook workbook = new XSSFWorkbook(file);
        readSheet(workbook);
    }

    private void readSheet(Workbook workbook) {
        Sheet sheet = workbook.getSheetAt(NUMBER_OF_SHEET);

        Map<Integer, ClientDTO> data = new HashMap<>();
        int i = 0;
        for (Row row : sheet) {
            if (row.getRowNum() >= STARTING_ROW) {
                Cell cellClientName = row.getCell(CellData.CLIENT_NAME.ordinal());
                Cell cellCode1 = row.getCell(CellData.CODE_1.ordinal());
                Cell cellCode2 = row.getCell(CellData.CODE_2.ordinal());
                Cell cellCellData = row.getCell(CellData.CELL_DATA.ordinal());

                DataDTO dataDTO = DataDTO.builder()
                        .someData(String.valueOf(cellCellData))
                        .code1(String.valueOf(cellCode1))
                        .code2(String.valueOf(cellCode2))
                        .build();

                ClientDTO clientDTO = ClientDTO.builder()
                        .name(String.valueOf(cellClientName))
                        .dataDTOList(Lists.newArrayList(dataDTO))
                        .build();
                dataDTO.setClientDTO(clientDTO);
                persistenceService.save(dataDTO);
                i++;
            }
        }
        System.out.println(data.size());
    }


}
