package com.informatorio.storeinformatorio.service;

import com.informatorio.storeinformatorio.entity.Carrito;
import com.informatorio.storeinformatorio.entity.CarritoItem;

import java.util.List;

public interface CarritoService {
    List<Carrito> getAllCarrito();
    Carrito addCarritoItem(Long idProduct, Long idUser);
}
