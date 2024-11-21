package com.example.utazas;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface MessageRepo extends CrudRepository<Message, Integer>, PagingAndSortingRepository<Message, Integer> {
}
