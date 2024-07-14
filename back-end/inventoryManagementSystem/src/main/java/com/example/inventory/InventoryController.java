package com.example.inventory;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    private com.example.inventory.Inventory inventory = new com.example.inventory.Inventory(100); // Initialize with a count of 100

    // GET API: Retrieve the current inventory count
    @GetMapping
    public int getInventoryCount() {
        return inventory.getCount();
    }

    // POST API: Update the inventory count by reducing it by one each time a purchase is made
    @PostMapping
    public String updateInventory() {
        if (inventory.getCount() > 0) {
            inventory.setCount(inventory.getCount() - 1);
            return "Inventory updated. Current count: " + inventory.getCount();
        } else {
            return "Inventory is already empty.";
        }
    }

    //PUT API: for testing purposes
    @PutMapping
    public String addInventory() {
        if (inventory.getCount() < 100) {
            inventory.setCount(inventory.getCount() + 1);
            return "Inventory updated. Current count: " + inventory.getCount();
        } else {
            return "Inventory is already full.";
        }
    }

}
