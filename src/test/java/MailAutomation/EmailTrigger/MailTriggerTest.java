package MailAutomation.EmailTrigger;

import java.util.Date;
import org.testng.annotations.Test;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

public class MailTriggerTest {
	@Test
	public void mailTriggerTest() throws IOException, InterruptedException {
		String appUrl = "https://outlook.live.com/mail/0/";
		WebDriverManager.chromedriver().setup();

		WebDriver driver = new ChromeDriver();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
		driver.get(appUrl);
		driver.manage().window().maximize();

		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[text()='Feedback']")));
		driver.findElement(By.xpath("//div[@class='wf-menu']//a[normalize-space(text()) = 'Sign in']")).click();

		// Login Page
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(text(),'Sign-in')]")));
		driver.findElement(By.name("loginfmt")).clear();
		driver.findElement(By.name("loginfmt")).sendKeys("shravzkumar.01@outlook.com");
		driver.findElement(By.id("idSIButton9")).click();

		if (driver.findElement(By.xpath("//h1[text()='Sign in another way']")).isDisplayed()) {
			driver.findElement(By.xpath(
					"//div[contains(@id,'fui-CardHeader')]//span[normalize-space(text()) = 'Use your password']"))
					.click();
		}
		wait.until(ExpectedConditions.presenceOfElementLocated(By.name("passwd")));
		driver.findElement(By.name("passwd")).clear();
		driver.findElement(By.name("passwd")).sendKeys("SaiKrishna5@9!");
		driver.findElement(By.xpath("//button[text()='Next']")).click();

		Thread.sleep(1500);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h1[contains(text(),'Stay signed in')]")));
		driver.findElement(By.xpath("//button[text()='No']")).sendKeys(Keys.ENTER);

		Thread.sleep(4000);
		driver.navigate().refresh();
		Thread.sleep(4000);
		wait.until(ExpectedConditions.presenceOfElementLocated(
				By.xpath("//span[contains(text(),'Create a new email message')]/ancestor::button")));

		driver.findElement(By.xpath("//span[contains(text(),'Create a new email message')]/ancestor::button")).click();

		driver.findElement(By.xpath("//div[@aria-label='To']")).sendKeys("artgurl.shravz@gmail.com");
		driver.findElement(By.xpath("//input[@aria-label ='Subject' and contains(@class,'fui-Input')]"))
				.sendKeys("Daily Batch Report");

		// Reading from File
		Date todayDate = new Date();
		SimpleDateFormat format = new SimpleDateFormat("YYYYMMdd");
		String currentDate = format.format(todayDate);

		String staticPath = "C:\\Users\\kumar\\OneDrive\\Documents\\Automation Trial\\";
		String dateWiseFileName = currentDate + ".txt";
		String todaysFilePath = staticPath + dateWiseFileName;

		File file = new File(todaysFilePath);
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		String line;
		StringBuilder contentBuilder = new StringBuilder();
		while ((line = br.readLine()) != null) {
			contentBuilder.append(line).append(System.lineSeparator());
		}
		br.close();
		fr.close();
		String fileContent = contentBuilder.toString();

		StringBuilder finalContent = new StringBuilder();
		finalContent.append("Hi All," + "\n" + "Please find below today's batch execution report" + "\n")
				.append(fileContent).append("\n" + "Thanks & Regards," + "\n" + "Shravya")
				.append(System.lineSeparator());

		// Entering the contents of the mail into New Mail Section.
		driver.findElement(By.xpath("//div[@id='editorParent_1']/child::div[@aria-label ='Message body']"))
				.sendKeys(finalContent.toString());
		Thread.sleep(1500);
		driver.findElement(By.xpath("//button[contains(@title,'Send') or @arial-label='Send']")).click();
		Thread.sleep(2500);
		driver.quit();
	}
}
