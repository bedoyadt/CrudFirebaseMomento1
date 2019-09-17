package com.example.crudfirebasemomento1.models;

import java.io.Serializable;

public class ClienteModel implements Serializable {
    private String _id ;
    private String _cedula;
    private String _nombre;
    private String _apellido;

    public ClienteModel() {
    }

    public ClienteModel(String _id, String _cedula, String _nombre, String _apellido) {
        this._id = _id;
        this._cedula = _cedula;
        this._nombre = _nombre;
        this._apellido = _apellido;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String get_cedula() {
        return _cedula;
    }

    public void set_cedula(String _cedula) {
        this._cedula = _cedula;
    }

    public String get_nombre() {
        return _nombre;
    }

    public void set_nombre(String _nombre) {
        this._nombre = _nombre;
    }

    public String get_apellido() {
        return _apellido;
    }

    public void set_apellido(String _apellido) {
        this._apellido = _apellido;
    }
}
