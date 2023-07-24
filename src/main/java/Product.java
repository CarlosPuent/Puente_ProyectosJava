package main.java;

public class Product
{
    private int IdProducto;
    private String NombreProducto;
    private int Existencia;
    private double Precio;

    public Product()
    {
        IdProducto = 0;
        NombreProducto = "";
        Existencia = 0;
        Precio = 0.0;
    }

    public Product(int Number, String Name, int Qty, double Price)
    {
        this.IdProducto = Number;
        this.NombreProducto = Name;
        this.Existencia = Qty;
        this.Precio = Price;
    }

    //Método get para IdProducto

    public int getIdProducto()
    {
        return IdProducto;
    }

    //Método set para IdProducto

    public void setIdProducto(int idProducto)
    {
        this.IdProducto = idProducto;
    }

    //Método get para NombreProducto

    public String getNombreProducto()
    {
        return NombreProducto;
    }

    //Método set para IdProducto

    public void setNombreProducto(String nombreProducto)
    {
        this.NombreProducto = nombreProducto;
    }

    //Método get para Existencia

    public int getExistencia()
    {
        return Existencia;
    }

    //Método set para Existencia

    public void setExistencia(int existencia)
    {
        this.Existencia = existencia;
    }

    //Método get para Precio

    public double getPrecio()
    {
        return Precio;
    }

    //Método set para Precio

    public void setPrecio(double precio)
    {
        this.Precio = precio;
    }

}
