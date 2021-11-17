package com.odde.atddv2;

import io.appium.java_client.windows.WindowsDriver;
import io.cucumber.java.en.Given;
import lombok.SneakyThrows;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WinFormSteps {

    private static WindowsDriver calculatorSession = null;
    private static WebElement calculatorResult = null;

    @SneakyThrows
    @Given("test win app driver")
    public void test_win_app_driver() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("app", "Microsoft.WindowsCalculator_8wekyb3d8bbwe!App");
        calculatorSession = new WindowsDriver(new URL("http://192.168.1.3:4723"), capabilities);
        calculatorSession.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

        calculatorResult = calculatorSession.findElementByAccessibilityId("CalculatorResults");


        calculatorSession.findElementByName("Clear").click();

        // trim extra text and whitespace off of the display value
        assertEquals("0", calculatorResult.getText().replace("Display is", "").trim());


        if (calculatorSession != null) {
            calculatorSession.quit();
        }
        calculatorSession = null;
    }
}
