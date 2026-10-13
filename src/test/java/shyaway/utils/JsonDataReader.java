package shyaway.utils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.charset.StandardCharsets;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


public class JsonDataReader {

    public List<HashMap<String, String>> getJsonDataToMap(
            String filePath) throws IOException {

        // Read JSON file into String
    	String jsonContent = FileUtils.readFileToString(
    		    new File(filePath),
    		    StandardCharsets.UTF_8
    		);

        // Convert JSON String into List of HashMaps
        ObjectMapper mapper = new ObjectMapper();

        List<HashMap<String, String>> data =
            mapper.readValue(
                jsonContent,
                new TypeReference<List<HashMap<String, String>>>() {}
            );

        return data;
    }
}