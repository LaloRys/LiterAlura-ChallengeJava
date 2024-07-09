package com.alura.bookmatch_app.service;

public interface IConvierteDatos {
    //    <T> T Dato generico, al no saber que retornaremos
    <T> T obtenerDatos(String json, Class<T> clase);
}
