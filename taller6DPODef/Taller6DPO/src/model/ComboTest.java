package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

public class ComboTest {

    private Combo combo;
    private Producto producto1, producto2;
    
    @BeforeEach
    public void setUp() {
        combo = new Combo("ComboTest", 20.0);
        producto1 = new ProductoMenu("Producto1", 10000);
        producto2 = new ProductoMenu("Producto2", 5000);
        combo.agregarItemACombo(producto1);
        combo.agregarItemACombo(producto2);
    }

    @Test
    public void agregarItemAComboTest() {
        Producto producto3 = new ProductoMenu("Producto3", 3000);
        combo.agregarItemACombo(producto3);
        assertEquals(3, combo.itemsCombo.size());
        assertEquals(producto3, combo.itemsCombo.get(2));
    }

    @Test
    public void getPrecioTest() {
        int expectedPrice = (int)Math.round((producto1.getPrecio() + producto2.getPrecio()) * (1 - (combo.getDescuento() / 100)));
        assertEquals(expectedPrice, combo.getPrecio());
    }

    @Test
    public void generarTextoFacturaTest() {
        String expectedText = producto1.generarTextoFactura() + producto2.generarTextoFactura();
        assertEquals(expectedText, combo.generarTextoFactura());
    }
}
