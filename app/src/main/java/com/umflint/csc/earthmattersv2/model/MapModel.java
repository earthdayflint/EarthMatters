package com.umflint.csc.earthmattersv2.model;

import java.util.ArrayList;

/**
 * Created by Benjamin on 3/14/2017.
 */

public class MapModel {

    private String mapOne;
    private String mapId;

    public MapModel(String mapOne, String mapId) {
        this.mapOne = mapOne;
        this.mapId = mapId;
    }

    public String getMapId() {
        return mapId;
    }

    public void setMapId(String mapId) {
        this.mapId = mapId;
    }

    public String getMapOne() {
        return mapOne;
    }

    public void setMapOne(String mapOne) {
        this.mapOne = mapOne;
    }

    public MapModel() {
    }
}
