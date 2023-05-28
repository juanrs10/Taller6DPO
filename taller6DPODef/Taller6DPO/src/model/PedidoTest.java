package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PedidoTest {

    private Pedido pedido;
    private Producto producto1, producto2;
    
    @BeforeEach
    public void setUp() {
        pedido = new Pedido("Juan", "Calle 123");
        producto1 = new ProductoMenu("Producto1", 50000);
        producto2 = new ProductoMenu("Producto2", 50000);
    }

    @Test
    public void agregarProductoTest() throws PedidoExcedeValorMaximoException {
        pedido.agregarProducto(producto1);
        assertEquals(1, pedido.getProductos().size());
        assertEquals(producto1, pedido.getProductos().get(0));

        pedido.agregarProducto(producto2);
        assertEquals(2, pedido.getProductos().size());
        assertEquals(producto2, pedido.getProductos().get(1));
    }

    @Test
    public void agregarProductoExcepcionTest() {
        assertThrows(PedidoExcedeValorMaximoException.class, () -> {
            for (int i = 0; i < 4; i++) {
                pedido.agregarProducto(producto1);
            }
        });
    }

    @Test
    public void getPrecioNetoPedidoTest() throws PedidoExcedeValorMaximoException {
        pedido.agregarProducto(producto1);
        pedido.agregarProducto(producto2);
        int expectedPrice = producto1.getPrecio() + producto2.getPrecio();
        assertEquals(expectedPrice, pedido.getPrecioNetoPedido());
    }

    @Test
    public void getPrecioIvaPedidoTest() throws PedidoExcedeValorMaximoException {
        pedido.agregarProducto(producto1);
        pedido.agregarProducto(producto2);
        int expectedPrice = (int)((producto1.getPrecio() + producto2.getPrecio()) * 0.19);
        assertEquals(expectedPrice, pedido.getPrecioIvaPedido());
    }

    @Test
    public void generarTextoFacturaTest() throws PedidoExcedeValorMaximoException {
        pedido.agregarProducto(producto1);
        pedido.agregarProducto(producto2);
        String expectedText = "Id pedido: 0\nNombre: Juan\nDirección: Calle 123\n" + producto1.generarTextoFactura() + "\n" + producto2.generarTextoFactura() + "\n" + "Neto: 100000\nIVA: 19000\nTotal a pagar: 119000";
        assertEquals(expectedText, pedido.generarTextoFactura());
    }

    @Test
    public void guardarFacturaTest() throws IOException, PedidoExcedeValorMaximoException {
        pedido.agregarProducto(producto1);
        pedido.agregarProducto(producto2);
        pedido.guardarFactura("Facturas.txt");
        String expectedText = "Id pedido: 0\nNombre: Juan\nDirección: Calle 123\n" + producto1.generarTextoFactura() + "\n" + producto2.generarTextoFactura() + "\n" + "Neto: 100000\nIVA: 19000\nTotal a pagar: 119000";
        try (Stream<String> lines = Files.lines(Paths.get("Facturas.txt"))) {
            String content = lines.collect(Collectors.joining("\n"));
            assertEquals(expectedText, content);
        }
        new File("Facturas.txt").delete();
    }
}
