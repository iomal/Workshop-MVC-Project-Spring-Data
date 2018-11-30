package app.ccb.config.common.mappings;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {
    private static ModelMapper mapper;

    static {
        mapper = new ModelMapper();
        MappingsInitializer.initMappings(mapper);
        mapper.validate();
    }

    @Bean
    public ModelMapper modelMapper() {
        return mapper;
    }
}
