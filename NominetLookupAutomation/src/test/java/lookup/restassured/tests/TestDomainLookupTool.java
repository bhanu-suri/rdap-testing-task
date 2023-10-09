package lookup.restassured.tests;

import org.testng.annotations.Test;
import org.testng.annotations.DataProvider;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import io.restassured.http.ContentType;

public class TestDomainLookupTool {
	
	@Test(dataProvider="region_and_domain")
	public void testNameserverAvailableForDomain(String region, String domain) {
		
	    given().
	      pathParam("region", region).
	      pathParam("domainName", domain).
	    when().
	      get("https://rdap.nominet.uk/{region}/domain/{domainName}").
	    then().
	      assertThat().
	      statusCode(200).
	    and().
	      contentType(ContentType.JSON).
	    and().
	      body("nameservers.ldhName",is(not(empty())));
	}
	
	@DataProvider(name="region_and_domain")
	public Object[][] createTestDataRecords() {
	    return new Object[][] {
	        {"wales","bbc.wales"},
	        {"uk","yahoo.co.uk"},
	        {"uk","gov.uk"}
	        //add negative case where nameserver is missing
	    };
	}
}
