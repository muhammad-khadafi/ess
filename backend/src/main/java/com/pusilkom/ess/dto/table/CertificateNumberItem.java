package com.pusilkom.ess.dto.table;

import com.pusilkom.ess.model.CertificateNumber;

public class CertificateNumberItem extends CertificateNumber {

    private String monthYear;

    public String getMonthYear() {
        return monthYear;
    }

    public void setMonthYear(String monthYear) {
        this.monthYear = monthYear;
    }
}
