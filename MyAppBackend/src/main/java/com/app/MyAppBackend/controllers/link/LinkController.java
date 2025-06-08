package com.app.MyAppBackend.controllers.link;

import com.app.MyAppBackend.model.entities.Link;
import com.app.MyAppBackend.services.link.LinkServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/link")
public class LinkController {

    @Autowired
    private LinkServiceImpl enlaceService;

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping
    private List<Link> getAllEnlaces(){
        return enlaceService.getAllEnlaces();
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PostMapping
    private Link addEnlace(@RequestBody Link link){
        return enlaceService.addEnlace(link);
    }


}
