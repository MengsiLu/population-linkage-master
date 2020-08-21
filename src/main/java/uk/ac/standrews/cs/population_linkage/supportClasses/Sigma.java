/*
 * Copyright 2020 Systems Research Group, University of St Andrews:
 * <https://github.com/stacs-srg>
 */
package uk.ac.standrews.cs.population_linkage.supportClasses;

import com.google.gson.Gson;
import uk.ac.standrews.cs.population_linkage.groundTruth.umea.UmeaBirthSiblingSpringBoot;
import uk.ac.standrews.cs.population_records.record_types.Birth;
import uk.ac.standrews.cs.storr.impl.LXP;
import uk.ac.standrews.cs.utilities.metrics.coreConcepts.Metric;

import java.io.Console;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Sigma function for combining metrics - compares a single set of fields
 * Created by al on 13/12/18
 */
public class Sigma extends Metric<LXP> {

    final Metric<String> base_distance;
    final List<Integer> field_list;
    final int id_field_index;


    public Sigma(final Metric<String> base_distance, final List<Integer> field_list, final int id_field_index) {
        // base_metric, getComparisonField(), getIdFieldIndex()

        this.base_distance = base_distance;
        this.field_list = field_list;
        this.id_field_index = id_field_index;

    }


    @Override
    public double calculateDistance(final LXP a, final LXP b) {

        double total_distance = 0.0d;
        double pair_distance = 0.0d;
        DecimalFormat df = new DecimalFormat("######0.00");

        final Metric<String> metric = base_distance;
        final String metric_name = metric.getMetricName();

        String RecordPair = "null";
        String LinkStatus = "null";
        List<PairList> PairDistance = new ArrayList<>();
        List<PairList> result = new ArrayList<>();

        for (int field_index : field_list) {
            try {

                String field_value1 = a.getString(field_index);
                String field_value2 = b.getString(field_index);

                String record1_id = (String) a.get(1);
                String record2_id = (String) b.get(1);

//                List<RecordList> record = new ArrayList<>();
//                record.add(new RecordList(record1_id, record2_id));
//                System.out.println(record);

                String Record1_FATHER_FORENAME_ID = a.getString(Birth.FATHER_IDENTITY).trim();
                String Record2_FATHER_FORENAME_ID = b.getString(Birth.FATHER_IDENTITY).trim();

                String Record1_MOTHER_FORENAME_ID = a.getString(Birth.MOTHER_IDENTITY).trim();
                String Record2_MOTHER_FORENAME_ID = b.getString(Birth.MOTHER_IDENTITY).trim();

                // FATHER_IDENTITY
                if (field_index ==7 ){
                    RecordPair = "FATHER_FORENAME";
                    if (Record1_FATHER_FORENAME_ID.isEmpty() || Record2_FATHER_FORENAME_ID.isEmpty()) {
                        LinkStatus = "UNKNOWN";
                    } else if (Record1_FATHER_FORENAME_ID.equals(Record2_FATHER_FORENAME_ID)) {
                        LinkStatus = "TRUE_MATCH";
                    } else {
                        LinkStatus = "NOT_TRUE_MATCH";
                    }
                }
                else if(field_index ==8){
                    RecordPair = "FATHER_SURNAME";
                    if (Record1_FATHER_FORENAME_ID.isEmpty() || Record2_FATHER_FORENAME_ID.isEmpty()) {
                        LinkStatus = "UNKNOWN";
                    } else if (Record1_FATHER_FORENAME_ID.equals(Record2_FATHER_FORENAME_ID)) {
                        LinkStatus = "TRUE_MATCH";
                    } else {
                        LinkStatus = "NOT_TRUE_MATCH";
                    }
                }

                // Mother Identity
                else if(field_index ==9){
                    RecordPair = "MOTHER_FORENAME";
                    if (Record1_MOTHER_FORENAME_ID.isEmpty() || Record2_MOTHER_FORENAME_ID.isEmpty()) {
                        LinkStatus = "UNKNOWN";
                    } else if (Record1_MOTHER_FORENAME_ID.equals(Record2_MOTHER_FORENAME_ID)) {
                        LinkStatus = "TRUE_MATCH";
                    } else {
                        LinkStatus = "NOT_TRUE_MATCH";
                    }
                }
                else if(field_index ==11){
                    RecordPair = "MOTHER_MAIDEN_SURNAME";
                    if (Record1_MOTHER_FORENAME_ID.isEmpty() || Record2_MOTHER_FORENAME_ID.isEmpty()) {
                        LinkStatus = "UNKNOWN";
                    } else if (Record1_MOTHER_FORENAME_ID.equals(Record2_MOTHER_FORENAME_ID)) {
                        LinkStatus = "TRUE_MATCH";
                    } else {
                        LinkStatus = "NOT_TRUE_MATCH";
                    }
                }
//                else if(field_index ==30){
//                    RecordPair = "PARENTS_PLACE_OF_MARRIAGE";
//                }

                total_distance += base_distance.distance(field_value1, field_value2);
                pair_distance = (base_distance.distance(field_value1, field_value2));


                UmeaBirthSiblingSpringBoot.PairDistance.add(new PairList(field_index, RecordPair, record1_id, record2_id, field_value1, field_value2, metric_name, df.format(pair_distance),LinkStatus));
                UmeaBirthSiblingSpringBoot.PairDistanceDetail.add(new RecordDetailList(field_index, RecordPair, record1_id, record2_id, field_value1, field_value2, metric_name, df.format(pair_distance),LinkStatus));
                //PairDistance.add(new PairList(field_index, RecordPair, record1_id, record2_id, field_value1, field_value2, metric_name,df.format(pair_distance),  LinkStatus));


//                result = PairDistance.stream()
//                        .filter(t -> t.getLinkStatus().equals("TRUE_MATCH") && t.getField_index() <= 11)
//                        .collect(Collectors.toList());

//                try {
//                    FileWriter writer = new FileWriter("FilterData.json");
//                    writer.write(FilterPairDistance);
//                    writer.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
                //System.out.println(FilterPairDistance);


                // List<PairList> test = PairDistance.stream().distinct().collect(Collectors.toList());


//
//                // Save the Json File to the local
//

//                try {
//                    //write converted json data to a file named "CountryGSON.json"
//                    FileWriter writer = new FileWriter("UmeaRecordPairJson.json", true);
//                    writer.write(test);
//                    writer.close();
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }

                     } catch (Exception e) {
                printExceptionDebug(a, b, field_index);
                throw new RuntimeException("exception comparing field " + a.getMetaData().getFieldName(field_index) + " in records \n" + a + "\n and \n" + b, e);
            }
        }

//        Set<PairList> linkageSet = new HashSet<>(result);
//        List<PairList> list = new ArrayList<>(linkageSet);
//
//        String FilterPairDistance= new Gson().toJson(PairDistance);
//        System.out.println(FilterPairDistance);


        return normaliseArbitraryPositiveDistance(total_distance);
    }




    @Override
    public String getMetricName() {
        return base_distance.getMetricName();
    }

    private void printExceptionDebug(final LXP a, final LXP b, final int field_index) {

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println("Exception in distance calculation");
        System.out.println("field index list: " + field_index);
        System.out.println("a: " + (a == null ? "null" : "not null"));
        System.out.println("b: " + (b == null ? "null" : "not null"));
        System.out.println("id of a: " + a.getString(id_field_index));
        System.out.println("id of b: " + b.getString(id_field_index));
        System.out.println("field name a: " + a.getMetaData().getFieldName(field_index));
        System.out.println("field name b: " + b.getMetaData().getFieldName(field_index));
        System.out.println("field value a: " + a.getString(field_index));
        System.out.println("field value b: " + b.getString(field_index));
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }
}
