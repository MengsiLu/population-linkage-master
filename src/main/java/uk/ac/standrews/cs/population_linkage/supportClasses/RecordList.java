/*
 * Copyright 2020 Systems Research Group, University of St Andrews:
 * <https://github.com/stacs-srg>
 */
package uk.ac.standrews.cs.population_linkage.supportClasses;

import uk.ac.standrews.cs.population_linkage.characterisation.LinkStatus;

public class RecordList {
    //int id_field_index;
    private String RecordPair;
    private String record1_id;
    private String record2_id;

    //private double total_distance = 0.0d;


    public void setRecord1_id(String record1_id) {
        this.record1_id = record1_id;
    }

    public void setRecord2_id(String record2_id) {
        this.record2_id = record2_id;
    }

    public String getRecordPair() {
        return RecordPair;
    }

    public void setRecordPair(String recordPair) {
        RecordPair = recordPair;
    }

    public Object getRecord1_id() {
        return record1_id;
    }


    public Object getRecord2_id() {
        return record2_id;
    }


    public RecordList(String RecordPair, String record1_id, String record2_id){
        //this.id_field_index = id_field_index;
        this.RecordPair = RecordPair;
        this.record1_id = record1_id;
        this.record2_id = record2_id;

        //this.total_distance = total_distance;


    }

    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("Record1_id:" + record1_id);
        builder.append("Record2_id:" + record2_id);

        return  builder.toString();
    }
}


