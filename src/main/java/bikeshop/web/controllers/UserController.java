package bikeshop.web.controllers;

import bikeshop.domain.models.binding.UserEditBindingModel;
import bikeshop.domain.models.binding.UserRegisterBindingModel;
import bikeshop.domain.models.service.RoleServiceModel;
import bikeshop.domain.models.service.UserServiceModel;
import bikeshop.domain.models.view.UserAllViewModel;
import bikeshop.domain.models.view.UserProfileViewModel;
import bikeshop.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/users")
public class UserController extends BaseController{

    private final UserService userService;
    private final ModelMapper mapper;

    @Autowired
    public UserController(UserService userService, ModelMapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }

    @GetMapping("/register")
    @PreAuthorize("isAnonymous()")
    public ModelAndView register(){
        return view("user/register");
    }

    @PostMapping("/register")
    @PreAuthorize("isAnonymous()")
    public ModelAndView registerConfirm(@ModelAttribute UserRegisterBindingModel model, RedirectAttributes redirectAttributes){
        if(this.passwordsNotMatch(model.getPassword(), model.getConfirmPassword())){
            this.setRedirectsOnRegisterForm(model, redirectAttributes);
            return redirect("/users/register");
        }
        UserServiceModel serviceModel = mapper.map(model, UserServiceModel.class);
        userService.registerUser(serviceModel);

        return redirect("/users/login");
    }

    @GetMapping("/login")
    @PreAuthorize("isAnonymous()")
    public ModelAndView login(){
        return view("user/login");
    }

    @GetMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView profile(Principal principal, ModelAndView modelAndView){
        UserServiceModel user = userService.findUserByUsername(principal.getName());
        UserProfileViewModel model = mapper.map(user, UserProfileViewModel.class);
        modelAndView.addObject("model", model);

        return view("user/profile", modelAndView);
    }

    @GetMapping("/edit")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView editProfile(Principal principal, ModelAndView modelAndView){
        UserServiceModel user = userService.findUserByUsername(principal.getName());
        UserProfileViewModel model = mapper.map(user, UserProfileViewModel.class);
        modelAndView.addObject("model", model);

        return view("user/edit-profile", modelAndView);
    }

    @PatchMapping("/edit")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView editProfileConfirm(@ModelAttribute UserEditBindingModel model, ModelAndView modelAndView){
        if (this.passwordsNotMatch(model.getPassword(), model.getConfirmPassword())){
            return redirect("/users/edit");
        }

        UserServiceModel serviceModel = mapper.map(model, UserServiceModel.class);
        userService.editUserProfile(serviceModel, model.getOldPassword());

        return redirect("/users/profile");
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView allUsers(ModelAndView modelAndView){
        List<UserAllViewModel> users = userService.findAll()
                .stream()
                .map(u -> {
                    UserAllViewModel user = mapper.map(u, UserAllViewModel.class);
                    Set<String> authorities = getAuthoritiesToString(u);
                    user.setAuthorities(authorities);
                    return user;
                })
                .collect(Collectors.toList());

        modelAndView.addObject("users", users);
        return view("user/all-users", modelAndView);
    }

    @PatchMapping("/set-user/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView setUser(@PathVariable String id) {
        userService.setUserRole(id, "user");

        return super.redirect("/users/all");
    }

    @PatchMapping("/set-moderator/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView setModerator(@PathVariable String id) {
        userService.setUserRole(id, "moderator");

        return super.redirect("/users/all");
    }

    @PatchMapping("/set-admin/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView setAdmin(@PathVariable String id) {
        userService.setUserRole(id, "admin");

        return super.redirect("/users/all");
    }

    private Set<String> getAuthoritiesToString(UserServiceModel userServiceModel) {
        return userServiceModel.getAuthorities()
                .stream()
                .map(RoleServiceModel::getAuthority)
                .collect(Collectors.toSet());
    }

    private boolean passwordsNotMatch(String password, String confirmPassword) {
        return !password.equals(confirmPassword);
    }

    private void setRedirectsOnRegisterForm(UserRegisterBindingModel model, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("username", model.getUsername());
        redirectAttributes.addFlashAttribute("firstName", model.getFirstName());
        redirectAttributes.addFlashAttribute("lastName", model.getLastName());
        redirectAttributes.addFlashAttribute("email", model.getEmail());
    }
}
