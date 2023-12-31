package definitions;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import support.Helper;
import support.TestContext;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

import static org.assertj.core.api.Assertions.*;
import static support.TestContext.getDriver;

public class MinniStepDefs {
    @Given("MP open url {string}")
    public void mpOpenUrl(String url) {
        getDriver().get(url);
    }

    @Then("MP type {string} into element with xpath {string}")
    public void mpTypeIntoElementWithXpath(String text, String xpath) {
        getDriver().findElement(By.xpath(xpath)).sendKeys(text);
    }

    @Then("MP type {string} into {string}")
    public void iTypeInto(String text, String element_text) {
        getDriver().findElement(By.xpath(MinnipVariable.ElementXpathForText(element_text))).sendKeys(text);
    }

    @Then("MP click on element with xpath {string}")
    public void mpClickOnElementWithXpath(String xpath) {
        getDriver().findElement(By.xpath(xpath)).click();
    }

    @Then("MP wait for element with xpath {string} to be present")
    public void mpWaitForElementWithXpathToBePresent(String xpath) {
        new WebDriverWait(getDriver(), 5, 100).until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
    }

    @Then("MP element with xpath {string} should contain text {string}")
    public void mpElementWithXpathShouldContainText(String xpath, String text) {
        String realText = getDriver().findElement(By.xpath(xpath)).getText();
        assertThat(realText).isEqualTo(text);
    }

    @Then("MP is successfully logged in")
    public void mpIsSuccessfullyLoggedIn() {
    }

    @Then("MP login is unsuccessful")
    public void mpLoginIsUnsuccessful() {
    }

    @Then("MP wait for {int} sec")
    public void mpWaitForSec(int sec) throws InterruptedException {
        Thread.sleep(sec * 1000);
    }

    @Then("MP take screenshot")
    public void mpTakeScreenshot() throws IOException {
        TakesScreenshot screenshotTaker = (TakesScreenshot) getDriver();
        File screenshot = screenshotTaker.getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(screenshot, new File("target/screenshots/screenshot " + new Date() + ".png"));
    }

    @And("MP click on element with xpath {string}cdk-overlay{int}{string}")
    public void mpClickOnElementWithXpathCdkOverlay(String arg0, int arg1, String arg2) {
    }

    @Then("MP move slider with xpath {string} to the {string} for {int} steps")
    public void mpMoveSliderWithXpathToTheForSteps(String xpath, String direction, int steps) {
        WebElement slider = getDriver().findElement(By.xpath(xpath));
        for (int i=0; i<steps; i++) {
            if (direction.equals("right")) {
                slider.sendKeys(Keys.ARROW_RIGHT);
            }
            if (direction.equals("left")) {
                slider.sendKeys(Keys.ARROW_LEFT);
            }
        }
    }

    private String xpathForQuestion(String questionID) {
        return "//*[contains(text(),'"+questionID+"')]/../../..mat-slider";
    }

    @Then("MP move slider for {string} question to the {string} for {int} steps")
    public void mpMoveSliderForQuestionToTheForSteps(String questionID, String direction, int steps) {
        WebElement slider = getDriver().findElement(By.xpath(xpathForQuestion(questionID)));
        for (int i = 0; i < steps; i++) {
            if (direction.equals("right")) {
                slider.sendKeys(Keys.ARROW_RIGHT);
            }
            if (direction.equals("left")) {
                slider.sendKeys(Keys.ARROW_LEFT);
            }
        }
    }

    @And("MP is successfully logged out")
    public void mpIsSuccessfullyLoggedOut() {
    }

    @Then("MP press tab key on keyboard to navigate to xpath {string}")
    public void mpPressTabKeyOnKeyboardToNavigateToXpath(String xpath) {
        WebElement inputField = getDriver().findElement(By.xpath(xpath));
        inputField.sendKeys(Keys.TAB);
    }

    @Then("MP press enter key on element with xpath {string}")
    public void mpPressEnterKeyOnElementWithXpath(String xpath) {
        WebElement inputField = getDriver().findElement(By.xpath(xpath));
        inputField.sendKeys(Keys.ENTER);
    }

    @Then("MP should see page title as {string}")
    public void mpShouldSeePageTitleAs(String title) {
        assertThat(getDriver().getTitle()).isEqualTo(title);
    }

    @Then("^MP scroll to the element with xpath \"([^\"]*)\" with offset (\\d+)$")
    public void iScrollToTheElementWithXpathWithOffset(String xpath, int offset) throws Exception {
        WebElement element = getDriver().findElement(By.xpath(xpath));
        JavascriptExecutor executor = (JavascriptExecutor) getDriver();
        executor.executeScript("arguments[0].scrollIntoView(false);", element);
        executor.executeScript("window.scrollBy(0, " + offset + ");", element);
        Thread.sleep(500);
    }

    @Then("MP activate user with email {string}")
    public void mpActivateUserWithEmail(String email) throws SQLException, IOException {
        String activationResult = Helper.getAccessToken(email);
        String[] ar = activationResult.split(";");
        Helper.activateUser(Integer.parseInt(ar[0]), ar[1]);
    }

    @Then("^Mp element with xpath \"([^\"]*)\" should not contain text \"([^\"]*)\"$")
    public void mpElementWithXpathShouldNotContainText(String xpath, String text) {
        String actualText = getDriver().findElement(By.xpath(xpath)).getText();
        assertThat(actualText).doesNotContain(text);
    }

    @Then("MP element with xpath {string} should be displayed")
    public void mpElementWithXpathShouldBeDisplayed(String xpath) {
        assertThat(getDriver().findElement(By.xpath(xpath)).isDisplayed()).isTrue();
    }
}
