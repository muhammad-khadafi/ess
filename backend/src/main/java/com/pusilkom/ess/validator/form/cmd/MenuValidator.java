package com.pusilkom.ess.validator.form.cmd;

import com.pusilkom.ess.dto.form.cmd.MenuCmd;
import com.pusilkom.ess.service.MenuService;
import com.pusilkom.ess.util.ValidatorUtil;
import com.pusilkom.ess.util.ValidatorWebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class MenuValidator implements Validator {
    @Autowired
    ValidatorWebUtil validatorWebUtil;
    @Autowired
    ValidatorUtil validatorUtil;
    @Autowired
    MenuService menuService;

    @Override
    public boolean supports(Class<?> type) {
        return MenuCmd.class.isAssignableFrom(type);
    }

    @Override
    public void validate(Object target, Errors errors) {

        MenuCmd cmd = (MenuCmd) target;
        System.out.println("====validatenyaaa false===="+cmd.getId());
        if(!menuService.isParentValid(cmd)){
            System.out.println("====validatenyaaa false====");
            errors.rejectValue("idParent","msg.validate.menu.parent");
        }
        if (!menuService.isNameDuplicate(cmd)) {
            System.out.println("====validatenyaaa false====");
            errors.rejectValue("label", "msg.validate.menu.label");
        }

        if (!menuService.isCodeDuplicate(cmd)) {
            System.out.println("====validatenyaaa false====");
            errors.rejectValue("kode", "msg.validate.menu.kode");
        }

    }
}
