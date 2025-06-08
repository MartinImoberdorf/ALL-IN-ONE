package com.app.MyAppBackend.controllers.link;

import com.app.MyAppBackend.model.dtos.addLinkDto;
import com.app.MyAppBackend.model.entities.Link;
import com.app.MyAppBackend.services.link.LinkServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/link")
@RequiredArgsConstructor
public class LinkController {

    private final LinkServiceImpl enlaceService;

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping
    private List<Link> getAllEnlaces(){
        return enlaceService.getAllEnlaces();
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<addLinkDto> addEnlace(@RequestBody Link link) {
       addLinkDto addLinkDto = enlaceService.addEnlace(link);
        return ResponseEntity.status(HttpStatus.CREATED).body(addLinkDto);
    }



}
