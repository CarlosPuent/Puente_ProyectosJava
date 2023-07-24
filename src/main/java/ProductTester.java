package main.java;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.JOptionPane;
import java.util.Scanner;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ProductTester {

    private static final String formato = "| %-10s | %-43s | %-10s | $ %-8s |";
    private static final String formato1 = "| %-10s | %-43s | %-10s | %-10s |";
    private static final String separador = "+------------+---------------------------------------------+------------+------------+";

    private static final String FILE_PATH = "productos.txt";

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        JOptionPane JOptionPane = new JOptionPane(System.in);

        List<Product> productos = loadProductsFromFile();

        // Mostrar la tabla completa, incluyendo los registros cargados desde el archivo
        displayTable(productos);

        // Permite al usuario agregar un nuevo producto
        Product newProduct = readNewProduct(in);
        productos.add(newProduct);

        // Guardar los registros en el archivo
        saveProductsToFile(productos);

        // Imprimir la tabla actualizada
        displayTable(productos);

        // Eliminar un producto por ID (el usuario podría ingresar este ID)

        /*System.out.println("Ingresa 'B' si quieres eliminar un registro, de lo contario ingresa "
                +"\n"+ "'C' para continuar ");*/

        /*char Simbolo;
        Simbolo = in.next().charAt(0);*/

        String input = javax.swing.JOptionPane.showInputDialog(null, "Ingresa 'B' si quieres eliminar un registro, de lo contrario ingresa 'C' para continuar");
        char Simbolo = input.isEmpty() ? '\0' : Character.toLowerCase(input.charAt(0));


        switch (Character.toLowerCase(Simbolo)) {
            case 'b':
                /*System.out.print("Ingrese el ID del producto a eliminar: ");
                int idToRemove = in.nextInt();
                removeProductById(productos, idToRemove);*/

                int idToRemove = Integer.parseInt(javax.swing.JOptionPane.showInputDialog(null,
                        "Ingrese el ID del producto a eliminar: "));
                removeProductById(productos, idToRemove);
                break;
            case 'c':
                javax.swing.JOptionPane.showMessageDialog(null, "Haz click en 'Run' para cargar de nuevo el programa e ingresar nuevos registros" );
                break;
            default:
                javax.swing.JOptionPane.showMessageDialog(null, "Algo anda mal, vuelva a cargar el programa");
                break;
        }

        // Guardar los registros actualizados en el archivo
        saveProductsToFile(productos);

        // Imprimir la tabla nuevamente
        displayTable(productos);

    }

    private static void displayTable(List<Product> products) {
        System.out.println(separador);
        System.out.println("                  BIENVENIDO A MI SISTEMA DE GESTIÓN DE INVENTARIO");
        System.out.println(separador);
        System.out.println(String.format(formato1, "ID", "NOMBRE", "EXISTENCIA", "PRECIO"));
        System.out.println(separador);

        for (Product product : products) {
            System.out.println(String.format(formato, product.getIdProducto(),
                    product.getNombreProducto().toUpperCase(), product.getExistencia(), product.getPrecio()));
            System.out.println(separador);
        }
    }

    /*private static Product readNewProduct(Scanner in) {
        System.out.println("Ingrese los detalles del nuevo producto:");
        System.out.print("ID: ");
        int tempNumber = in.nextInt();
        in.nextLine(); // Consumir el salto de línea después de leer el entero
        System.out.print("Nombre: ");
        String tempName = in.nextLine();
        System.out.print("Existencia: ");
        int tempQty = in.nextInt();
        System.out.print("Precio: ");
        double tempPrice = in.nextDouble();

        return new Product(tempNumber, tempName, tempQty, tempPrice);
    }*/

    private static Product readNewProduct(Scanner in) {

        int tempNumber = Integer.parseInt(JOptionPane.showInputDialog("Ingrese los detalles del nuevo producto:" +"\n"+ "ID: "));
        //JOptionPane.showMessageDialog(null, tempNumber);

        String tempName = JOptionPane.showInputDialog("Ingrese el nombre del producto: "+"\n"+ "Nombre: ");
        //JOptionPane.showMessageDialog(null, tempName);

        int tempQty = Integer.parseInt(JOptionPane.showInputDialog("Ingrese la existencia del producto: "+"\n"+ "Existencia: "));
        //JOptionPane.showMessageDialog(null,  tempQty);

        double tempPrice = Double.parseDouble(JOptionPane.showInputDialog("Ingrese el precio del producto: "+"\n"+ "Precio: "));
        //JOptionPane.showMessageDialog(null,  tempPrice);

        JOptionPane.showMessageDialog(null, "El producto se agregó correctamente");
        return new Product(tempNumber, tempName, tempQty, tempPrice);
    }

    private static List<Product> loadProductsFromFile() {
        List<Product> products = new ArrayList<>();

        try (FileInputStream file = new FileInputStream("productos.xlsx");
             Workbook workbook = WorkbookFactory.create(file)) {

            Sheet sheet = workbook.getSheetAt(0);
            DataFormatter dataFormatter = new DataFormatter();

            for (Row row : sheet) {
                int id = Integer.parseInt(dataFormatter.formatCellValue(row.getCell(0)));
                String name = dataFormatter.formatCellValue(row.getCell(1));
                int quantity = Integer.parseInt(dataFormatter.formatCellValue(row.getCell(2)));
                double price = Double.parseDouble(dataFormatter.formatCellValue(row.getCell(3)));

                products.add(new Product(id, name, quantity, price));
            }
        } catch (IOException | EncryptedDocumentException e) {
            System.out.println("Error al cargar los productos desde el archivo Excel: " + e.getMessage());
        }

        return products;
    }

    //Para archivos txt
    /*private static List<Product> loadProductsFromFile() {
        List<Product> products = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    int id = Integer.parseInt(parts[0].trim());
                    String name = parts[1].trim();
                    int quantity = Integer.parseInt(parts[2].trim());
                    double price = Double.parseDouble(parts[3].trim());
                    products.add(new Product(id, name, quantity, price));
                }
            }
        } catch (IOException e) {
            System.out.println("Error al cargar los productos desde el archivo: " + e.getMessage());
        }

        return products;
    }*/

    private static void saveProductsToFile(List<Product> products) {
        try (Workbook workbook = new XSSFWorkbook();
             FileOutputStream fileOut = new FileOutputStream("productos.xlsx")) {

            Sheet sheet = workbook.createSheet("Productos");

            int rowIdx = 0;
            for (Product product : products) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(product.getIdProducto());
                row.createCell(1).setCellValue(product.getNombreProducto());
                row.createCell(2).setCellValue(product.getExistencia());
                row.createCell(3).setCellValue(product.getPrecio());
            }

            workbook.write(fileOut);
        } catch (IOException e) {
            System.out.println("Error al guardar los productos en el archivo Excel: " + e.getMessage());
        }
    }

    //Para archivos txt
    /*private static void saveProductsToFile(List<Product> products) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Product product : products) {
                String line = product.getIdProducto() + ", " + product.getNombreProducto() + ", " +
                        product.getExistencia() + ", " + product.getPrecio();
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al guardar los productos en el archivo: " + e.getMessage());
        }
    }*/

    private static void removeProductById(List<Product> products, int idToRemove) {
        boolean removed = false;

        Iterator<Product> iterator = products.iterator();
        while (iterator.hasNext()) {
            Product product = iterator.next();
            if (product.getIdProducto() == idToRemove) {
                iterator.remove();
                removed = true;
                break;
            }
        }
        if (removed) {
            System.out.println("Producto con ID " + idToRemove + "ha sido eliminado exitosamente.");
        } else {
            System.out.println("No se encontró un producto con el ID " + idToRemove + ".");
        }
    }

    //Código para concectarlo a mi base de datos

    /*public class DatabaseConnection {
        // Datos de conexión a la base de datos (modifica estos valores según tu configuración)
        private static final String DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=tu_base_de_datos";
        private static final String USER = "tu_usuario";
        private static final String PASSWORD = "tu_contraseña";

        public static Connection getConnection() {
            Connection connection = null;
            try {
                // Cargar el controlador JDBC
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

                // Establecer la conexión
                connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            } catch (ClassNotFoundException e) {
                System.out.println("Error al cargar el controlador JDBC: " + e.getMessage());
            } catch (SQLException e) {
                System.out.println("Error al establecer la conexión: " + e.getMessage());
            }
            return connection;
        }*/
}