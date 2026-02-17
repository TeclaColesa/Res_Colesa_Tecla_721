package org.example.repository;

import org.example.model.Supply;

public class SupplyRepository extends JsonRepository<Supply>{
    public SupplyRepository() {super("supplies.json", Supply.class);}
}
