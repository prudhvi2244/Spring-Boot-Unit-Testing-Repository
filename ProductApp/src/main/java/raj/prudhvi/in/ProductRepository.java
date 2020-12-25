package raj.prudhvi.in;

import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Integer> {
	
	public Product findByName(String name);
}
