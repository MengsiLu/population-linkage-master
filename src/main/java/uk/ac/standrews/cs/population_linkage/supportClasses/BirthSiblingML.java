/*
 * Copyright 2020 Systems Research Group, University of St Andrews:
 * <https://github.com/stacs-srg>
 */
package uk.ac.standrews.cs.population_linkage.supportClasses;
//import uk.ac.standrews.cs.population_linkage.characterisation.LinkStatus;
import uk.ac.standrews.cs.population_linkage.groundTruthML.AllPairsSameSourceLinkageAnalysisML.LinkStatus;

public class BirthSiblingML {

    private String record1_id;
    private String record2_id;
    private LinkStatus link_status;


    public BirthSiblingML (String record1_id, String record2_id, LinkStatus link_status){

        this.record1_id = record1_id;
        this.record2_id = record2_id;
        this.link_status = link_status;

        //this.total_distance = total_distance;

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



    public LinkStatus getLinkStatus() {
        return link_status;
    }

    public void setLinkStatus(String linkStatus) {
        link_status = link_status;
    }

    public boolean equals(Object obj) {
        BirthSiblingML t = (BirthSiblingML) obj;
        //return RecordPair.equals(t.RecordPair) && record1_id.equals(t.record1_id) && record2_id.equals(t.record2_id) && metric_name.equals(t.metric_name);
        return  record1_id.equals(t.record1_id) && record2_id.equals(t.record2_id);
    }

    public int hashCode() {
        String str = record1_id + record2_id;
        return str.hashCode();
    }


    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Record1_id:" + record1_id);
        builder.append("Record2_id:" + record2_id);
        builder.append("Link_status:" + link_status);
        return  builder.toString();
    }
}


