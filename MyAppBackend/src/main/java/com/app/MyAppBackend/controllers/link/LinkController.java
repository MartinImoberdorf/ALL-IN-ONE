package com.app.MyAppBackend.controllers.link;

import com.app.MyAppBackend.model.dtos.LinkDto;
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

    private final LinkServiceImpl linkService;

    // get all links
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    private List<LinkDto> getAllLinks(){
        return linkService.getAllLinks();
    }

    // add link
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<LinkDto> addLink(@RequestBody Link link) {
       LinkDto LinkDto = linkService.addLink(link);
        return ResponseEntity.status(HttpStatus.CREATED).body(LinkDto);
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<LinkDto> removeLink(@PathVariable Long id) {
        LinkDto deletedLink = linkService.removeLink(id);
        return ResponseEntity.ok(deletedLink);
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping()
    private List<LinkDto> getLinksActualUser(){
        return linkService.getLinksActualUser();
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PatchMapping()
    public ResponseEntity<LinkDto> updateLink(@RequestBody LinkDto link){
        LinkDto linkDto = linkService.updateLink(link);
        return ResponseEntity.ok(linkDto);
    }
}
