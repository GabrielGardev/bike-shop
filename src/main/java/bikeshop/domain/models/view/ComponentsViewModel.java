package bikeshop.domain.models.view;

import bikeshop.domain.models.binding.ComponentEditBindingModel;

import java.util.Set;

public class ComponentsViewModel {

    private Set<ComponentEditBindingModel> components;

    public Set<ComponentEditBindingModel> getComponents() {
        return components;
    }

    public void setComponents(Set<ComponentEditBindingModel> components) {
        this.components = components;
    }
}
