package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

public class ProductoAjustadoTest {

    private ProductoAjustado productoAjustado;
    private ProductoMenu base;
    private ArrayList<Ingrediente> agregados, eliminados;
    private Ingrediente ingrediente1, ingrediente2;

    @BeforeEach
    public void setUp() {
        base = new ProductoMenu("ProductoBase", 10000);
        ingrediente1 = new Ingrediente("Ingrediente1", 1000);
        ingrediente2 = new Ingrediente("Ingrediente2", 2000);
        agregados = new ArrayList<>();
        eliminados = new ArrayList<>();
        agregados.add(ingrediente1);
        agregados.add(ingrediente2);
        productoAjustado = new ProductoAjustado(base, eliminados, agregados);
    }

    @Test
    public void getPrecioTest() {
        assertEquals(13000, productoAjustado.getPrecio());
    }

    @Test
    public void generarTextoFacturaTest() {
        String expectedText = "ProductoBase: 13000";
        assertEquals(expectedText, productoAjustado.generarTextoFactura());
    }
}

