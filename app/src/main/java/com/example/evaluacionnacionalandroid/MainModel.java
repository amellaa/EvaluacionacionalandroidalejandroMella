package com.example.evaluacionnacionalandroid;

public class MainModel {
    String Producto, Precio, Codigo, imgURL;

    public MainModel() {
    }

    public MainModel(String Producto, String Precio, String Codigo, String imgURL) {
        this.Producto = Producto;
        this.Precio = Precio;
        this.Codigo = Codigo;
        this.imgURL = imgURL;
    }

    public String getProducto() {
        return Producto;
    }

    public void setProducto(String Producto) {
        this.Producto = Producto;
    }

    public String getPrecio() {
        return Precio;
    }

    public void setPrecio(String Precio) {
        this.Precio = Precio;
    }

    public String getCodigo() {
        return Codigo;
    }

    public void setCodigo(String Codigo) {
        this.Codigo = Codigo;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }
}
