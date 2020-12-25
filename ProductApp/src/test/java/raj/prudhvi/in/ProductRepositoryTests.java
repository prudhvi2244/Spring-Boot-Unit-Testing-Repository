package raj.prudhvi.in;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import raj.prudhvi.in.Product;
import raj.prudhvi.in.ProductRepository;

@DataJpaTest
//@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestMethodOrder(OrderAnnotation.class)
public class ProductRepositoryTests {

	@Autowired
	private ProductRepository repo;
	
	@Test
	@Rollback(false)
	@Order(1)
	public void testCreateProduct() {
		Product savedProduct = repo.save(new Product("iPhone 10", 789));
		
		assertThat(savedProduct.getId()).isGreaterThan(0);
	}
	
	@Test
	@Order(2)
	public void testFindProductByName() {
		Product product = repo.findByName("iPhone 10");		
		assertThat(product.getName()).isEqualTo("iPhone 10");
	}
	
	@Test
	@Order(3)
	public void testListProducts() {
		List<Product> products = (List<Product>) repo.findAll();		
		assertThat(products).size().isGreaterThan(0);
	}	
	
	@Test
	@Rollback(false)
	@Order(4)
	public void testUpdateProduct() {
		Product product = repo.findByName("iPhone 10");
		product.setPrice(1000);
		
		repo.save(product);
		
		Product updatedProduct = repo.findByName("iPhone 10");
		
		assertThat(updatedProduct.getPrice()).isEqualTo(1000);
	}
	
	@Test
	@Rollback(false)
	@Order(5)

	public void testDeleteProduct() {
		Product product = repo.findByName("iPhone 10");
		
		repo.deleteById(product.getId());
		
		Product deletedProduct = repo.findByName("iPhone 10");
		
		assertThat(deletedProduct).isNull();		
		
	}
}
