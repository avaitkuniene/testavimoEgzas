import org.example.Calculator;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class CalculatorTest {
    public static WebDriver browser;
    public String vartotojas = "Asta";
    public String slaptazodis = "12345";

    public String blogasSlaptazodis = "54321";

    public int sk1 = 1;
    public int sk2 = 2;
    public char operacija = '+';

    public int blogasSK = -5;


    @BeforeTest
    void setup() {
        Calculator.setup();
    }

    @AfterTest
    void close() {
        Calculator.atsijungti();
        Calculator.uzdaryti();
    }

    @Test(priority = 1)
    void userRegistrationPositive() {
        String actualResult = Calculator.sukurtiNaujaProfili(vartotojas, slaptazodis);
        String expectedResult = "http://localhost:8080/skaiciuotuvas";
        // http://localhost:8080/skaiciuotuvas {teig} arba http://localhost:8080/registruoti (neig)
        Calculator.atsijungti();

        Assert.assertEquals(actualResult, expectedResult, "Naujas naudotojas užregistruotas.");
    }

    @Test(priority = 2)
    void userRegistrationNegative() {
        String actualResult = Calculator.sukurtiNaujaProfili(vartotojas, slaptazodis);
        String expectedResult = "http://localhost:8080/skaiciuotuvas";

        Assert.assertNotEquals(actualResult, expectedResult, "Naudotojas neužregistruotas (toks naudotojas jau egzistuoja).");
    }

    @Test(priority = 4)
    void userLoginPositive() {
        String actualResult = Calculator.prisijungimas(vartotojas, slaptazodis);
        String expectedResult = "http://localhost:8080/";
        // http://localhost:8080/ (teig) http://localhost:8080/prisijungti?error (neig)

        Assert.assertEquals(actualResult, expectedResult, "Naudotojas sėkmingai prijungtas.");
    }

    @Test(priority = 3)
    void userLoginNegative() {
        String actualResult = Calculator.prisijungimas(vartotojas, blogasSlaptazodis);
        String expectedResult = "http://localhost:8080/";

        Assert.assertNotEquals(actualResult, expectedResult, "Naudotojas neprijungtas.");
    }

    @Test(priority = 5)
    void createOperationPositive() {
        String actualResult = Calculator.sukurtiOperacija(operacija, sk1, sk2);
        String expectedResult = "1 + 2 = 3";

        Assert.assertEquals(actualResult, expectedResult, "Operacija sėkminga.");
    }

    @Test(priority = 6)
    void createOperationNegative() {
        String actualResult = Calculator.sukurtiOperacija(operacija, sk1, blogasSK);
        String expectedResult = "1 + -5 = -4";

        Assert.assertNotEquals(actualResult, expectedResult, "Tokia operacija negalima.");
    }

    @Test(priority = 7)
    void searchOperationPositive() {
        String operationId = Calculator.ieskotiOperacijos(operacija, sk1, sk2);
        String actualResult = Calculator.veiksmas(operationId, "rodyti");
        String expectedResult = "http://localhost:8080/rodyti?id=" + operationId;

        Assert.assertEquals(actualResult, expectedResult, "Rodomas įrašas, kurio id =" + operationId);
    }

    @Test(priority = 8)
    void searchOperationNegative() {
        String operationId = Calculator.ieskotiOperacijos(operacija, sk1, sk2);
        String actualResult = Calculator.veiksmas(operationId, "rodyti");
        String expectedResult = "http://localhost:8080/rodyti?id=" + operationId;

        Assert.assertNotEquals(actualResult, expectedResult, "Tokio įrašo nėra");
    }

}

