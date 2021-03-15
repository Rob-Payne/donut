package com.example.donuts.controller;

import com.example.donuts.dao.DonutsRepository;
import com.example.donuts.model.Donuts;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class DonutsController {
    private final DonutsRepository donutsRepository;

    public DonutsController(DonutsRepository donutsRepository) {
        this.donutsRepository = donutsRepository;
    }

    @GetMapping("/donuts")
    public Iterable<Donuts> listDonuts(){
        return this.donutsRepository.findAll();
    }

    @GetMapping("/donuts/{id}")
    public Optional<Donuts> getDonutsById(@PathVariable Long id){
        return this.donutsRepository.findById(id);
    }

    @PostMapping("/donuts")
    public Donuts createDonuts(@RequestBody Donuts newDonuts){
        return this.donutsRepository.save(newDonuts);
    }

    @DeleteMapping("/donuts/{id}")
    public void deleteDonuts(@PathVariable Long id){
        this.donutsRepository.deleteById(id);
    }

    @PatchMapping("/donuts/{id}")
    public Donuts patchDonuts(@PathVariable Long id, @RequestBody Donuts newDonuts){

        Donuts oldDonuts = this.donutsRepository.findById(id).get();

        if(newDonuts.getName()!=null){
            oldDonuts.setName(newDonuts.getName());
        }
        if(newDonuts.getTopping()!=null){
            oldDonuts.setTopping(newDonuts.getTopping());
        }
        if(newDonuts.getExpiration()!=null){
            oldDonuts.setExpiration(newDonuts.getExpiration());
        }

        return this.donutsRepository.save(oldDonuts);
    }
}
