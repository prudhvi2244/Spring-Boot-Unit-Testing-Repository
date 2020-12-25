SpringBoot Starter Test:
======================================

	<dependency>
	    <groupId>org.springframework.boot</groupId>
	    <artifactId>spring-boot-starter-test</artifactId>
	    <scope>test</scope>
	</dependency>

-----------------------------------------------------------------------

Note :
========
By Default Spring Boot Will Provide Following Libraries

   1)Junit : Standard for Unit Testing Java Applications

   2)Spring Test and Spring Boot Test : Utilities and Integration test support for Spring Boot Apps

   3)AsserJ : AssertJ provides a set of classes and utility methods that allow us to write fluent
              and beautiful assertions easily for:

		* Standard Java
		* Java 8
		* Guava
		* Joda Time
		* Neo4J and
		* Swing components

    4)Hamcrest : A Library of Matcher Objects( also known as contraints or predicates)

    5)Mockito :  A Java Mocking framework

    6)JsonAssert : An Assertion Library for json

    7)JsonPath : Xpath for json

  

Annotations for Unit Testing Spring Data JPA
=====================================================
* When coding the data access layer, you can test only the Spring Data JPA repositories without
  testing a fully loaded Spring Boot application.


* Spring Boot provides the following annotations which you can use for unit testing JPA repositories




@RunWith(SpringRunner.class):
==================================
* @RunWith(SpringRunner.class) provides a bridge between Spring Boot test features and JUnit.
  Whenever we are using any Spring Boot testing features in our JUnit tests, this annotation will be
  required.

@DataJpaTest:
====================
* This annotation will disable full auto-configuration and instead apply only configuration relevant 
  to JPA tests. By default, it will use an embedded, in-memory H2 database instead of the one 
  declared in the configuration file, for faster test running time as compared to disk file database.

* @DataJpaTest provides some standard setup needed for testing the persistence layer:

	* configuring H2, an in-memory database
	* setting Hibernate, Spring Data, and the DataSource
	* performing an @EntityScan
	* turning on SQL logging


TestEntityManager:
==================-===
* To carry out DB operations, we need some records already in our database. 
  To setup this data, we can use TestEntityManager.

* The Spring Boot TestEntityManager is an alternative to the standard JPA EntityManager that 
  provides methods commonly used when writing tests.



@DataJpaTest

public class ProductRepositoryTests

{

    @Autowired

    private TestEntityManager entityManager;
     
    @Autowired

    private ProductRepository repository;
     
    @Test

    public void testSaveNewProduct()

    {

        entityManager.persist(new Product("iPhone 10", 1099));
                 
        Product product = repository.findByName("iPhone 10");
         
        assertThat(product.getName()).isEqualTo("iPhone 10");

    }

}

========================================================================================================

As you can see, in this test class we can inject a TestEntityManager and ProductRepository. 
TestEntityManager is a subset of JPA EntityManager. It allows us to quickly test JPA without the 
need to manually configure/instantiating an EntityManagerFactory and EntityManager.


And ProductRepository is the repository that need to be tested,along with the entity class Product. 
The repository proxy class generated by Spring Data JPA is very well implemented, 
so it’s for testing the entity classes and custom methods in repository interfaces.


@Rollback:
==================
* Note that by default, tests using @DataJpaTest are transactional and roll back at the end of each 
  test method. If you want to disable auto rollback for the whole test class, annotate the class with
  the  @Rollback annotation


====================================================

@DataJpaTest

@Rollback(false)

public class ProductRepositoryTests
{

    ...

}

====================================================

@DataJpaTest

public class ProductRepositoryTests 
{

    @Autowired

    private TestEntityManager entityManager;
     
    @Autowired

    private ProductRepository repository;
     
    @Test

    @Rollback(false)

    public void testSaveNewProduct()
    {

        ...

    }
     
    @Test  

    public void testUpdateProduct()
    {

        ...

    }
}

=======================================================



@AutoConfigureTestDatabase:
=========================================================
* By default, the @DataJpaTest annotation replaces the declared database configuration by an 
  in-memory database (H2), which is useful when running tests that doesn’t touch real database. 

* When you want to run tests on real database, use the @AutoConfigureTestDatabase

===========================================================================================

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class ProductRepositoryTests {
    ...
}

===========================================================================================

* Then Spring Boot will use the data source declared in the application configuration file.

application.properties
-----------------------------

spring.datasource.url=jdbc:mysql://localhost:3306/company

spring.datasource.username=root

spring.datasource.password=root1

spring.jpa.hibernate.ddl-auto=update

spring.jpa.show-sql=true

spring.jpa.properties.hibernate.format-sql=true





------------------------------------------------------------------------------
class Product

{

private Integer pid;

private String pname;

private Double price;

}

-------------------------------------------------------------------------------

public interface ProductRepository extends CrudRepository<Product, Integer>

{
     
    public Product findByName(String name);
}

--------------------------------------------------------------------------------

Test Create operation:
------------------------------------------
@Test

public void testCreateProduct() 

{

    Product savedProduct = repo.save(new Product("iPhone 10", 789));
    
    assertThat(savedProduct.getId()).isGreaterThan(0);
}


Test Retrieval Operation:
==========================================

@Test

public void testFindProductByName()

{

    Product product = repo.findByName("iPhone 10"); 
    
    assertThat(product.getName()).isEqualTo("iPhone 10");

}


@Test

public void testListProducts()

{

    List<Product> products = (List<Product>) repo.findAll();

    assertThat(products).size().isGreaterThan(0);
}


Test Update Operation:
============================================

@Test

@Rollback(false)

public void testUpdateProduct() 

{

    Product product = repo.findByName("iPhone 10");

    product.setPrice(1000);
     
    repo.save(product);
     
    Product updatedProduct = repo.findByName("iPhone 10");
     
    assertThat(updatedProduct.getPrice()).isEqualTo(1000);
}


========================================================================


Test Delete Operation:

--------------------------------------------------------

@Test

@Rollback(false)

public void testDeleteProduct() 

{

    Product product = repo.findByName("iPhone 10");
     
    repo.deleteById(product.getId());
     
    Product deletedProduct = repo.findByName("iPhone 10");
     
    assertThat(deletedProduct).isNull();       
     
}

============================================================================


@DataJpaTest

@AutoConfigureTestDatabase(replace = Replace.NONE)

@TestMethodOrder(OrderAnnotation.class)

public class ProductRepositoryTests

{
 
    @Autowired

    private ProductRepository repo;
     
    @Test

    @Rollback(false)

    @Order(1)

    public void testCreateProduct() 

    {

        ...

    }
     
    @Test

    @Order(2)

    public void testFindProductByName()

    {

        ...

    }
     
    @Test

    @Order(3)

    public void testListProducts() 

    {

        ...

    }
     
    @Test

    @Rollback(false)

    @Order(4)

    public void testUpdateProduct() 

    {

        ...

    }
     
    @Test

    @Rollback(false)

    @Order(5)

    public void testDeleteProduct() 
    {

        ...

    }

}


======================================================================================

Thank You For Reading : All The Best
















