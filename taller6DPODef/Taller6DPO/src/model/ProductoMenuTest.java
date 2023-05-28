package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ProductoMenuTest {

    private ProductoMenu producto;

    @BeforeEach
    public void setUp() {
        producto = new ProductoMenu("Producto de prueba", 10000);
    }

    @Test
    public void generarTextoFacturaTest() {
        String expectedText = "Producto de prueba: 10000";
        assertEquals(expectedText, producto.generarTextoFactura());
    }
}
