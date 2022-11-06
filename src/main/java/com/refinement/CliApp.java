package com.refinement;

import com.refinement.dto.DataDTO;
import com.refinement.persistence.DataEntityPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.naming.directory.InvalidAttributesException;
import java.io.File;
import java.util.List;
import java.util.logging.Logger;

@SpringBootApplication
public class CliApp implements CommandLineRunner {
    private static final Logger log = Logger.getLogger(CliApp.class.getName());
    private final DataEntityPersistence dataEntityPersistence;

    @Autowired
    public CliApp(DataEntityPersistence dataEntityPersistence) {
        this.dataEntityPersistence = dataEntityPersistence;
    }

    public static void main(String[] args) {
        log.info("STARTING THE APPLICATION");
        SpringApplication.run(CliApp.class, args);
        log.info("APPLICATION FINISHED");
    }

    @Override
    public void run(String... args) throws Exception {
        File file = getFileFromArgs(args);
        FileReader fileReader = new FileReader(file.getAbsolutePath());
        fileReader.parseFile();
        List<DataDTO> dataToPersist = fileReader.getDataFromFile();
        persistData(dataToPersist);
        log.info("Saved data: " + fileReader.getDataFromFile().size());
        System.exit(0);
    }

    private static File getFileFromArgs(String[] args) throws InvalidAttributesException {
        File file;
        if (0 < args.length) {
            file = new File(args[0]);
        } else {
            throw new InvalidAttributesException("Invalid arguments count:" + args.length);
        }
        return file;
    }

    private void persistData(List<DataDTO> dataToSave) {
        log.info("Persistence started...");
        dataToSave.forEach(dataEntityPersistence::saveOrUpdate);
        log.info("Persistence finished...");
    }
}
