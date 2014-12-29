package com.mtimmerman.rest.resources.plex;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

/**
 * Created by maarten on 26.12.14.
 */
public class SyncList {
    @JacksonXmlProperty(isAttribute = true)
    private Integer itemsCompleteCount;
    @JacksonXmlProperty(isAttribute = true)
    private Integer totalSize;

    public Integer getItemsCompleteCount() {
        return itemsCompleteCount;
    }

    public void setItemsCompleteCount(Integer itemsCompleteCount) {
        this.itemsCompleteCount = itemsCompleteCount;
    }

    public Integer getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(Integer totalSize) {
        this.totalSize = totalSize;
    }
}
