package com.informatorio.storeinformatorio.service.serviceImpl;

import com.informatorio.storeinformatorio.entity.Carrito;
import com.informatorio.storeinformatorio.entity.CarritoItem;
import com.informatorio.storeinformatorio.entity.Product;
import com.informatorio.storeinformatorio.entity.User;
import com.informatorio.storeinformatorio.repository.CarritoItemsRepository;
import com.informatorio.storeinformatorio.repository.CarritoRepository;
import com.informatorio.storeinformatorio.repository.ProductRepository;
import com.informatorio.storeinformatorio.repository.UserRepository;
import com.informatorio.storeinformatorio.service.CarritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CarritoServiceImpl implements CarritoService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private CarritoItemsRepository carritoItemsRepository;


    @Override
    public List<Carrito> getAllCarrito() {
        List<Carrito> alls = carritoRepository.findAll();
        for (Carrito i: alls){
            i.getItems().forEach( carritoItem -> {
                i.setTotal(carritoItem.getSubTotal());
            });
        }


        return alls;
    }

    public Carrito createCarrito(User user, String platform){

        Carrito carritoActive = new Carrito();
        carritoActive.setStatus(true);
        carritoActive.setClientPlatform("Mobile");
        carritoActive.setUserId(user);
        carritoActive.setCreatedAt(LocalDate.now());
        Carrito carritoActiveDB = carritoRepository.save(carritoActive);

        return carritoActiveDB;
    }

    public CarritoItem createCarritoItem(Product productDB, Carrito carritoActiveDB){
        CarritoItem itemActive = carritoItemsRepository.findByCarritoAndProduct(carritoActiveDB, productDB);
        if (itemActive != null){
            itemActive.setQuantity(1.);
            carritoItemsRepository.save(itemActive);
            return itemActive;
        }
        CarritoItem newCarritoItems = new CarritoItem();
        newCarritoItems.setProduct(productDB);
        newCarritoItems.setPrice(productDB.getPrice());
        newCarritoItems.setCarrito(carritoActiveDB);
        newCarritoItems.setQuantity(1.0);
        CarritoItem carritoItemDB = carritoItemsRepository.save(newCarritoItems);

        return carritoItemDB;
    }

    public Carrito totalPrice(Carrito carrito){
        List<CarritoItem> carritoItems = carrito.getItems();
        for (CarritoItem i: carritoItems){
            carrito.setTotal(i.getSubTotal());
        }
        return carrito;
    }

    @Override
    public Carrito addCarritoItem(Long idProduct, Long idUser) {
        User userDB = userRepository.findById(idUser).orElse(null);
        if (userDB == null){
            return null;
        }

        Carrito carritoActive = carritoRepository.findByUserIdAndStatusTrue(userDB);
        Product productDB = productRepository.findById(idProduct).orElse(null);

        if (productDB == null){
            return null;
        }
        if (carritoActive == null){

            Carrito carritoActiveDB = createCarrito(userDB, "Mobile");

            CarritoItem carritoItemDB = createCarritoItem(productDB, carritoActiveDB);

            carritoActiveDB.setItems(carritoItemDB);
            totalPrice(carritoActiveDB);
            carritoRepository.save(carritoActiveDB);

            return carritoActiveDB;
        }
        CarritoItem carritoItemDB = createCarritoItem(productDB, carritoActive);

        if (carritoItemDB.getQuantity() < 2){
            carritoActive.setItems(carritoItemDB);
        }
        totalPrice(carritoActive);
        carritoRepository.save(carritoActive);

        return carritoActive;
    }



}
