package com.pusilkom.ess.validator.form.cmd;

import com.pusilkom.ess.dto.form.cmd.InventoryCmd;
import com.pusilkom.ess.util.ValidatorUtil;
import com.pusilkom.ess.util.ValidatorWebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class InventoryCmdValidator implements Validator {
    @Autowired
    ValidatorWebUtil validatorWebUtil;
    @Autowired
    ValidatorUtil validatorUtil;

    @Override
    public boolean supports(Class<?> type) {
        return InventoryCmd.class.isAssignableFrom(type);
    }

    @Override
    public void validate(Object target, Errors errors) {
        InventoryCmd cmd = (InventoryCmd) target;

        validatorWebUtil.validateNotBlank("name", errors, cmd.getName());
    }
}
