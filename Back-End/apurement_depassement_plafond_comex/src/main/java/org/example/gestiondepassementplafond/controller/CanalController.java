package org.example.gestiondepassementplafond.controller;

import org.example.gestiondepassementplafond.modeles.Canal;
import org.example.gestiondepassementplafond.services.canal.CanalService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/canal")
public class CanalController {

    public final CanalService canalService;

    public CanalController(CanalService canalService) {
        this.canalService = canalService;
    }

    @PostMapping("/save")
    public Mono<Canal> createCanal(Canal canal){
       return canalService.saveCanal(canal);
    }

    @GetMapping("/{id}")
    public Mono<Canal> getCanalById(Long id){
        return canalService.getCanalById(id);
    }

    public Flux<Canal> getAllCanal(){
        return canalService.getCanals();
    }

    public Mono<Canal> updateCanal(Canal canal, Long id){
        return canalService.updateCanal(canal, id);
    }

}
