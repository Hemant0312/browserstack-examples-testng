package com.browserstack.examples.suites;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;

import io.github.webdriver.config.Platform;
import io.github.webdriver.core.WebDriverFactory;
import io.github.webdriver.testng.LazyInitWebDriverIterator;
import io.github.webdriver.testng.ManagedWebDriver;
import io.github.webdriver.testng.listeners.WebDriverListener;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Anirudha Khanna
 */
@Listeners({WebDriverListener.class})
public abstract class BaseTest {

    @DataProvider(name="webdriver", parallel = true)
    public static Iterator<Object[]> provideWebDrivers(Method testMethod) {
        return new LazyInitWebDriverIterator(testMethod.getName(),
                                             WebDriverFactory.getInstance().getPlatforms(),
                                             new Object[0]);
    }

    @DataProvider(name="managedwebdriver", parallel = true)
    public static Object[] provideManagedWebDrivers(Method testMethod) {
        List<ManagedWebDriver> managedWebDrivers = createManagedWebDrivers(testMethod.getName());
        return managedWebDrivers.toArray(new ManagedWebDriver[0]);
    }

    protected static List<ManagedWebDriver> createManagedWebDrivers(String testMethodName) {
        WebDriverFactory webDriverFactory = WebDriverFactory.getInstance();
        final List<ManagedWebDriver> managedWebDrivers = new ArrayList<>();
        List<Platform> platforms = webDriverFactory.getPlatforms();
        platforms.forEach( p -> {
            managedWebDrivers.add(new ManagedWebDriver(testMethodName, p));
        });
        return managedWebDrivers;
    }
}
