package bikeshop.service.impl;

import bikeshop.domain.entities.Component;
import bikeshop.domain.models.service.ComponentServiceModel;
import bikeshop.error.ComponentNotFoundException;
import bikeshop.repository.ComponentRepository;
import bikeshop.service.ComponentService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ComponentServiceTest {

    private Component component = new Component();

    private static final String VALID_TYPE = "Seat";
    private static final String VALID_DESCRIPTION = "Volt 406";
    private static final String NEW_DESCRIPTION = "Bolt 506";
    private static final String RANDOM_ID = "dadadada";

    @InjectMocks
    ComponentServiceImpl componentService;

    @Mock
    ComponentRepository componentRepository;

    @Mock
    ModelMapper mapper;

    @Before
    public void init() {
        ModelMapper actualMapper = new ModelMapper();

        component.setType(VALID_TYPE);
        component.setDescription(VALID_DESCRIPTION);

        when(mapper.map(any(ComponentServiceModel.class), eq(Component.class)))
                .thenAnswer(invocationOnMock ->
                        actualMapper.map(invocationOnMock.getArguments()[0], Component.class));

        when(mapper.map(any(Component.class), eq(ComponentServiceModel.class)))
                .thenAnswer(invocationOnMock ->
                        actualMapper.map(invocationOnMock.getArguments()[0], ComponentServiceModel.class));

        when(componentRepository.save(any())).thenAnswer(invocationOnMock -> invocationOnMock.getArguments()[0]);
    }


   @Test
    public void saveComponent_shouldSaveCorrectly_whenDontPersistInDBAlready(){
        //Arrange
        when(componentRepository.findByTypeAndDescription(any(), any()))
                .thenReturn(Optional.empty());

        ComponentServiceModel serviceModel = new ComponentServiceModel();
        serviceModel.setType(VALID_TYPE);
        serviceModel.setDescription(VALID_DESCRIPTION);

        //Act
        ComponentServiceModel result = componentService.saveComponent(serviceModel);

        //Assert
        verify(componentRepository).save(any());
        assertEquals(VALID_TYPE, result.getType());
    }

    @Test
    public void saveComponent_shouldReturnTheSameServiceModel_whenPersistInDB(){
        //Arrange
        when(componentRepository.findByTypeAndDescription(any(), any()))
                .thenReturn(Optional.ofNullable(component));

        ComponentServiceModel serviceModel = new ComponentServiceModel();
        serviceModel.setType(VALID_TYPE);
        serviceModel.setDescription(VALID_DESCRIPTION);

        //Act
        ComponentServiceModel result = componentService.saveComponent(serviceModel);

        //Assert
        assertEquals(VALID_TYPE, result.getType());
        assertEquals(VALID_DESCRIPTION, result.getDescription());
    }

    @Test
    public void editById_shouldEditTheDescription_whenIdIsCorrect(){
        //Arrange
        when(componentRepository.findById(any()))
                .thenReturn(Optional.of(component));

        //Act
        ComponentServiceModel result = componentService.editById(RANDOM_ID, NEW_DESCRIPTION);

        //Assert
        verify(componentRepository).save(any());
        assertEquals(NEW_DESCRIPTION, result.getDescription());
    }

    @Test(expected = ComponentNotFoundException.class)
    public void editById_shouldThrowException_whenIdIsInvalid(){
        //Arrange
        when(componentRepository.findById(any()))
                .thenReturn(Optional.empty());

        //Act
        componentService.editById(RANDOM_ID, NEW_DESCRIPTION);

    }
}
