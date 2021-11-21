package com.odde.atddv2.page;

import com.odde.atddv2.WinForm;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class WelcomePage {
    @Autowired
    WinForm winForm;

    @SneakyThrows
    public void open() {
        TimeUnit.SECONDS.sleep(6);
        winForm.newPage();
    }

    public void loginUserShouldBe(String userName) {
        winForm.shouldHaveText("Welcome " + userName);
    }
}
