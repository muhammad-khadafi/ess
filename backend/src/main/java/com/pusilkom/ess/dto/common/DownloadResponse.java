package com.pusilkom.ess.dto.common;

/**
 * Created by fahri on 4/18/17.
 */
public class DownloadResponse {
    private String ticket;

    public DownloadResponse(String ticket) {
        this.ticket = ticket;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }
}
