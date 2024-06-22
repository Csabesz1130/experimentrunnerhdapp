package com.festivalcompany.model;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

public class Company {
    private String id;
    private String name;
    private String programName;
    private Date lastModified;
    private List<String> equipmentSNs;
    private List<Comment> comments;

    // Fields
    private boolean eloszto;
    private boolean aram;
    private boolean halozat;
    private boolean ptg;
    private boolean szoftver;
    private boolean param;
    private boolean helyszin;
    private String telepites;
    private String felderites;
    private boolean distributor;
    private boolean electricity;
    private boolean network;
    private boolean software;
    private boolean parameter;
    private boolean location;
    private String dismantlingStatus;
    private String packagingStatus;
    private boolean baseDismantling;

    public Company() {
        this.equipmentSNs = new ArrayList<>();
        this.comments = new ArrayList<>();
    }

    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getProgramName() { return programName; }
    public void setProgramName(String programName) { this.programName = programName; }

    public Date getLastModified() { return lastModified; }
    public void setLastModified(Date lastModified) { this.lastModified = lastModified; }

    public List<String> getEquipmentSNs() { return equipmentSNs; }
    public void setEquipmentSNs(List<String> equipmentSNs) { this.equipmentSNs = equipmentSNs; }

    public String getEquipmentSN() { return equipmentSNs.isEmpty() ? null : equipmentSNs.get(0); }
    public void setEquipmentSN(String sn) {
        if (equipmentSNs.isEmpty()) {
            equipmentSNs.add(sn);
        } else {
            equipmentSNs.set(0, sn);
        }
    }

    public List<Comment> getComments() { return comments; }
    public void setComments(List<Comment> comments) { this.comments = comments; }

    public boolean isEloszto() { return eloszto; }
    public void setEloszto(boolean eloszto) { this.eloszto = eloszto; }

    public boolean isAram() { return aram; }
    public void setAram(boolean aram) { this.aram = aram; }

    public boolean isHalozat() { return halozat; }
    public void setHalozat(boolean halozat) { this.halozat = halozat; }

    public boolean isPtg() { return ptg; }
    public void setPtg(boolean ptg) { this.ptg = ptg; }

    public boolean isSzoftver() { return szoftver; }
    public void setSzoftver(boolean szoftver) { this.szoftver = szoftver; }

    public boolean isParam() { return param; }
    public void setParam(boolean param) { this.param = param; }

    public boolean isHelyszin() { return helyszin; }
    public void setHelyszin(boolean helyszin) { this.helyszin = helyszin; }

    public String getTelepites() { return telepites; }
    public void setTelepites(String telepites) { this.telepites = telepites; }

    public String getFelderites() { return felderites; }
    public void setFelderites(String felderites) { this.felderites = felderites; }

    public boolean isDistributor() { return distributor; }
    public void setDistributor(boolean distributor) { this.distributor = distributor; }

    public boolean isElectricity() { return electricity; }
    public void setElectricity(boolean electricity) { this.electricity = electricity; }

    public boolean isNetwork() { return network; }
    public void setNetwork(boolean network) { this.network = network; }

    public boolean isSoftware() { return software; }
    public void setSoftware(boolean software) { this.software = software; }

    public boolean isParameter() { return parameter; }
    public void setParameter(boolean parameter) { this.parameter = parameter; }

    public boolean isLocation() { return location; }
    public void setLocation(boolean location) { this.location = location; }

    public String getDismantlingStatus() { return dismantlingStatus; }
    public void setDismantlingStatus(String dismantlingStatus) { this.dismantlingStatus = dismantlingStatus; }

    public String getPackagingStatus() { return packagingStatus; }
    public void setPackagingStatus(String packagingStatus) { this.packagingStatus = packagingStatus; }

    public boolean isBaseDismantling() { return baseDismantling; }
    public void setBaseDismantling(boolean baseDismantling) { this.baseDismantling = baseDismantling; }
}