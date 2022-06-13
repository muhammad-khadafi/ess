package com.pusilkom.ess.dto.table;

import com.pusilkom.ess.model.SubProAssignment;

public class SubProAssignmentItem extends SubProAssignment {
    private String subProName;
    private String spvName;
    private Integer idSubPro;

    public String getSubProName() {
        return subProName;
    }

    public void setSubProName(String subProName) {
        this.subProName = subProName;
    }

    public String getSpvName() {
        return spvName;
    }

    public void setSpvName(String spvName) {
        this.spvName = spvName;
    }

    public Integer getIdSubPro() {
        return idSubPro;
    }

    public void setIdSubPro(Integer idSubPro) {
        this.idSubPro = idSubPro;
    }
}
