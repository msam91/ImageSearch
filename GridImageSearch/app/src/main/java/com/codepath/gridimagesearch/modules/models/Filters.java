package com.codepath.gridimagesearch.modules.models;

import java.io.Serializable;

/**
 * Created by msamant on 10/17/15.
 */
public class Filters implements Serializable {

    private String imageSize;
    private String imageColor;
    private String imageType;
    private String filterSite;

    public String getImageSize() {
        return imageSize;
    }

    public void setImageSize(String imageSize) {
        this.imageSize = imageSize;
    }

    public String getImageColor() {
        return imageColor;
    }

    public void setImageColor(String imageColor) {
        this.imageColor = imageColor;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public String getFilterSite() {
        return filterSite;
    }

    public void setFilterSite(String filterSite) {
        this.filterSite = filterSite;
    }
}
