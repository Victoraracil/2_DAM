package com.accesoadatos.biblioteca_api.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

//@Controller ANTIGUO: ESPERA DEVOLVER UN ARCHIVO HTML
@RestController //ACTUAL: DEVUELVE DATOS
public class IndexController {
    // Las Siguientes lineas anotadas son equivalentes, usando @RequestMapping
    //o @GetMapping respectivamente
    // @RequestMapping(value = "/index", method = RequestMethod.GET)
// @RequestMapping(value = "/index", method = RequestMethod.GET)
// @GetMapping(value = "/index")
    @GetMapping("/index")
    public String index() {
        return "index";
    }
}


