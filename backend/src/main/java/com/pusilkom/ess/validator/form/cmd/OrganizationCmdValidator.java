package com.pusilkom.ess.validator.form.cmd;

import com.pusilkom.ess.dto.form.cmd.OrganizationCmd;
import com.pusilkom.ess.util.ValidatorUtil;
import com.pusilkom.ess.util.ValidatorWebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 *
 * @author Ruliantyo
 */
@Component
public class OrganizationCmdValidator implements Validator{
    @Autowired
    ValidatorWebUtil validatorWebUtil;
    @Autowired
    ValidatorUtil validatorUtil;

    @Override
    public boolean supports(Class<?> type) {
        return OrganizationCmd.class.isAssignableFrom(type);
    }
    
    @Override
    public void validate(Object o, Errors errors) {
        OrganizationCmd cmd = (OrganizationCmd) o;

        validatorWebUtil.validateNotBlank("isActive", errors, cmd.getIsActive());
        validatorWebUtil.validateNotBlank("name", errors, cmd.getName());
        validatorWebUtil.validateHasParent("parentId", errors, cmd.getParentId().longValue());
    }
     
}
