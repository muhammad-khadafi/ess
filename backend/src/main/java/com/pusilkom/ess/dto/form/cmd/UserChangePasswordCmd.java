package com.pusilkom.ess.dto.form.cmd;

import com.pusilkom.ess.model.Userweb;
import org.springframework.security.crypto.bcrypt.BCrypt;

/**
 * Created by ITF on 2/12/2019.
 */
public class UserChangePasswordCmd extends Userweb {
    private String employeeName;
    private String newPassword;
    private String newPasswordRepeat;

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getNewPasswordRepeat() {
        return newPasswordRepeat;
    }

    public void setNewPasswordRepeat(String newPasswordRepeat) {
        this.newPasswordRepeat = newPasswordRepeat;
    }

    public Userweb forSave(){
        Userweb userweb = new Userweb();

        userweb.setId(this.getId());

        String newSalt = BCrypt.gensalt();
        userweb.setPassword(BCrypt.hashpw(this.getNewPassword(), newSalt));
        userweb.setSalt(newSalt);

        return userweb;
    }


}
