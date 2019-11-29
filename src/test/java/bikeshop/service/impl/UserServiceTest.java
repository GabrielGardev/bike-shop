package bikeshop.service.impl;

import bikeshop.domain.entities.Role;
import bikeshop.domain.entities.User;
import bikeshop.domain.models.service.RoleServiceModel;
import bikeshop.domain.models.service.UserServiceModel;
import bikeshop.error.EmailAlreadyExistException;
import bikeshop.error.UsernameAlreadyExistException;
import bikeshop.repository.UserRepository;
import bikeshop.service.RoleService;
import bikeshop.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    private static final UserServiceModel USER_SERVICE_MODEL = new UserServiceModel();
    private static final User INVALID_MODEL = new User();


    private static final String VALID_USERNAME = "gosho";
    private static final String VALID_FIRST_NAME = "Gosho";
    private static final String VALID_LAST_NAME = "Goshev";
    private static final String VALID_EMAIL = "gosho@gkozel.com";
    private static final String VALID_PASSWORD = "1111";

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleService roleService;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private BCryptPasswordEncoder encoder;


    @Before
    public void init() {
        ModelMapper actualMapper = new ModelMapper();
        BCryptPasswordEncoder actualEncoder = new BCryptPasswordEncoder();

        when(modelMapper.map(any(UserServiceModel.class), eq(User.class)))
                .thenAnswer(invocationOnMock ->
                        actualMapper.map(invocationOnMock.getArguments()[0], User.class));

//        when(modelMapper.map(any(User.class), eq(UserServiceModel.class)))
//                .thenAnswer(invocationOnMock ->
//                        actualMapper.map(invocationOnMock.getArguments()[0], UserServiceModel.class));

//        when(roleService.findByAuthority(anyString()))
//                .thenAnswer(invocationOnMock -> actualMapper.map(new Role((String) invocationOnMock.getArguments()[0]),
//                        RoleServiceModel.class));

        when(encoder.encode(any())).thenAnswer(invocationOnMock -> actualEncoder.encode((CharSequence) invocationOnMock.getArguments()[0]));
//        when(encoder.matches(any(), any())).thenAnswer(invocationOnMock -> actualEncoder.matches((String) invocationOnMock.getArguments()[0], (String) invocationOnMock.getArguments()[1]));
        when(userRepository.save(any())).thenAnswer(invocationOnMock -> invocationOnMock.getArguments()[0]);

        USER_SERVICE_MODEL.setUsername(VALID_USERNAME);
        USER_SERVICE_MODEL.setFirstName(VALID_FIRST_NAME);
        USER_SERVICE_MODEL.setLastName(VALID_LAST_NAME);
        USER_SERVICE_MODEL.setEmail(VALID_EMAIL);
        USER_SERVICE_MODEL.setPassword(VALID_PASSWORD);
    }

    @Test
    public void registerUser_shouldSuccessfullyRegisterUser_whenValidUser(){
        //Act
        userService.registerUser(USER_SERVICE_MODEL);

        //Assert
        verify(userRepository).save(any());
    }

    @Test(expected = UsernameAlreadyExistException.class)
    public void registerUser_shouldThrowException_whenUserIsDuplicateByUsername(){
        //Arrange
        INVALID_MODEL.setUsername(VALID_USERNAME);
        when(userRepository.findByUsername(USER_SERVICE_MODEL.getUsername())).thenReturn(Optional.of(INVALID_MODEL));

        //Act
        userService.registerUser(USER_SERVICE_MODEL);
    }

    @Test(expected = EmailAlreadyExistException.class)
    public void registerUser_shouldThrowException_whenUserIsDuplicateByEmail(){
        //Arrange
        INVALID_MODEL.setEmail(VALID_EMAIL);
        when(userRepository.findByEmail(USER_SERVICE_MODEL.getEmail())).thenReturn(Optional.of(INVALID_MODEL));

        //Act
        userService.registerUser(USER_SERVICE_MODEL);
    }
}
