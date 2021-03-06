package com.kefan.blackstone.database;

import org.litepal.crud.DataSupport;

/**
 * Created by MY SHIP on 2017/3/24.
 * 物种信息表
 */

public class Species extends DataSupport{

    private int singal;
    private String chineseName;
    private String englishName;
    private String latinName;
    private String order;
    private String latinOrder;
    private String family;
    private String latinFamily;
    private String mainPhoto;
    private String speciesType;
    private String chineseAbbr;

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public int getSingal() {
        return singal;
    }

    public void setSingal(int singal) {
        this.singal = singal;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getChineseName() {
        return chineseName;
    }

    public void setChineseName(String chineseName) {
        this.chineseName = chineseName;
    }

    public String getLatinName() {
        return latinName;
    }

    public void setLatinName(String latinName) {
        this.latinName = latinName;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getMainPhoto() {
        return mainPhoto;
    }

    public void setMainPhoto(String mainPhoto) {
        this.mainPhoto = mainPhoto;
    }

    public String getSpeciesType() {
        return speciesType;
    }

    public void setSpeciesType(String speciesType) {
        this.speciesType = speciesType;
    }

    public String getLatinOrder() {
        return latinOrder;
    }

    public void setLatinOrder(String latinOrder) {
        this.latinOrder = latinOrder;
    }

    public String getLatinFamily() {
        return latinFamily;
    }

    public void setLatinFamily(String latinFamily) {
        this.latinFamily = latinFamily;
    }

    public String getChineseAbbr() {
        return chineseAbbr;
    }

    public void setChineseAbbr(String chineseAbbr) {
        this.chineseAbbr = chineseAbbr;
    }

    @Override
    public String toString() {
        return "Species{" +
                "singal=" + singal +
                ", chineseName='" + chineseName + '\'' +
                ", englishName='" + englishName + '\'' +
                ", latinName='" + latinName + '\'' +
                ", order='" + order + '\'' +
                ", latinOrder='" + latinOrder + '\'' +
                ", family='" + family + '\'' +
                ", latinFamily='" + latinFamily + '\'' +
                ", mainPhoto='" + mainPhoto + '\'' +
                ", speciesType='" + speciesType + '\'' +
                ", chineseAbbr='" + chineseAbbr + '\'' +
                '}';
    }
}
