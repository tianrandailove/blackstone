package com.kefan.blackstone.database;

import org.litepal.crud.DataSupport;

/**
 * Created by MY SHIP on 2017/5/11.
 */

public class AlterRecord extends DataSupport {
    private String chineseName;
    private boolean isChecked;
    private int speciesId;
    private String remark;
    private boolean remarkIsNull;
    private String speciesType;

    public AlterRecord(String chineseName, int speciesId, String speciesType) {
        this.chineseName = chineseName;
        this.speciesId = speciesId;
        this.speciesType = speciesType;
        remarkIsNull = true;
        isChecked = false;
    }

    public String getChineseName() {
        return chineseName;
    }

    public void setChineseName(String chineseName) {
        this.chineseName = chineseName;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public int getSpeciesId() {
        return speciesId;
    }

    public void setSpeciesId(int speciesId) {
        this.speciesId = speciesId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public boolean isRemarkIsNull() {
        return remarkIsNull;
    }

    public void setRemarkIsNull(boolean remarkIsNull) {
        this.remarkIsNull = remarkIsNull;
    }
}
