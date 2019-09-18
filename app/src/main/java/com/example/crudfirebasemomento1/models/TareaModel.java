package com.example.crudfirebasemomento1.models;

import java.io.Serializable;

public class TareaModel  implements Serializable {
    private String _id ;
    private String nino;
    private String materia;
    private String tarea;
    private String descrcion;
    private String cocente;

    public TareaModel() {
    }

    public TareaModel(String _id, String nino, String materia, String tarea, String descrcion, String cocente) {
        this._id = _id;
        this.nino = nino;
        this.materia = materia;
        this.tarea = tarea;
        this.descrcion = descrcion;
        this.cocente = cocente;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getNino() {
        return nino;
    }

    public void setNino(String nino) {
        this.nino = nino;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public String getTarea() {
        return tarea;
    }

    public void setTarea(String tarea) {
        this.tarea = tarea;
    }

    public String getDescrcion() {
        return descrcion;
    }

    public void setDescrcion(String descrcion) {
        this.descrcion = descrcion;
    }

    public String getCocente() {
        return cocente;
    }

    public void setCocente(String cocente) {
        this.cocente = cocente;
    }}