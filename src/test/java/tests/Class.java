package tests;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;


public abstract class Class {
    public static XMLConfiguration config;
    public static Properties prop = new Properties();
    private static InputStream input = null;
    private static final long PAGE_LOAD_TIMEOUT = 10;
    private static final long IMPLICITLY_WAIT = 10;
    private static WebDriverWait wait;
    @Autowired
    protected static WebDriver driver;

    @BeforeClass
    public static void setUp() throws Exception {
        config = loadFileConfig();
        driver = launchChrome();
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(IMPLICITLY_WAIT, TimeUnit.SECONDS);



    }

    private static XMLConfiguration loadFileConfig() throws IOException, ConfigurationException {

            return new XMLConfiguration(new File("src/main/resources/xpath.xml").getCanonicalFile());

    }

    @AfterClass
    public static void tierDown() throws Exception {
        driver.quit();
    }

    private static WebDriver launchChrome() throws MalformedURLException {
        DesiredCapabilities capability = DesiredCapabilities.chrome();
        capability.setBrowserName("chrome");
        capability.setCapability(CapabilityType.PAGE_LOAD_STRATEGY, "normal");
        URL hostURL = new URL("http://192.168.0.100:5555/wd/hub");
        driver = new RemoteWebDriver(hostURL, capability);
        wait = new WebDriverWait(driver, 10);
        return driver;

    }
}
