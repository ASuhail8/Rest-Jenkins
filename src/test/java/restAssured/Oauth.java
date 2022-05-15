package restAssured;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import restAssured.pojo.API;
import restAssured.pojo.WebAutomation;
import restAssured.pojo.getCourse;

public class Oauth {

	@Test
	public void test() throws InterruptedException {

		String[] WebAutomationCourseTitles = { "Selenium Webdriver Java", "Cypress", "Protractor" };
		/*
		 * // Since Chrome and firefox not working due to 2 factor auth enabled in gamil
		 * ids WebDriverManager.firefoxdriver().setup(); WebDriver driver = new
		 * FirefoxDriver(); driver.get(
		 * "https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&Auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php&state=verifyfjdss"
		 * ); driver.manage().window().maximize();
		 * driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		 * driver.findElement(By.xpath("//input[@type='email']")).sendKeys("flo4abs");
		 * driver.findElement(By.xpath("//input[@type='email']")).sendKeys(Keys.ENTER);
		 * Thread.sleep(3000);
		 * driver.findElement(By.xpath("//input[@type='password']")).sendKeys(
		 * "floweroflover");
		 * driver.findElement(By.xpath("//input[@type='password']")).sendKeys(Keys.ENTER
		 * ); Thread.sleep(5000);
		 * 
		 * String url = driver.getCurrentUrl(); driver.quit();
		 * 
		 * String partialCode = url.split("code=")[1]; String code =
		 * partialCode.split("&scope")[0];
		 */
		// get Access Token request

		String accessTokenResponse = given().urlEncodingEnabled(false).log().all()
				.queryParam("code", "4%2F0AX4XfWgUb2Ty1rQ6dYD2IXDN88yVou_poHa_WIPzVgxYDnS_EOD8UFegiMjR0H3E-fmqlw")
				.queryParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
				.queryParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
				.queryParam("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
				.queryParam("grant_type", "authorization_code").when().log().all()
				.post("https://www.googleapis.com/oauth2/v4/token").asString();

		JsonPath js = new JsonPath(accessTokenResponse);
		String accessToken = js.getString("access_token");

		// Actual Request to hit rahul shetty academy

		getCourse gc = given().queryParam("access_token", accessToken).expect().defaultParser(Parser.JSON).when()
				.get("https://rahulshettyacademy.com/getCourse.php").as(getCourse.class);

		List<API> apiCourses = gc.getCourses().getApi();
		for (int i = 0; i < apiCourses.size(); i++) {
			if (apiCourses.get(i).getCourseTitle().equalsIgnoreCase("Rest Assured Automation using Java")) {
				System.out.println(apiCourses.get(i).getPrice());
			}
		}

		// get the course name of web automation
		List<WebAutomation> webAutomationCourses = gc.getCourses().getWebAutomation();
		// To store dynamic value use ArrayList
		ArrayList<String> al = new ArrayList<String>();
		for (int i = 0; i < webAutomationCourses.size(); i++) {
			al.add(webAutomationCourses.get(i).getCourseTitle());
		}
		System.out.println(al);

		// convert arrays to arrayList
		List<String> expectedList = Arrays.asList(WebAutomationCourseTitles);
		System.out.println(expectedList);

		// get the instructor and url
		System.out.println("instructor :" + gc.getInstructor());
		System.out.println("url :" + gc.getUrl());

		AssertJUnit.assertTrue(al.equals(expectedList));

	}

}
