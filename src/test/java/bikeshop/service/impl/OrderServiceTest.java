package bikeshop.service.impl;

import bikeshop.domain.entities.*;
import bikeshop.domain.models.service.*;
import bikeshop.error.BicycleSizeNotFoundException;
import bikeshop.error.OrderNotFoundException;
import bikeshop.repository.BicycleSizeRepository;
import bikeshop.repository.OrderItemRepository;
import bikeshop.repository.OrderRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceTest {

    private OrderServiceModel orderServiceModel = new OrderServiceModel();
    private OrderItemServiceModel orderItemServiceModel = new OrderItemServiceModel();
    private BicycleServiceModel bicycleServiceModel = new BicycleServiceModel();
    private BicycleSize bicycleSize = new BicycleSize();
    private OrderItem orderItem = new OrderItem();


    private static final String RANDOM_ID = "abuudfAF";
    private static final String RANDOM_NAME = "Gosho";
    private static final String CATEGORY_NAME = "MOUNTAIN";

    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private BicycleSizeRepository bicycleSizeRepository;

    @Mock
    private OrderItemRepository orderItemRepository;

    @Mock
    private ModelMapper modelMapper;


    @Before
    public void init() {
        ModelMapper mapper = new ModelMapper();

        when(modelMapper.map(any(Order.class), eq(OrderServiceModel.class)))
                .thenAnswer(invocationOnMock ->
                        mapper.map(invocationOnMock.getArguments()[0], OrderServiceModel.class));

        when(modelMapper.map(any(OrderServiceModel.class), eq(Order.class)))
                .thenAnswer(invocationOnMock ->
                        mapper.map(invocationOnMock.getArguments()[0], Order.class));

        when(orderRepository.save(any())).thenAnswer(invocationOnMock -> invocationOnMock.getArguments()[0]);

        when(orderItemRepository.save(any()))
                .thenAnswer(invocationOnMock -> {
                    orderItem =(OrderItem) invocationOnMock.getArguments()[0];
                    orderItem.setId(RANDOM_ID);
                    return orderItem;
                });

        bicycleServiceModel.setId(RANDOM_ID);

        bicycleSize.setName("L");
        bicycleSize.setId(RANDOM_ID);

        orderItemServiceModel.setId(RANDOM_ID);
        orderItemServiceModel.setBicycleSize("L");
        orderItemServiceModel.setBicycle(bicycleServiceModel);
        orderItemServiceModel.setPrice(BigDecimal.valueOf(400));
        orderItemServiceModel.setQuantity(2);

        List<OrderItemServiceModel> temp = new ArrayList<>();
        temp.add(orderItemServiceModel);

        orderServiceModel.setId(RANDOM_ID);
        orderServiceModel.setBicycles(temp);
        orderServiceModel.setTotalPrice(BigDecimal.valueOf(800));
        orderServiceModel.setUser(new UserServiceModel());
    }


    @Test
    public void createOrder_shouldCreateOrderCorrectly(){
        //Arrange
        when(bicycleSizeRepository.findByName(any()))
                .thenReturn(Optional.of(bicycleSize));

        //Act
        orderService.createOrder(orderServiceModel);

        //Assert
        verify(orderRepository).save(any());
    }

    @Test(expected = BicycleSizeNotFoundException.class)
    public void createOrder_shouldThrowException_whenBicycleSizeDontExist(){
        //Arrange
        when(bicycleSizeRepository.findByName(any()))
                .thenReturn(Optional.empty());

        //Act
        orderService.createOrder(orderServiceModel);
    }

    @Test
    public void findAllOrders_shouldReturnCorrectly(){
        //Arrange
        when(orderRepository.findAll())
                .thenReturn(List.of(new Order()));

        //Act
        List<OrderServiceModel> allOrders = orderService.findAllOrders();

        //Assert
        assertEquals(1, allOrders.size());
    }

    @Test
    public void findAllOrders_shouldReturnEmptyCollection_whenNoOrdersInDB(){
        //Arrange
        when(orderRepository.findAll())
                .thenReturn(new ArrayList<>());

        //Act
        List<OrderServiceModel> allOrders = orderService.findAllOrders();

        //Assert
        assertEquals(0, allOrders.size());
    }

    @Test
    public void findByCustomerName_shouldReturnCollectionOfOrders_whenNameMatch(){
        //Arrange
        when(orderRepository.findAllByUserUsername(any()))
                .thenReturn(List.of(new Order()));

        //Act
        List<OrderServiceModel> allOrders = orderService.findByCustomerName(RANDOM_NAME);

        //Assert
        assertEquals(1, allOrders.size());
    }

    @Test
    public void findByCustomerName_shouldReturnEmptyCollectionOfOrders_whenNameDontMatch(){
        //Arrange
        when(orderRepository.findAllByUserUsername(any()))
                .thenReturn(new ArrayList<>());

        //Act
        List<OrderServiceModel> allOrders = orderService.findByCustomerName(RANDOM_NAME);

        //Assert
        assertEquals(0, allOrders.size());
    }

    @Test
    public void findOrderById_shouldReturnOrder_whenIdMatch(){
        //Arrange
        orderItem.setId(RANDOM_ID);

        Bicycle bicycle = new Bicycle();
        bicycle.setId(RANDOM_ID);

        Category category = new Category();
        category.setId(RANDOM_ID);
        category.setName(CATEGORY_NAME);
        bicycle.setCategory(category);

        Set sizes = new HashSet<>();
        sizes.add(bicycleSize);
        bicycle.setBicycleSize(sizes);
        orderItem.setBicycle(bicycle);
        orderItem.setBicycleSize(bicycleSize);

        List<OrderItem> temp1 = new ArrayList<>();
        temp1.add(orderItem);

        Order order = new Order();
        order.setId(RANDOM_ID);
        order.setBicycles(temp1);


        when(orderRepository.findById(RANDOM_ID))
                .thenReturn(Optional.of(order));

        //Act
        OrderServiceModel orderById = orderService.findOrderById(RANDOM_ID);

        //Assert
        String id = orderById.getId();
        assertEquals(RANDOM_ID, id);
    }

    @Test(expected = OrderNotFoundException.class)
    public void findOrderById_shouldThrowException_whenIdDontMatch(){
        //Arrange
        when(orderRepository.findById(any()))
                .thenReturn(Optional.empty());

        //Act
        OrderServiceModel orderById = orderService.findOrderById(RANDOM_ID);
    }
}
