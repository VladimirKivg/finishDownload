package test.example.finishdownload.regions;

import java.util.ArrayList;

public class Region {
    private int deeply;
    private String name;
    private String normalName;
    private boolean map=true;
    private boolean srtm=true;
    private boolean thisMapIs=true;
    private String innerDownloadSuffix;
    private String downloadSuffix;
    private String downloadPrefix;
    private String innerDownloadPrefix;
    private String downloadName;

    public boolean isThisMapIs() {
        return thisMapIs;
    }

    public void setThisMapIs(boolean thisMapIs) {
        this.thisMapIs = thisMapIs;
    }

    public String getDownloadName() {
        return downloadName;
    }

    public void setDownloadName(String downloadName) {
        this.downloadName = downloadName;
    }

    public String getDownloadSuffix() {
        return downloadSuffix;
    }

    public void setDownloadSuffix(String downloadSuffix) {
        this.downloadSuffix = downloadSuffix;
    }

    public String getDownloadPrefix() {
        return downloadPrefix;
    }

    public void setDownloadPrefix(String downloadPrefix) {
        this.downloadPrefix = downloadPrefix;
    }

    public String getInnerDownloadPrefix() {
        return innerDownloadPrefix;
    }

    public void setInnerDownloadPrefix(String innerDownloadPrefix) {
        this.innerDownloadPrefix = innerDownloadPrefix;
    }

    private ArrayList<Region> underRegions = new ArrayList<>();

    public boolean isSrtm() {
        return srtm;
    }

    public void setSrtm(String srtm) {
        if (srtm.equals("yes")) {
            this.srtm = true;
        } else if (srtm.equals("no")) {
            this.srtm = false;
        }
    }

    public String getInnerDownloadSuffix() {
        return innerDownloadSuffix;
    }

    public void setInnerDownloadSuffix(String innerDownloadSuffix) {
        this.innerDownloadSuffix = innerDownloadSuffix;
    }

    public boolean isMap() {
        return map;
    }

    public void setMap(String map) {
        if (map.equals("yes")) {
            this.map = true;
        } else if (map.equals("no")) {
            this.map = false;
        }

    }

    public int getDeeply() {
        return deeply;
    }

    public void setDeeply(int deeply) {
        this.deeply = deeply;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        setNormalName(this.name);
    }

    public ArrayList<Region> getUnderRegions() {
        return underRegions;
    }

    public void setAddUnderRegions(Region underRegions) {
        this.underRegions.add(underRegions);
    }

    public String getNormalName() {
        return normalName;
    }

    @Override
    public String toString() {
        return normalName;
    }

    private void setNormalName(String name) {
        this.normalName = name.substring(0, 1).toUpperCase() + name.substring(1);
    }
}
