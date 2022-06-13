package com.pusilkom.ess.validator.form.cmd;

import com.pusilkom.ess.dto.form.cmd.LogActivityDetailCmd;
import com.pusilkom.ess.service.LogActivityDetailService;
import com.pusilkom.ess.util.ValidatorUtil;
import com.pusilkom.ess.util.ValidatorWebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class LogActivityDetailValidator implements Validator {
    @Autowired
    ValidatorWebUtil validatorWebUtil;
    @Autowired
    ValidatorUtil validatorUtil;
    @Autowired
    LogActivityDetailService logActivityDetailService;

    @Override
    public boolean supports(Class<?> type) {
        return LogActivityDetailCmd.class.isAssignableFrom(type);
    }

    @Override
    public void validate(Object target, Errors errors) {
        LogActivityDetailCmd cmd = (LogActivityDetailCmd) target;

        Boolean validasi = logActivityDetailService.validateAbsent(cmd.getId(), cmd.getProjectAssignId(), cmd.getSubProId(),cmd.getDate());
        if(!validasi){
            System.out.println("====validatenyaaa false====");
            errors.rejectValue("date","msg.validate.duplicate.date.project");
        }


    }
}
