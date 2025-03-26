import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features", // Caminho dos arquivos de feature
        glue = "com.myproject94.myerp.steps",        // Pacote onde estão os Step Definitions
        plugin = {"pretty", "html:target/cucumber-report.html"} // Plugins para relatório dos testes
)
public class RunCucumberTest {

}
