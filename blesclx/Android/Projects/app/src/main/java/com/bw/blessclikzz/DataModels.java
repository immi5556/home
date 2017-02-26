package com.bw.blessclikzz;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Immanuel Raj on 2/23/2017.
 */
public class DataModels implements Serializable {
    ArrayList<DataModel> imgs;

    public DataModels(){
        imgs = new ArrayList<DataModel>();
    }
    public DataModels(ArrayList<DataModel> mdl){
        imgs = mdl;
    }
    public void addImg(DataModel mod) {
        imgs.add(mod);
    }
    public void setImgs(ArrayList<DataModel> mod) {
        imgs = mod;
    }

    public ArrayList<DataModel> getImgs(ArrayList<DataModel> mod) {
        return imgs;
    }
}
