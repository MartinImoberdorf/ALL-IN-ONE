package com.app.MyAppBackend.controllers.link;

import com.app.MyAppBackend.model.entities.Link;
import com.app.MyAppBackend.services.link.LinkServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/link")
public class LinkController {

    @Autowired
    private LinkServiceImpl enlaceService;

    @GetMapping
    private List<Link> getAllEnlaces(){
        return enlaceService.getAllEnlaces();
    }

    @PostMapping
    private Link addEnlace(@RequestBody Link link){
        return enlaceService.addEnlace(link);
    }


}
