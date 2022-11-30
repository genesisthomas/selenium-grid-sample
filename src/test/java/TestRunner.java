import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        features = {"src/test/resources/features"},
        glue = {"StepDefs"},
        tags = "@pass",
        plugin = { "pretty", "json:target/Cucumber.json", "junit:target/Cucumber.xml", "html:target/Cucumber.html" },
        monochrome = true
)
public class TestRunner extends AbstractTestNGCucumberTests {

    @DataProvider(parallel = true) //scenarios and rows in a scenario outline are executed in multiple threads
    public Object[][] scenarios() {
        return super.scenarios();
    }
}	