package examenSelenium;

import org.testng.annotations.DataProvider;

public class dataProvider {
    @DataProvider(name = "emails")
    public Object[][] enviarLogin(){
        return new Object[][]{
                {"hola@email.com", "holamundo"},
                {"adios@email.com", "holamundo"},
                {"quetal@email.com", "holamundo"}
        };
    }
}
