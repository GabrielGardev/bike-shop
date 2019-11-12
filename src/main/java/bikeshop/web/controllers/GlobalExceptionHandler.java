package bikeshop.web.controllers;

import bikeshop.error.*;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.ValidationException;

@ControllerAdvice
public class GlobalExceptionHandler  {
    private static final String EXCEPTION_VIEW_ROUTE = "/error";
    private static final String ERROR_PAGE_STATUS_CODE_ATTRIBUTE_NAME = "statusCode";
    private static final String ERROR_PAGE_MESSAGE_ATTRIBUTE_NAME = "message";

    @ExceptionHandler(Throwable.class)
    public ModelAndView handleGlobalException(Throwable e) {
        e.printStackTrace();
        return fillModelAndView(Integer.parseInt(e.getCause().getMessage()), e.getMessage());
    }

    @ExceptionHandler({BicycleNotFoundException.class,
            UserNotFoundException.class,
            AuthorityNotFoundException.class,
            BicycleSizeNotFoundException.class,
            CategoryNotFoundException.class,
            ComponentNotFoundException.class,
            PasswordDontMatchException.class})
    public ModelAndView handleNotFoundExceptions(BaseException e) {
        e.printStackTrace();
        return fillModelAndView(e.getStatus(), e.getMessage());
    }

    @ExceptionHandler({UsernameAlreadyExistException.class,
            EmailAlreadyExistException.class,
            BicycleAlreadyExistException.class,
            CategoryAlreadyExistException.class,
            BicycleSizeAlreadyExistException.class})
    public ModelAndView handleAlreadyExistExceptions(BaseException e) {
        e.printStackTrace();
        return fillModelAndView(e.getStatus(), e.getMessage());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ModelAndView handleUsernameNotFoundException(UsernameNotFoundException e) {
        e.printStackTrace();
        return fillModelAndView(Integer.parseInt(e.getCause().getMessage()), e.getMessage());
    }

    private ModelAndView fillModelAndView(int statusCode, String message) {
        ModelAndView modelAndView = new ModelAndView(EXCEPTION_VIEW_ROUTE);
        modelAndView.addObject(ERROR_PAGE_STATUS_CODE_ATTRIBUTE_NAME, statusCode);
        modelAndView.addObject(ERROR_PAGE_MESSAGE_ATTRIBUTE_NAME, message);

        return modelAndView;
    }
}
