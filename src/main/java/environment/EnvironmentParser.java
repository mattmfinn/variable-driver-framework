package environment;

import capabilities.WebCapabilities;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.openqa.selenium.InvalidArgumentException;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class EnvironmentParser
{
    public WebCapabilities[] parseEnvironments(String jsonEnvironments)
    {
        try
        {
            FileReader fileReader = new FileReader(jsonEnvironments);
            ObjectMapper objectMapper = new ObjectMapper();
            WebCapabilities[] capabilities = objectMapper.readValue(fileReader, WebCapabilities[].class);
            return capabilities;
        }
        catch (IOException e)
        {
            // TODO Implement logback
            System.out.println(e.getMessage());
        }
        throw new InvalidArgumentException("Please check the contents of the JSON file containing the environment" +
                    "information for errors.");
    }
}
