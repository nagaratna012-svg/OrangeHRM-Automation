package utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ScreenshotUtil {

    public static String captureScreenshot(
            WebDriver driver,
            String screenshotName
    ) {

        try {

            Path screenshotDirectory =
                    Paths.get(
                            System.getProperty("user.dir"),
                            "screenshots"
                    );

            Files.createDirectories(screenshotDirectory);

            File source =
                    ((TakesScreenshot) driver)
                            .getScreenshotAs(OutputType.FILE);

            Path destination =
                    screenshotDirectory.resolve(
                            screenshotName + ".png"
                    );

            Files.copy(
                    source.toPath(),
                    destination,
                    java.nio.file.StandardCopyOption.REPLACE_EXISTING
            );

            return destination.toString();

        } catch (Exception e) {

            System.out.println(
                    "Unable to capture screenshot: "
                            + e.getMessage()
            );

            return null;
        }
    }
}