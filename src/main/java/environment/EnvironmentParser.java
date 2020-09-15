package environment;

import capabilities.WebCapabilities;
import com.fasterxml.jackson.databind.ObjectMapper;
import data.Options;
import org.openqa.selenium.InvalidArgumentException;

import java.io.FileReader;
import java.io.IOException;

public class EnvironmentParser
{
    public Options options;
    private static final String optionsLocation = "./src/test/resources/options.json";

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

    public void setOptions()
    {
        try
        {
            FileReader fileReader = new FileReader(optionsLocation);
            ObjectMapper objectMapper = new ObjectMapper();
            options = objectMapper.readValue(fileReader, Options.class);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}
