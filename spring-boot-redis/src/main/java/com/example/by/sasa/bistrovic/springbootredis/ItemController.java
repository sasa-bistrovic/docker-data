package com.example.by.sasa.bistrovic.springbootredis;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }
    
    @GetMapping
    public List<Item> getAllBooks() {
        return itemService.getAllItems();
    }

    @PostMapping
    public ResponseEntity<Item> createItem(@RequestBody Item item) {
        Item savedItem = itemService.saveItem(item);
        return new ResponseEntity<>(savedItem, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> getItem(@PathVariable String id) {
        Item item = itemService.getItemById(id);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateItem(@PathVariable String id, @RequestBody Item item) {
        item.setId(id);
        itemService.updateItem(item);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable String id) {
        itemService.deleteItem(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

