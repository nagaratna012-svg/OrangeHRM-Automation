package utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class JsonDataReader {

    public static EmployeeData getEmployeeData(
            String filePath) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.readValue(
                new File(filePath),
                EmployeeData.class
        );
    }
}