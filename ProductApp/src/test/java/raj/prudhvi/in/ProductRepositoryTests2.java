package raj.prudhvi.in;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.hamcrest.MatcherAssert.assertThat;
import org.hamcrest.collection.IsEmptyCollection;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

@DataJpaTest
@RunWith(SpringRunner.class)
//@AutoConfigureTestDatabase(replace = Replace.NONE)
//@Rollback(false)
public class ProductRepositoryTests2 {
	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private ProductRepository repository;

	@BeforeEach
	public void setUp() {
		entityManager.persist(new Product("Sony", 1099));
		entityManager.persist(new Product("Samsung", 1099));
		entityManager.persist(new Product("Nokia", 1099));
		entityManager.persist(new Product("iPhone 10", 11099));
		entityManager.persist(new Product("LG", 1099));
	}

	@Test
	public void testSaveNewProduct() {

		Product product = repository.findByName("iPhone 10");
		assertNotNull(product);
		assertThat(product.getName()).isEqualTo("iPhone 10");

	}

	@Test
	public void testGetAllProducts() {
		List<Product> products = (List<Product>) repository.findAll();
		assertThat(products, hasSize(5));
		assertThat(products.size(),is(5));

	}

}
