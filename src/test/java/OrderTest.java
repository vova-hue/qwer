import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ru.yandex.praktikum.OrderPage;
import ru.yandex.praktikum.StartPage;

@RunWith(Parameterized.class)
public class OrderTest {
    private static final String DEFAULT_BROWSER_NAME = "FIREFOX";
    private static final String PAGE_URL = "https://qa-scooter.praktikum-services.ru/";
    private final String firstName;
    private final String secondName;
    private final String address;
    private final By station;
    private final String phone;

    private static final By STATION_1 = By.xpath(".//button[@value='1']/div[text()='Бульвар Рокоссовского']");
    private static final By STATION_2 = By.xpath(".//button[@value='2']/div[text()='Черкизовская']");
    private static final By ORDER_DATE = By.xpath(".//div[@aria-label='Choose пятница, 27-е декабря 2024 г.']");
    private static final By RENTAL_PERIOD = By.xpath(".//div[text()='сутки']");
    private static final By SCOOTER_COLOR = By.xpath(".//input[@id='grey']");
    private static final String textOrderCreated = "Заказ оформлен";
    private static final String BROWSER_NAME_ENV_VARIABLE = "BROWSER_NAME";
    private WebDriver driver;

    public OrderTest(String firstName, String secondName, String address, By station, String phone) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.address = address;
        this.station = station;
        this.phone = phone;
    }

    @Parameterized.Parameters
    public static Object[][] getDataCustomer() {
        return new Object[][]{
                {"Иван", "Иванов", "ул. Пушкина, дом 123", STATION_1, "89999999999"},
                {"Петр", "Петров", "ул. Колотушкина", STATION_2, "89876543210"},
        };
    }

    @Before
    public void before() {
        String browserName = System.getenv(BROWSER_NAME_ENV_VARIABLE);
        driver =
                WebDriverFactory.createForName(browserName != null ? browserName : DEFAULT_BROWSER_NAME);
    }

    @Test
    public void orderHeaderButtonTest() {
        driver.get(PAGE_URL);
        StartPage startPage = new StartPage(driver);
        startPage.clickAcceptCookieButton();
        startPage.clickHeaderOrderButton();
        OrderPage orderPage = new OrderPage(driver);
        orderPage.customerData(firstName, secondName, address, station, phone);
        orderPage.forRentalData(ORDER_DATE, RENTAL_PERIOD, SCOOTER_COLOR);
        orderPage.clickYesButton();
        orderPage.checkOrderCreated(textOrderCreated);
    }

    @Test
    public void orderMiddleButtonTest() {
        driver.get(PAGE_URL);
        StartPage startPage = new StartPage(driver);
        startPage.clickAcceptCookieButton();
        startPage.clickMiddleOrderButton();
        OrderPage orderPage = new OrderPage(driver);
        orderPage.customerData(firstName, secondName, address, station, phone);
        orderPage.forRentalData(ORDER_DATE, RENTAL_PERIOD, SCOOTER_COLOR);
        orderPage.clickYesButton();
        orderPage.checkOrderCreated(textOrderCreated);
    }

    @After
    public void after() {
        driver.quit();
    }
}
