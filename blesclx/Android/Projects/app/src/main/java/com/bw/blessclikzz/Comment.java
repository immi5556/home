package com.bw.blessclikzz;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Immanuel Raj on 2/24/2017.
 */
public class Comment implements Serializable {
    int commentid;
    int id;
    String comment;
    String deviceid;
    Date createat;

    public  Comment(int commentid, int id, String comment, String deviceid, Date createat)
    {
        this.comment = comment;
        this.commentid = commentid;
        this.id = id;
        this.deviceid = deviceid;
        this.createat = createat;
    }
    public int getId() { return id; }
    public int getCommentId() { return commentid; }
    public String getDeviceidId() { return deviceid; }
    public String getComment() { return comment; }
    public Date getCreateat() { return createat; }
}
