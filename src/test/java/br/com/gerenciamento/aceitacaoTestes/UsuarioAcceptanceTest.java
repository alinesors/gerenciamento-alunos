package br.com.gerenciamento.aceitacaoTestes;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import io.github.bonigarcia.wdm.WebDriverManager;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UsuarioAcceptanceTest {

    @LocalServerPort
    private int port;

    private WebDriver driver;

    private String baseUrl;

    @BeforeAll
    static void setupWebDriverManager() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void setup() {
        driver = new ChromeDriver();
        baseUrl = "http://localhost:" + port;
        driver.manage().window().maximize();
    }

    @Test
    public void testCadastroDeUsuarioComSucesso() { 
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        driver.get(baseUrl + "/cadastro");

        WebElement userInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user"))); 
        userInput.sendKeys("Aline");

        WebElement emailInput = driver.findElement(By.id("email")); 
        emailInput.sendKeys("Aline@gmail.com");

        WebElement senhaInput = driver.findElement(By.id("senha")); 
        senhaInput.sendKeys("123456"); 


        WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']")));
        submitButton.click();

        
        wait.until(ExpectedConditions.urlToBe(baseUrl + "/")); 
        assertTrue(driver.getCurrentUrl().equals(baseUrl + "/"));

        driver.quit();
    }
}