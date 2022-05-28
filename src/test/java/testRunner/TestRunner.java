package testRunner;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions
        (
        		features= {".//features/Login.feature",".//features/AccountRegistration.feature"},
                glue={"stepDefinitions"},
                dryRun=false,
                //tags="@sanity and not @regression",    //scenarios tagged with @sanity
        		plugin= {"pretty",
        				"html:reports/myreport.html",
        				"rerun:target/rerun.txt", //To capture failures
        				}
                
        )

public class TestRunner {

}
//Tags:
//tags="@sanity and @regression"   //scenarios tagged with @sanity and @regression
//tags="@sanity or @regression"   //scenarios tagged with either @sanity or @regression
//tags="@sanity  and not @regression" //scenarios tagged with @sanity but not tagged with @regression

//feature options:
	//features= {".//features/LoginDDT.feature"},
	//features= {".//features/AccountRegistration.feature"},
	//features={".//features"},

//To run failure scenarios:s
	//features= "@target/rerun.txt"