package app.ccb.config.common.mappings;

import org.modelmapper.ModelMapper;

public interface IHaveCustomMappings {
    void configureMappings(ModelMapper mapper);
}
