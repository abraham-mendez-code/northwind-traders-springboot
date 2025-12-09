package com.pluralsight.NorthwindTradersSpringBoot;

import com.pluralsight.NorthwindTradersSpringBoot.dao.IProductDao;
import com.pluralsight.NorthwindTradersSpringBoot.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class NorthwindApplication implements CommandLineRunner {

    // ask spring to inject a IProductDao here
    // this is an example of dependency injection and inversion of control (DI, IC)
    @Autowired
    @Qualifier("jdbcProductDao")
    private IProductDao productDao;

    @Override
    public void run(String... args) throws Exception {
        // Create a Scanner object so we can read user input from the console.
        Scanner scanner = new Scanner(System.in);

        // Create a String object so we can store the menu options.
        String message = """
                === Product Admin Menu ===
                1) List All Products
                2) Add A Product
                3) Delete A Product
                4) Search For Product
                5) Update A Product
                0) Exit
                Enter Your Selection:\s""";

        // This is a "loop" that will keep showing the menu until the user chooses to exit.
        while (true) {
            // Print the menu options to the screen.
            System.out.print(message);

            // Read the user's choice as a String.
            String selection = scanner.nextLine();

            // Use a "switch" to handle each possible choice.
            switch (selection) {

                case "1":
                    // The user chose option 1 → List all products.

                    // Call the DAO to get a list of all products.
                    List<Product> products = productDao.getAll();

                    // Print the products to the screen.
                    System.out.println("\nProducts:");
                    products.forEach(System.out::println);

                    break;

                case "2":
                    // The user chose option 2 → Add a new product.

                    // Ask the user for the product's name.
                    System.out.print("Enter product name: ");
                    String productName = scanner.nextLine();


                    // Ask the user for the product's category id.
                    System.out.print("Enter the product category id: ");
                    int categoryID = Integer.parseInt(scanner.nextLine());

                    // Ask the user for the product's unit price.
                    System.out.print("Enter product unit price: ");
                    double unitPrice = Double.parseDouble(scanner.nextLine());


                    // Create a new Product object and set its data.
                    Product product = new Product();
                    product.setProductName(productName);
                    product.setCategoryId(categoryID);
                    product.setUnitPrice(unitPrice);

                    // Add the new product to the DAO (which stores it in memory).
                    productDao.add(product);

                    // Let the user know that the product was added.
                    System.out.println("Product added successfully.");

                    break;

                case "3":
                    // The user chose option 3 → Delete a product.

                    // Ask the user for the product's id
                    System.out.print("Enter the product id: ");
                    int productId = Integer.parseInt(scanner.nextLine());

                    // Delete the product from DAO (which removes it from the DB).
                    productDao.deleteByID(productId);

                    // Let the user know that the product was deleted.
                    System.out.println("Product deleted successfully.");

                    break;

                case "4":
                    // The user chose option 4 → Search for a product.

                    // Ask the user for the product's id
                    System.out.print("Enter the product id: ");
                    int searchProductId = Integer.parseInt(scanner.nextLine());

                    // Call the DAO to get a list of matching products
                    List<Product> searchProducts = productDao.findById(searchProductId);

                    if (searchProducts.isEmpty()) {
                        System.out.println("\nProducts: ");
                        System.out.println("No results.");
                    } else {
                        // Print the products to the screen.
                        System.out.println("\nProducts:");
                        searchProducts.forEach(System.out::println);
                    }

                    break;

                case "5":
                    // The user chose option 5 → Update a product.

                    // Ask the user for the product's id.
                    System.out.print("Enter product id: ");
                    int updateProductId = Integer.parseInt(scanner.nextLine());

                    // Ask the user for the product's name.
                    System.out.print("Enter product name: ");
                    String updateProductName = scanner.nextLine();

                    // Ask the user for the product's category id.
                    System.out.print("Enter the product category id: ");
                    int updateCategoryID = Integer.parseInt(scanner.nextLine());

                    // Ask the user for the product's unit price.
                    System.out.print("Enter product unit price: ");
                    double updateUnitPrice = Double.parseDouble(scanner.nextLine());

                    // Create a new Product object and set its data.
                    Product updateProduct = new Product();
                    updateProduct.setProductId(updateProductId);
                    updateProduct.setProductName(updateProductName);
                    updateProduct.setCategoryId(updateCategoryID);
                    updateProduct.setUnitPrice(updateUnitPrice);

                    // Update the product
                    productDao.update(updateProduct);

                    // Let the user know that the product was updated.
                    System.out.println("Product updated successfully.");

                    break;

                case "0":
                    // The user chose option 0 → Exit the program.

                    // Print a goodbye message.
                    System.out.println("Goodbye!");

                    // End the program with a success status (0).
                    System.exit(0);

                default:
                    // The user entered something that is not a valid option.
                    // Tell the user the input was invalid and show the menu again.
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }
}
