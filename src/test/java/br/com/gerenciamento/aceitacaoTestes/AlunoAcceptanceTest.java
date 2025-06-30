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
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import io.github.bonigarcia.wdm.WebDriverManager;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AlunoAcceptanceTest {

    private WebDriver driver; 

    @LocalServerPort
    private int port;

    private String baseUrl;

    @BeforeAll 
    static void setupWebDriverManager() {
        WebDriverManager.chromedriver().setup();
    }
 
    @BeforeEach 
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        baseUrl = "http://localhost:" + port;
    }

    @Test 
    public void testCadastroDeAlunoComSucesso() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        driver.get(baseUrl + "/inserirAlunos");

        WebElement nome = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nome"))); 
        nome.sendKeys("Aline");

        WebElement matricula = driver.findElement(By.id("matricula"));
        matricula.sendKeys("123456");

        WebElement cursoSelecao = driver.findElement(By.id("curso"));
        Select curso = new Select(cursoSelecao);
        curso.selectByValue("INFORMATICA");

        WebElement turnoSelecao = driver.findElement(By.id("turno"));
        Select turno = new Select(turnoSelecao);
        turno.selectByValue("NOTURNO");

        WebElement statusSelecao = driver.findElement(By.id("status"));
        Select status = new Select(statusSelecao);
        status.selectByValue("ATIVO");

        WebElement salvarButton = wait.until(ExpectedConditions.elementToBeClickable( By.cssSelector("button.btn.btn-outline-success[type='submit']")));
        salvarButton.click();

       
        wait.until(ExpectedConditions.urlContains("/alunos-adicionados"));
        assertTrue(driver.getCurrentUrl().contains("/alunos-adicionados"));

        
        WebElement pageBody = wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("body")));
        assertTrue(pageBody.getText().contains("Aline"));
        assertTrue(pageBody.getText().contains("123456"));
        assertTrue(pageBody.getText().contains("INFORMATICA"));
        assertTrue(pageBody.getText().contains("NOTURNO"));
        assertTrue(pageBody.getText().contains("ATIVO"));

        driver.quit();
    }
}