package tests;

import org.apache.commons.configuration.ConfigurationException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import pages.Yandex;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertTrue;

@RunWith(Parameterized.class)
public class TestMarket extends Class {
    private static List<Object[]> variables = new ArrayList<Object[]>();
    @Parameterized.Parameter
    public String category;
    @Parameterized.Parameter(value = 1)
    public String minPrice;
    @Parameterized.Parameter(value = 2)
    public String maxPrice;
    @Parameterized.Parameter(value = 3)
    public List<String> manufacturers;

    @Parameterized.Parameters(name = "{index}: ({0},{1},{2},{3}")
    public static List<Object[]> data() throws IOException, ConfigurationException {
        variables.add(new Object[]{"Ноутбуки", "0", "30000", Arrays.asList("HP", "Lenovo")});
        variables.add(new Object[]{"Планшеты", "20000", "25000", Arrays.asList("Acer", "DELL")});
        return variables;
    }

    @Test
    public void Market() {
        driver.get(config.getString("HomePage"));
        Yandex yandex = new Yandex(driver);
        assertTrue("Не найдена ссылка на маркет", yandex.openMarket());
        assertTrue("Не найдена ссылка на компьютеры", yandex.openPC());
        assertTrue("Не найдена ссылка на " + category, yandex.openCategory(category));
        assertTrue("Не найдена ссылка на расширенный поиск", yandex.advancedSearch());
        assertTrue("Не удалось ввести ценовой диапазон", yandex.setPrice(minPrice, maxPrice));
        assertTrue("Не удалось выбрать производителя", yandex.setManufacturer(manufacturers));
        assertTrue("Не удалось нажать кнопку применить", yandex.applyFilter());
        assertTrue("Вывелось не верное количество товаров", yandex.countElements(config.getInt("CountElementsInPage")));
        assertTrue("Первый элемент из списка не найден", yandex.searchFirstElement());
    }
}