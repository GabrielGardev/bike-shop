package bikeshop.web.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController extends BaseController {

    @GetMapping("/")
    @PreAuthorize("isAnonymous()")
    public ModelAndView index(){
        return view("index");
    }

    @GetMapping("/home")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView home(){
        return view("home");
    }

//    @GetMapping("/**")
//    @PreAuthorize("isAuthenticated()")
//    public ModelAndView categoriesInNavBar(ModelAndView modelAndView){
//        List<CategoryViewModel> categories = categoryService.findAllCategories()
//                .stream()
//                .map(c -> mapper.map(c, CategoryViewModel.class))
//                .collect(Collectors.toList());
//
//        modelAndView.addObject("categories", categories);
//        return modelAndView;
//    }
}
