package testRunner;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions
        (
        		//features= {".//features/Login.feature"},
        		features= {".//features/LoginDDT.feature"},
                glue={"stepDefinitions"},
                dryRun=false
               
        )

public class TestRunner {

}
