package com.pusilkom.ess.validator.form.cmd;

import com.pusilkom.ess.dto.form.cmd.UserChangePasswordCmd;
import com.pusilkom.ess.service.UserwebService;
import com.pusilkom.ess.util.ValidatorUtil;
import com.pusilkom.ess.util.ValidatorWebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {

    @Autowired
    ValidatorWebUtil validatorWebUtil;
    @Autowired
    ValidatorUtil validatorUtil;
    @Autowired
    UserwebService userwebService;

    @Override
    public boolean supports(Class<?> type) {
        return UserChangePasswordCmd.class.isAssignableFrom(type);
    }

    @Override
    public void validate(Object target, Errors errors) {
        System.out.println("====validatenyaaa false====");

        UserChangePasswordCmd cmd = (UserChangePasswordCmd) target;
        if(!userwebService.isPasswordValid(cmd)){
            System.out.println("====validatenyaaa false====");
            errors.rejectValue("password","msg.validate.user.password");
        }

    }
}
