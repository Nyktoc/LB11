package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import java.time.Duration;

public class SimpleLoginTest {
    
    private WebDriver driver;      // Переменная для управления браузером
    private WebDriverWait wait;    // Переменная для ожиданий
    
    // Этот метод выполняется ПЕРЕД каждым тестом
    @BeforeMethod
    public void setUp() {
        System.out.println("Запускаем браузер...");
        
        // 1. Автоматически настраиваем драйвер Chrome
        WebDriverManager.chromedriver().setup();
        
        // 2. Создаем экземпляр Chrome браузера
        driver = new ChromeDriver();
        
        // 3. Разворачиваем окно на весь экран
        driver.manage().window().maximize();
        
        // 4. Создаем ожидание (10 секунд максимум)
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
        System.out.println("Браузер готов!");
    }
    
    // ТЕСТ 1: Успешный логин
    @Test
    public void testSuccessfulLogin() {
        System.out.println("Запускаем тест успешного логина...");
        
        // Шаг 1: Открываем страницу логина
        driver.get("https://the-internet.herokuapp.com/login");
        System.out.println("Открыли страницу логина");
        
        // Шаг 2: Находим поле "Username" и вводим логин
        WebElement usernameField = driver.findElement(By.id("username"));
        usernameField.sendKeys("tomsmith");
        System.out.println("Ввели логин: tomsmith");
        
        // Шаг 3: Находим поле "Password" и вводим пароль
        WebElement passwordField = driver.findElement(By.id("password"));
        passwordField.sendKeys("SuperSecretPassword!");
        System.out.println("Ввели пароль");
        
        // Шаг 4: Находим кнопку "Login" и нажимаем
        WebElement loginButton = driver.findElement(By.cssSelector("button.radius"));
        loginButton.click();
        System.out.println("Нажали кнопку Login");
        
        // Шаг 5: Ждем появления сообщения об успехе (максимум 10 секунд)
        WebElement successMessage = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.id("flash"))
        );
        
        // Шаг 6: Получаем текст сообщения
        String messageText = successMessage.getText();
        System.out.println("Получили сообщение: " + messageText);
        
        // Шаг 7: ПРОВЕРКА - должно содержать текст об успехе
        Assert.assertTrue(messageText.contains("You logged into a secure area!"),
            "ОШИБКА: Сообщение об успешном входе не появилось! Текст был: " + messageText);
        
        System.out.println("✓ Тест успешного логина пройден!");
    }
    
    // ТЕСТ 2: Неуспешный логин (неверный пароль)
    @Test
    public void testFailedLogin_WrongPassword() {
        System.out.println("Запускаем тест с неверным паролем...");
        
        // Шаг 1: Открываем страницу
        driver.get("https://the-internet.herokuapp.com/login");
        
        // Шаг 2: Вводим правильный логин, но НЕПРАВИЛЬНЫЙ пароль
        driver.findElement(By.id("username")).sendKeys("tomsmith");
        driver.findElement(By.id("password")).sendKeys("wrong_password");
        driver.findElement(By.cssSelector("button.radius")).click();
        
        // Шаг 3: Ждем сообщение об ошибке
        WebElement errorMessage = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.id("flash"))
        );
        
        String messageText = errorMessage.getText();
        System.out.println("Получили сообщение: " + messageText);
        
        // Шаг 4: ПРОВЕРКА - должно содержать текст об ошибке
        Assert.assertTrue(messageText.contains("Your password is invalid!"),
            "ОШИБКА: Не появилось сообщение о неверном пароле! Текст был: " + messageText);
        
        System.out.println("✓ Тест с неверным паролем пройден!");
    }
    
    // Этот метод выполняется ПОСЛЕ каждого теста
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("Закрываем браузер...");
        
        // Закрываем браузер и освобождаем ресурсы
        if (driver != null) {
            driver.quit();
        }
        
        System.out.println("Браузер закрыт!\n");
    }
}