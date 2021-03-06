/*
 * Copyright 2020 Systems Research Group, University of St Andrews:
 * <https://github.com/stacs-srg>
 */
package uk.ac.standrews.cs.population_linkage.groundTruthML;

import com.google.gson.Gson;
import uk.ac.standrews.cs.data.umea.UmeaBirthsDataSet;
import uk.ac.standrews.cs.population_linkage.data.umea.ConvertorImporter;
import uk.ac.standrews.cs.population_linkage.groundTruth.umea.UmeaBirthSiblingSpringBoot;
import uk.ac.standrews.cs.population_linkage.supportClasses.BirthSiblingML;
import uk.ac.standrews.cs.population_linkage.supportClasses.Utilities;
import uk.ac.standrews.cs.storr.impl.LXP;
import uk.ac.standrews.cs.storr.impl.exceptions.BucketException;
import uk.ac.standrews.cs.utilities.dataset.DataSet;
import uk.ac.standrews.cs.utilities.metrics.coreConcepts.StringMetric;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * This class performs linkage analysis on data pulled from a single data sources, for example births.
 *
 * Classes extending this class are required to implement the following methods:
 *     getSourceRecords(RecordRepository record_repository), which provides the records from the first data source
 *     getSourceType(), which provides a textual description of the first data source, for example, "births"
 *     LinkStatus isTrueLink(final LXP record1, final LXP record2), returns the ground truth about equivalence of two datum's from the source
 *     getComparisonFields(), returns the set of fields to be used for distance comparison from data source 1 (note the name)
 */

public abstract class AllPairsSameSourceLinkageAnalysisML extends ThresholdAnalysisML {

//    private final Path store_path;
//    private final String repo_name;

    private static final int BLOCK_SIZE = 100;
    private static final String DELIMIT = ",";
    private static final int CHECK_ALL_RECORDS =-1;

//    private final PrintWriter distance_results_writer;
//    private final PrintWriter distance_results_metadata_writer;

    private List<LXP> source_records;
    private int number_of_records;

    DecimalFormat df = new DecimalFormat("#.###");

    protected AllPairsSameSourceLinkageAnalysisML(final String repo_name) throws IOException {

        super();

//        this.store_path = store_path;
//        this.repo_name = repo_name;

        //distance_results_writer = new PrintWriter(new BufferedWriter(new FileWriter(distance_results_filename + ".csv", false)));
        //distance_results_metadata_writer = new PrintWriter(new BufferedWriter(new FileWriter(distance_results_filename + ".meta", false)));

        setupRecords();
    }

    protected abstract Iterable<LXP> getSourceRecords(ConvertorImporter record_repository);

    protected abstract LinkStatus isTrueLink(final LXP record1, final LXP record2);

    protected abstract String getSourceType();

    private List<Map<String, int[]>> initialiseDistances() {

        final List<Map<String, int[]>> result = new ArrayList<>();

        for (int i = 0; i < NUMBER_OF_RUNS; i++) {

            final Map<String, int[]> map = new HashMap<>();

            for (final StringMetric metric : metrics) {
                map.put(metric.getMetricName(), new int[NUMBER_OF_THRESHOLDS_SAMPLED]);
            }

            result.add(map);
        }
        return result;
    }

    private Map<String, Integer> initialiseRunNumbers() {

        final Map<String, Integer> map = new HashMap<>();

        for (final StringMetric metric : metrics) {
            map.put(metric.getMetricName(), 0);
        }

        return map;
    }

    private void setupRecords() throws IOException {

       // System.out.println("Reading records from repository: " + repo_name);

        //final RecordRepository record_repository = new RecordRepository(store_path, repo_name);
//        final ConvertorImporter record_repository = new ConvertorImporter();
//
//        final Iterable<LXP> records = getSourceRecords(record_repository);
//
//        //System.out.println("Randomising record order");
//
//        source_records = Utilities.permute(records, SEED);
//        number_of_records = source_records.size();

        //if (verbose) System.out.println("Reading records from list: " );
        int number_of_records_to_be_checked = 25000;

        ConvertorImporter record_repository = new ConvertorImporter();
        DataSet birth_records = new UmeaBirthsDataSet();
        try {
            record_repository.importBirthRecords(birth_records);
        } catch (BucketException e) {
            e.printStackTrace();
        }
        final Iterable<LXP> records = getSourceRecords(record_repository);


        // final RecordRepository = new RecordRepository(store_path, repo_name);
        //final Iterable<LXP> records = getSourceRecords(record_repository);

        //if (verbose) System.out.println("Randomising record order");

        source_records = Utilities.permute(records, SEED);
        number_of_records = number_of_records_to_be_checked == CHECK_ALL_RECORDS ? source_records.size() : number_of_records_to_be_checked;

    }

