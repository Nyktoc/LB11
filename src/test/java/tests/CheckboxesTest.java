package tests;

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
        
        // 1. Открываем страницу
        driver.get("https://the-internet.herokuapp.com/checkboxes");
        
        // 2. Находим все чекбоксы
        List<WebElement> checkboxes = driver.findElements(By.cssSelector("input[type='checkbox']"));
        
        // 3. Проверяем начальное состояние
        System.out.println("Первый чекбокс выбран: " + checkboxes.get(0).isSelected());
        System.out.println("Второй чекбокс выбран: " + checkboxes.get(1).isSelected());
        
        // 4. Кликаем на первый чекбокс (если не выбран)
        if (!checkboxes.get(0).isSelected()) {
            checkboxes.get(0).click();
            System.out.println("Кликнули на первый чекбокс");
        }
        
        // 5. Снимаем выбор со второго чекбокса (если выбран)
        if (checkboxes.get(1).isSelected()) {
            checkboxes.get(1).click();
            System.out.println("Сняли выбор со второго чекбокса");
        }
        
        // 6. Проверяем конечное состояние
        Assert.assertTrue(checkboxes.get(0).isSelected(), 
            "Первый чекбокс должен быть выбран");
        Assert.assertFalse(checkboxes.get(1).isSelected(),
            "Второй чекбокс НЕ должен быть выбран");
        
        System.out.println("✓ Тест чекбоксов пройден!");
    }
    
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}