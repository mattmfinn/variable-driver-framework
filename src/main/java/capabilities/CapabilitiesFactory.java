package capabilities;

import environment.EnvironmentParser;
import jdk.nashorn.internal.parser.JSONParser;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class CapabilitiesFactory
{
    private EnvironmentParser environmentParser;

    public CapabilitiesFactory(EnvironmentParser environmentParser)
    {
        this.environmentParser = environmentParser;
    }

    public DesiredCapabilities[] makeCapabilities(String jsonEnvironments)
    {
        try
        {
            WebCapabilities[] rawCapabilities = environmentParser.parseEnvironments(jsonEnvironments);
            DesiredCapabilities finalCapabilities[] = new DesiredCapabilities[rawCapabilities.length];

            int index = 0;
            for (WebCapabilities m : rawCapabilities)
            {
                DesiredCapabilities capabilities = new DesiredCapabilities();
                for (Field field : m.getClass().getDeclaredFields())
                {
                    capabilities.setCapability(field.getName(), field.get(m));
                }
                finalCapabilities[index] = capabilities;
                index++;
            }
            return finalCapabilities;
        } catch (Exception e)
        {
            // TODO: Implement Logback
            System.out.println(e.getMessage());
        }
        throw new Error("Unable to create array of capabilities for test run");
    }
}
