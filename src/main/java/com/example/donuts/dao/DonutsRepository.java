package com.example.donuts.dao;

import com.example.donuts.model.Donuts;
import org.springframework.data.repository.CrudRepository;

public interface DonutsRepository extends CrudRepository<Donuts, Long> {

}
