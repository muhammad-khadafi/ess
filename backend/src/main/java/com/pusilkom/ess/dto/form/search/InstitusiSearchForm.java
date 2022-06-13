package com.pusilkom.ess.dto.form.search;

/**
 * Created by fahri on 1/16/17.
 */
public class InstitusiSearchForm extends BaseTableSearchForm {
    private String kodeInstitusi;
    private String nama;
    private String namaSingkat;
    private Boolean flagAktif;

    public String getKodeInstitusi() {
        return kodeInstitusi;
    }

    public void setKodeInstitusi(String kodeInstitusi) {
        this.kodeInstitusi = kodeInstitusi;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNamaSingkat() {
        return namaSingkat;
    }

    public void setNamaSingkat(String namaSingkat) {
        this.namaSingkat = namaSingkat;
    }

    public Boolean getFlagAktif() {
        return flagAktif;
    }

    public void setFlagAktif(Boolean flagAktif) {
        this.flagAktif = flagAktif;
    }
}
