package app.ccb.config.common.util;

import org.springframework.stereotype.Component;

import javax.validation.Validation;
import javax.validation.Validator;

@Component
public class ValidationUtilImpl implements ValidationUtil {

    private final Validator validator = Validation
            .byDefaultProvider()
            .configure()
            .buildValidatorFactory()
            .getValidator();

    @Override
    public <E> boolean isValid(E entity) {
        return validator.validate(entity).size()==0;
    }
}
