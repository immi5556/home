package com.bw.blessclikzz;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Immanuel Raj on 2/22/2017.
 */

public class DataModel implements Serializable {

    int id;
    String url;
    String title;
    String author;
    String deviceid;
    Date createdate;
    ArrayList<Comment> comments;

    public DataModel(int iid, String ul, String title, String auth, String device, Date created, ArrayList<Comment> comments) {
        this.id = iid;
        this.url = ul;
        this.author = auth;
        this.deviceid = device;
        this.title = title;
        this.createdate = created;
        this.comments = comments;
    }
    public int getId() { return id; }
    public String getUrl() { return url;  }
    public String getAuthor() {
        return author;
    }
    public String getDeviceid() {
        return deviceid;
    }
    public String getTitle() {
        return title;
    }
    public Date getCreateddate() {
        return createdate;
    }
    public ArrayList<Comment> getComments() {
        return comments;
    }
}