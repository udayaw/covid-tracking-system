package org.chathu.covid.api.model.documents;

import java.util.Date;
import java.util.List;

public class CovidCase extends Document {

    private String userId;

    private Date dateReported;

    private Date dateConfirmed;

    private Date dateDied;

    private boolean isConfirmed;

    private boolean isDead;

    private String primaryTransmitter;

    private List<String> possibleTransmitters;

    private List<String> possibleRecipients;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getDateReported() {
        return dateReported;
    }

    public void setDateReported(Date dateReported) {
        this.dateReported = dateReported;
    }

    public Date getDateConfirmed() {
        return dateConfirmed;
    }

    public void setDateConfirmed(Date dateConfirmed) {
        this.dateConfirmed = dateConfirmed;
    }

    public Date getDateDied() {
        return dateDied;
    }

    public void setDateDied(Date dateDied) {
        this.dateDied = dateDied;
    }

    public boolean isConfirmed() {
        return isConfirmed;
    }

    public void setConfirmed(boolean confirmed) {
        isConfirmed = confirmed;
    }

    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }

    public String getPrimaryTransmitter() {
        return primaryTransmitter;
    }

    public void setPrimaryTransmitter(String primaryTransmitter) {
        this.primaryTransmitter = primaryTransmitter;
    }

    public List<String> getPossibleTransmitters() {
        return possibleTransmitters;
    }

    public void setPossibleTransmitters(List<String> possibleTransmitters) {
        this.possibleTransmitters = possibleTransmitters;
    }

    public List<String> getPossibleRecipients() {
        return possibleRecipients;
    }

    public void setPossibleRecipients(List<String> possibleRecipients) {
        this.possibleRecipients = possibleRecipients;
    }
}
