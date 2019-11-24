//package bikeshop.service.impl;
//
//
//import bikeshop.domain.entities.*;
//import bikeshop.domain.models.service.OrderServiceModel;
//import bikeshop.repository.OrderRepository;
//import bikeshop.repository.UserRepository;
//import bikeshop.service.OrderService;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//
//import static junit.framework.TestCase.assertEquals;
//import static org.junit.Assert.assertTrue;
//import static org.mockito.Mockito.when;
//
//@SpringBootTest
//@RunWith(SpringRunner.class)
//public class OrderServiceTests {
//    private static final String USERNAME = "Gabo";
//    private static final String ROLE_USER = "ROLE_USER";
//
//    private static final String BICYCLE_IMAGE_URL = "http://image.com";
//    private static final String BICYCLE_MAKE = "Oryx";
//    private static final String BICYCLE_MODEL = "c-18";
//    private static final BigDecimal BICYCLE_PRICE = BigDecimal.valueOf(100.67);
//
//    private static final int ORDER_QUANTITY = 2;
//    private static final BigDecimal ORDER_PRICE = BigDecimal.valueOf(200.67);
//
//
//    @Autowired
//    OrderService orderService;
//
//    @MockBean
//    OrderRepository orderRepository;
//
//    @MockBean
//    UserRepository userRepository;
//
//
//    private List<Order> orders;
//    private Order order;
//
//    @Before
//    public void setUpTest(){
//        orders = new ArrayList<>();
//        //Create order
//        order = new Order();
//        order.setUser(new User(){{
//            setUsername(USERNAME);
//            setFirstName("Test");
//            setLastName("Test");
//            setPassword("1");
//            setEmail("Test");
//            setAuthorities(new HashSet<Role>(){{
//                add(new Role(ROLE_USER));
//            }});
//        }});
//        order.setFinishedOn(LocalDateTime.now());
//        order.setBicycle(new Bicycle(){{
//            setMake(BICYCLE_MAKE);
//            setModel(BICYCLE_MODEL);
//            setDescription("Test");
//            setColor("Test");
//            setImageUrl(BICYCLE_IMAGE_URL);
//            setPrice(BICYCLE_PRICE);
//            setBicycleSize(new HashSet<BicycleSize>(){{
//                add(new BicycleSize(){{
//                    setName("Test");
//                }});
//            }});
//            setCategory(new Category(){{
//                setName("Test");
//            }});
//        }});
//        order.setTotalPrice(ORDER_PRICE);
//        order.setQuantity(ORDER_QUANTITY);
//        order.setBicycleSize(new BicycleSize(){{
//            setName("Test");
//        }});
//
//        when(orderRepository.findAll())
//                .thenReturn(orders);
//    }
//
//    @Test
//    public void findAllOrders_when1Orders_return1Orders(){
//        //Arrange
//        orders.add(order);
//
//        //Act
//        var result = orderService.findAllOrders();
//        OrderServiceModel orderResult = result.get(0);
//
//        //Assert
//        assertEquals(1, result.size());
//        assertEquals(USERNAME, orderResult.getUser().getUsername());
//        assertEquals(BICYCLE_IMAGE_URL, orderResult.getBicycle().getImageUrl());
//        assertEquals(BICYCLE_MAKE, orderResult.getBicycle().getMake());
//        assertEquals(BICYCLE_MODEL, orderResult.getBicycle().getModel());
//        assertEquals(BICYCLE_PRICE, orderResult.getBicycle().getPrice());
//        assertEquals(ORDER_QUANTITY, orderResult.getQuantity());
//        assertEquals(ORDER_PRICE, orderResult.getTotalPrice());
//    }
//
//    @Test
//    public void findAllOrders_whenNoOrders_returnEmptyOrders() {
//        orders.clear();
//        var result = orderService.findAllOrders();
//        assertTrue(result.isEmpty());
//    }
//}
