package com.odde.atddv2.page;

import com.odde.atddv2.Browser;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class HomePage {

    @Autowired
    public Browser browser;

    public void open() {
        browser.launchByUrl("/");
    }

    @SneakyThrows
    public void login(String userName, String password) {
        System.out.println("browser.getWebDriver().getPageSource() = " + browser.getWebDriver().getPageSource());
        browser.inputTextByPlaceholder("textBox_userName", userName);
        browser.inputTextByPlaceholder("textBox_password", password);
        browser.clickByText("userButton_login");
        TimeUnit.SECONDS.sleep(6);
    }
}
