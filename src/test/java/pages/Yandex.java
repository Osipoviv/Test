package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static tests.Class.config;

public class Yandex {

    private final WebDriver driver;
    private final WebDriverWait wait;

    public Yandex(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 20);
    }

    public boolean openMarket() {
        return clickByXpath(config.getString("xPath.Market"));
    }

    public boolean openPC() {
        return clickByXpath(config.getString("xPath.PCInMarket"), config.getString("xPath.PCInMarketCheck"));
    }

    public boolean openCategory(String category) {
        return clickByXpath(config.getString("xPath.TMP.CategoryInPc").replace("$cat", category), config.getString("xPath.TMP.CategoryInPcCheck").replace("$cat", category));
    }

    public boolean advancedSearch() {
        return clickByXpath(config.getString("xPath.AdvancedSearchInCategory"), config.getString("xPath.AdvancedSearchInCategoryCheck"));
    }

    public boolean applyFilter() {
        clickByXpath(config.getString("xPath.BtnAdvancedSearchApply"));
        waitUntil(config.getString("xPath.Load"));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(config.getString("xPath.Load"))));
        return waitUntil(config.getString("xPath.BtnAdvancedSearchApplyCheck"));
    }

    public boolean setPrice(String min, String max) {
        if (waitUntil(config.getString("xPath.AdvancedSearchPricefrom")) && waitUntil(config.getString("xPath.AdvancedSearchPriceto"))) {
            driver.findElement(By.xpath(config.getString("xPath.AdvancedSearchPricefrom"))).sendKeys(min);
            driver.findElement(By.xpath(config.getString("xPath.AdvancedSearchPriceto"))).sendKeys(max);
            return true;
        }
        return false;
    }

    public boolean setManufacturer(List<String> manufacturers) {
        clickByXpath(config.getString("xPath.BtnEnotherinAdvancedSearch"));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(config.getString("xPath.BtnEnotherinAdvancedSearch"))));
        for (String manufacturer : manufacturers) {

            if (!waitUntil(config.getString("xPath.TMP.Manufacturer").replace("$manufacturer", manufacturer))) {
                return false;
            }
            driver.findElement(By.xpath(config.getString("xPath.TMP.Manufacturer").replace("$manufacturer", manufacturer))).click();
        }
        return true;
    }

    private boolean clickByXpath(String xpath) {
        try {
            if (waitUntil(xpath)) {
                driver.findElement(By.xpath(xpath)).click();
                return true;
            }
        } catch (org.openqa.selenium.TimeoutException e) {
        }
        return false;
    }

    private boolean clickByXpath(String xpath, String waitUntilXpath) {
        try {
            if (waitUntil(xpath)) {
                driver.findElement(By.xpath(xpath)).click();
                assertTrue("Переход по ссылке не произошел.", waitUntil(waitUntilXpath));
                return true;
            }
        } catch (org.openqa.selenium.TimeoutException e) {
        }
        return false;
    }

    private boolean waitUntil(String xpath) {
        try {

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
            return true;
        } catch (org.openqa.selenium.TimeoutException e) {
            return false;
        }

    }


    public boolean countElements(int count_elements_in_page) {

        if (waitUntil(config.getString("xPath.CountElementsInPage"))) {
            if (driver.findElements(By.xpath(config.getString("xPath.CountElementsInPage"))).size() == count_elements_in_page) {
                return true;
            }
        }
        return false;
    }

    public boolean searchFirstElement() {
        if (waitUntil(config.getString("xPath.CountElementsInPage"))) {
            String name = driver.findElements(By.xpath(config.getString("xPath.CountElementsInPage"))).get(0).findElement(By.xpath(config.getString("xPath.NameFirstElement"))).getText();
            driver.findElement(By.xpath(config.getString("xPath.HeaderSearch"))).sendKeys(name);
            clickByXpath(config.getString("xPath.BtnHeaderSearchgoryInPc"));
            return waitUntil(config.getString("xPath.TitleInProductPage").replace("$name", name));
        }

        return false;
    }
}
