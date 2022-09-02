package Selenium_4_Tests_Practice.Pages;

import Selenium_4_Tests_Practice.Components.FormFieldComponent;
import Selenium_4_Tests_Practice.Components.InputTextComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class RegisterPage {

    public WebDriver driver;

    /**
     * Constructor for the RegisterPage class.
     */
    public RegisterPage(WebDriver driver) {
        super();
    }

    private enum Using {
        ERROR_MESSAGES(By.xpath("//div[@class='text-danger']")),
        CONTINUE_BUTTON(By.xpath("//input[@value='Continue']"));

        public final By selector;

        Using(By selector) {
            this.selector = selector;
        }
    }

    /**
     * Interface Form Fields.
     */
    public interface FormField<P> {
        By getSelector();

        FormFieldComponent getComponent(WebElement element, P page);
    }

    /**
     * Enum containing the elements of Register Account Form
     * Also including 'First Name and Last name' fields with the minimum and maximum characters for each component.
     * Functional Interface for the FormFieldComponent that represents a function that accepts two arguments and produces a result.
     */
    public enum FirstAndLastName implements FormField<RegisterPage> {
        FIRST_NAME_INPUT(By.xpath("//input[@id='input-firstname']"),
                (element, page) -> new InputTextComponent(element, page, 1,32)),
        LAST_NAME_INPUT(By.xpath("//input[@id='input-lastname']"),
                (element, page) -> new InputTextComponent(element, page, 1,32));

        final By selector;
        final BiFunction<WebElement, WebDriver, FormFieldComponent> componentFactory;

        FirstAndLastName(By selector, BiFunction<WebElement, WebDriver, FormFieldComponent> componentFactory) {
            this.selector = selector;
            this.componentFactory = componentFactory;
        }

        @Override
        public By getSelector() {
            return selector;
        }

        @Override
        public FormFieldComponent getComponent(WebElement element, RegisterPage page) {
            return componentFactory.apply(element, (WebDriver) page);
        }
    }


    /**
     * FormFieldComponent Final
     */
    public FormFieldComponent getFormFieldComponent(final FormField<RegisterPage> formField) {
        return formField.getComponent(driver.findElement(formField.getSelector()), this);
    }

    /**
     * Gets the Error messages from the Register Account Form
     *
     * @return The list containing the error messages
     */
    public List<String> getFieldErrorMessages() {
        return driver.findElements(Using.ERROR_MESSAGES.selector).stream().map(WebElement::getText)
                .collect(Collectors.toList());
    }

    /**
     * Click on the Continue button
     */
    public void clickContinueButton() {
        driver.findElement(Using.CONTINUE_BUTTON.selector).click();
    }
}