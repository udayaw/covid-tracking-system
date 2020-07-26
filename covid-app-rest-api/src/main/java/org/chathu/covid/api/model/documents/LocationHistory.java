package org.chathu.covid.api.model.documents;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.chathu.covid.api.model.types.PointLocation;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class LocationHistory extends Document {

    private String userId;

    private String covidId;

    private Date timestamp;

    private PointLocation location;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public PointLocation getLocation() {
        return location;
    }

    public void setLocation(PointLocation location) {
        this.location = location;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getCovidId() {
        return covidId;
    }

    public void setCovidId(String covidId) {
        this.covidId = covidId;
    }
}
