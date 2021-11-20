package com.odde.atddv2;

import io.appium.java_client.windows.WindowsDriver;
import lombok.SneakyThrows;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.net.URL;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.openqa.selenium.By.xpath;

@Component
public class Browser {

    private WebDriver webDriver = null;

    @Value("${host.name:host.docker.internal}")
    private String hostName;

    @Value("${host.port:}")
    private String hostPort;

    public void launchByUrl(String path) {
        getWebDriver().get(String.format("http://%s:%d%s", hostName, getPort(), path));
    }

    public void inputTextByPlaceholder(String placeholder, String text) {
        waitElement("//*[@placeholder='" + placeholder + "']").sendKeys(text);
    }

    public void clickByText(String text) {
        waitElement(String.format("//*[normalize-space(@value)='%s' or normalize-space(text())='%s']", text, text)).click();
    }

    public void shouldHaveText(String text) {
        await().untilAsserted(() -> assertThat(getWebDriver().findElements(xpath("//*[text()='" + text + "']"))).isNotEmpty());
    }

    @PreDestroy
    public void close() {
        if (webDriver != null) {
            webDriver.quit();
            webDriver = null;
        }
    }

    @SneakyThrows
    public WebDriver createWebDriver() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("app", "C:\\Users\\Joseph\\code\\shop_app\\build\\windows\\runner\\Debug\\shop_app.exe");
        return new WindowsDriver(new URL("http://127.0.0.1:4723"), capabilities);
//        return new RemoteWebDriver(new URL("http://web-driver.tool.net:4444"), DesiredCapabilities.chrome());
    }

    public void selectTextByPlaceholder(String placeholder, String text) {
        waitElement(String.format("//*[normalize-space(@placeholder)='%s']", placeholder)).click();
        clickByText(text);
    }

    public void shouldNotHaveText(String text) {
        await().untilAsserted(() -> assertThat(getWebDriver().findElements(xpath("//*[text()='" + text + "']"))).isEmpty());
    }

    public WebDriver getWebDriver() {
        if (webDriver == null)
            webDriver = createWebDriver();
        return webDriver;
    }

    private Integer getPort() {
        return !hostPort.isEmpty() ? Integer.valueOf(hostPort) : 10081;
    }

    private WebElement waitElement(String xpathExpression) {
        return await().until(() -> getWebDriver().findElement(xpath(xpathExpression)), Objects::nonNull);
    }
}
