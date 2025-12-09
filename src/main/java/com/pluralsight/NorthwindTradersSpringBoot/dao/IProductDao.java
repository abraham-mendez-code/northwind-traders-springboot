package com.pluralsight.NorthwindTradersSpringBoot.dao;

import com.pluralsight.NorthwindTradersSpringBoot.model.Product;

import java.util.List;

public interface IProductDao {

    void add(Product product);

    List<Product> getAll();

    void deleteByID(int productId);

    List<Product> findById(int productId);

    void update(Product product);
}
