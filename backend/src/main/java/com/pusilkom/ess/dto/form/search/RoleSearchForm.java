package com.pusilkom.ess.dto.form.search;

/**
 * Created by ITF on 2/14/2019.
 */
public class RoleSearchForm extends BaseTableSearchForm {
    private String kode;
    private String label;

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
