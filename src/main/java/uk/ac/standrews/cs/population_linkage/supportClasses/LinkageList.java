/*
 * Copyright 2020 Systems Research Group, University of St Andrews:
 * <https://github.com/stacs-srg>
 */
package uk.ac.standrews.cs.population_linkage.supportClasses;

import uk.ac.standrews.cs.population_linkage.characterisation.LinkStatus;

public class LinkageList {
    private String record1_id;
    private String record2_id;
    private double distance;
    private LinkStatus link_status;
    private String metric_name;

    public LinkageList(String record1_id, String record2_id, double distance, LinkStatus link_status, String metric_name){
        this.record1_id = record1_id;
        this.record2_id = record2_id;
        this.distance = distance;
        this.link_status = link_status;
        this.metric_name = metric_name;

    }

    public boolean equals(Object obj) {
        LinkageList t = (LinkageList) obj;
        return record2_id.equals(t.record2_id);
    }

    public int hashCode() {
        String in = record2_id;
        return in.hashCode();
    }

    public String getRecord1_id() {
        return record1_id;
    }

    public void setRecord1_id(String record1_id) {
        this.record1_id = record1_id;
    }

    public String getRecord2_id() {
        return record2_id;
    }

    public void setRecord2_id(String record2_id) {
        this.record2_id = record2_id;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public LinkStatus getLink_status() {
        return link_status;
    }

    public void setLink_status(LinkStatus link_status) {
        this.link_status = link_status;
    }

    public String getMetric_name() {
        return metric_name;
    }

    public void setMetric_name(String metric_name) {
        this.metric_name = metric_name;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("Record1_id:" + record1_id + " ");
        builder.append("Record2_id:" + record2_id + " ");
        builder.append("distance:" + distance + " ");
        builder.append("link_status:" + link_status + " ");
        builder.append("metric_name:" + metric_name + " ");

        return  builder.toString();
    }
}


