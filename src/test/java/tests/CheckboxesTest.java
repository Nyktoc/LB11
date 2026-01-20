# Создайте файл с кодом одной командой
echo package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import java.time.Duration;
import java.util.List;

public class CheckboxesTest {
    
    private WebDriver driver;
    private WebDriverWait wait;
    
    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    
    @Test
    public void testCheckboxesSelection() {
        System.out.println("Тестируем страницу с чекбоксами...");
        driver.get("https://the-internet.herokuapp.com/checkboxes");
        
        List^<WebElement^> checkboxes = driver.findElements(By.cssSelector("input[type='checkbox']"));
        
        System.out.println("Первый чекбокс выбран: " + checkboxes.get(0).isSelected());
        System.out.println("Второй чекбокс выбран: " + checkboxes.get(1).isSelected());
        
        if (!checkboxes.get(0).isSelected()) {
            checkboxes.get(0).click();
            System.out.println("Кликнули на первый чекбокс");
        }
        
        if (checkboxes.get(1).isSelected()) {
            checkboxes.get(1).click();
            System.out.println("Сняли выбор со второго чекбокса");
        }
        
        Assert.assertTrue(checkboxes.get(0).isSelected(), 
            "Первый чекбокс должен быть выбран");
        Assert.assertFalse(checkboxes.get(1).isSelected(),
            "Второй чекбокс НЕ должен быть выбран");
        
        System.out.println("Тест чекбоксов пройден!");
    }
    
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
} > src\test\java\tests\CheckboxesTest.java