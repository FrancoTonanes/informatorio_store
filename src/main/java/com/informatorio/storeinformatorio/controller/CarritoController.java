package com.informatorio.storeinformatorio.controller;

import com.informatorio.storeinformatorio.entity.CarritoItem;
import com.informatorio.storeinformatorio.service.CarritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(CarritoController.URL)
public class CarritoController {
    public final static String URL = "api/v1/carrito";

    @Autowired
    private CarritoService carritoService;

    @GetMapping
    public ResponseEntity<?> getAllCarritos(){
        return ResponseEntity.ok(carritoService.getAllCarrito());
    }

    @PostMapping("{idUser}")
    public ResponseEntity<?> addItemCarrito(@PathVariable Long idUser,
                                            @RequestParam (name = "id_producto") Long idProducto){

        return new ResponseEntity<>(carritoService.addCarritoItem(idProducto, idUser), HttpStatus.OK);
    }
    @PutMapping({"idUser"})
    public ResponseEntity<?> putItemCarrito(@PathVariable Long idUser,
                                            @RequestParam (name = "id_producto") Long idProduct){

        return null;
    }

}
