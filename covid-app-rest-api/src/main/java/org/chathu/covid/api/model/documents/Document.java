package org.chathu.covid.api.model.documents;

import java.util.UUID;

public abstract class Document {
    private String id;
    private String partitionKey;

    public Document(){
        this.id = UUID.randomUUID().toString();
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