    public void run() throws Exception {

//        printHeaders();
//        printMetaData();

        final int number_of_blocks_to_be_checked = number_of_records / BLOCK_SIZE;

        for (int block_index = 0; block_index < number_of_blocks_to_be_checked; block_index++) {

            processBlock(block_index);

            System.out.println("finished block: checked " + (block_index + 1) * BLOCK_SIZE + " records");
            System.out.flush();
        }
    }

    private void processBlock(final int block_index) {

        final int start_index = block_index * BLOCK_SIZE;
        final int end_index = start_index + BLOCK_SIZE;

        for (int i = start_index; i < end_index; i++) {
            processRecord(i);
        }

    }

    private void processRecord(final int record_index) {

        LinkStatus last_status = LinkStatus.UNKNOWN;

        for (int j = record_index + 1; j < number_of_records; j++) {

            final LXP record1 = source_records.get(record_index);
            final LXP record2 = source_records.get(j);

            String record1_id = (String) record1.get(1);
            String record2_id = (String) record2.get(1);

            final LinkStatus link_status = isTrueLink(record1, record2);

            //UmeaBirthSiblingSpringBoot.BirthSiblingML.add(new BirthSiblingML(record1_id, record2_id, link_status));
            List<BirthSiblingML> Ml = new ArrayList<>();
            Ml.add(new BirthSiblingML(record1_id, record2_id, link_status));


            if (link_status != LinkStatus.UNKNOWN) {

                if (shouldPrintResult(link_status, last_status)) {

                    last_status = link_status;

                    for (final StringMetric metric : metrics) {

                        for (int field_selector : getComparisonFields()) {

                            final double distance = metric.distance(record1.getString(field_selector), record2.getString(field_selector));
                           // outputMeasurement(distance);
                        }
                    }
//                    distance_results_writer.print(statusToPrintFormat(link_status));
//                    distance_results_writer.println();
//                    distance_results_writer.flush();
                }
            }

            List<BirthSiblingML> result = Ml.stream()
                    //.filter(t -> t.getField_index() <= 11 && t.getLinkStatus().equals("NOT_TRUE_MATCH"))
                    .filter(t -> t.getLinkStatus().equals("TRUE_LINK") && t.getRecord1_id() != t.getRecord2_id())
                    .distinct()
                    .collect(Collectors.toList());

            String BirthSibling= new Gson().toJson(Ml);
            System.out.println(BirthSibling);

        }

                // save the Json to the local
//        try {
//
//            FileWriter writer = new FileWriter("/home/mengsilu/Dissertation/population-linkage-master-csv/population-linkage-master/src/main/java/BirthSiblingML.json");
//            writer.write(FilterLinkageML);
//            writer.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


    }

    private boolean shouldPrintResult(LinkStatus link_status, LinkStatus last_status) {
        if( link_status == LinkStatus.TRUE_LINK ) { // always print true links
            return true;
        }
        if( last_status == LinkStatus.NOT_TRUE_LINK && link_status == LinkStatus.NOT_TRUE_LINK ) { // never print two falses in a row
            return false;
        } else if( last_status == LinkStatus.TRUE_LINK && link_status == LinkStatus.NOT_TRUE_LINK ) { // last was true so print the false
            return true;
        }
        return true; // print if status was unknown or last was unknown
    }

//    private void outputMeasurement(double value) {
//        distance_results_writer.print(df.format(value));
//        distance_results_writer.print(DELIMIT);
//    }
//
//    private void outputMeasurement(long value) {
//        distance_results_writer.print(value);
//        distance_results_writer.print(DELIMIT);
//    }

//    private void printHeaders() {
//
//        LXP a_source_record = source_records.get(0);
//
//        for (final StringMetric metric : metrics) {
//
//            String name = metric.getMetricName();
//            for (int field_selector : getComparisonFields()) {
//
//                String label = name + "." + a_source_record.getMetaData().getFieldName(field_selector);  //metric name concatenated with the field selector name;
//                distance_results_writer.print(label);
//                distance_results_writer.print(DELIMIT);
//
//            }
//        }
//
//        distance_results_writer.print("link_non-link");
//        distance_results_writer.print(DELIMIT);
//
//        distance_results_writer.println();
//        distance_results_writer.flush();
//    }
//
//    private void printMetaData() {
//
//        distance_results_metadata_writer.println("Output file created: " + LocalDateTime.now());
//        distance_results_metadata_writer.println("Checking quality of linkage for machine learning processing: cross products of metrics and field distances");
//        distance_results_metadata_writer.println("Dataset: Umea");
//        distance_results_metadata_writer.println("LinkageRecipe type: sibling bundling");
//        distance_results_metadata_writer.println("Records: " + getSourceType());
//        distance_results_metadata_writer.flush();
//    }

    public enum LinkStatus {

        TRUE_LINK, NOT_TRUE_LINK, UNKNOWN
    }

    private String statusToPrintFormat( LinkStatus ls ) {
        if( ls == LinkStatus.TRUE_LINK ) {
            return "1";
        } else if( ls == LinkStatus.NOT_TRUE_LINK ) {
            return "-1";
        } else {
            return "0";
        }
    }
}
