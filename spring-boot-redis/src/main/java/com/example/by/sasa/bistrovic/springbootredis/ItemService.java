package com.example.by.sasa.bistrovic.springbootredis;

import java.util.Iterator;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }
    
    public List<Item> getAllItems() {
        return (List<Item>) itemRepository.findAll();
    }

    public Item saveItem(Item item) {
        return itemRepository.save(item);
    }

    public Item getItemById(String id) {
        return itemRepository.findById(id).orElse(null);
    }

    public void updateItem(Item item) {
        itemRepository.save(item);
    }

    public void deleteItem(String id) {
        itemRepository.deleteById(id);
    }
}

