/*
 * Copyright 2020 Systems Research Group, University of St Andrews:
 * <https://github.com/stacs-srg>
 */
package uk.ac.standrews.cs.population_linkage.groundTruth;

import uk.ac.standrews.cs.data.umea.UmeaBirthsDataSet;
import uk.ac.standrews.cs.population_linkage.ApplicationProperties;
import uk.ac.standrews.cs.population_linkage.data.umea.ConvertorImporter;
import uk.ac.standrews.cs.population_linkage.groundTruth.umea.UmeaBirthSibling;
import uk.ac.standrews.cs.population_linkage.supportClasses.Utilities;
import uk.ac.standrews.cs.population_records.RecordRepository;
import uk.ac.standrews.cs.storr.impl.LXP;
import uk.ac.standrews.cs.storr.impl.exceptions.BucketException;
import uk.ac.standrews.cs.utilities.dataset.DataSet;
import uk.ac.standrews.cs.utilities.metrics.coreConcepts.Metric;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;

/**
 * This class performs linkage analysis on data pulled from a single data sources, for example births.
 */
public abstract class SingleSourceLinkageAnalysis extends ThresholdAnalysis {

//    protected SingleSourceLinkageAnalysis(final Path store_path, final String repo_name, final String linkage_results_filename, final String distance_results_filename, final int number_of_records_to_be_checked, final int number_of_runs, final boolean allow_multiple_links) throws IOException {
//
//        super(store_path, repo_name, linkage_results_filename, distance_results_filename, number_of_records_to_be_checked, number_of_runs, allow_multiple_links);
//    }

      protected SingleSourceLinkageAnalysis(final String linkage_results_filename, final String distance_results_filename, final String linkage_analysis_filename, final int number_of_records_to_be_checked, final int number_of_runs, final boolean allow_multiple_links) throws IOException {

          super(linkage_results_filename, distance_results_filename, linkage_analysis_filename, number_of_records_to_be_checked, number_of_runs, allow_multiple_links);
      }



    @Override
    public void setupRecords() throws IOException {

        if (verbose) System.out.println("Reading records from list: " );

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

        if (verbose) System.out.println("Randomising record order");

        source_records = Utilities.permute(records, SEED);
        number_of_records = number_of_records_to_be_checked == CHECK_ALL_RECORDS ? source_records.size() : number_of_records_to_be_checked;
    }

    @Override
    public void processRecord(final int record_index, final Metric<LXP> metric, final boolean increment_counts) {

        processRecord(record_index, number_of_records, source_records, source_records, metric, increment_counts);
    }

    @Override
    public void printMetaData() {

        linkage_results_metadata_writer.println("Output file created: " + LocalDateTime.now());
        linkage_results_metadata_writer.println("Checking quality of linkage using various string similarity metrics and thresholds");
        linkage_results_metadata_writer.println("Dataset: " + getDatasetName());
        linkage_results_metadata_writer.println("Linkage type: " + getLinkageType());
        linkage_results_metadata_writer.println("Records: " + getSourceType());
        linkage_results_metadata_writer.flush();

        distance_results_metadata_writer.println("Output file created: " + LocalDateTime.now());
        distance_results_metadata_writer.println("Checking distributions of record pair distances using various string similarity metrics and thresholds");
        distance_results_metadata_writer.println("Dataset: " + getDatasetName());
        distance_results_metadata_writer.println("Linkage type: " + getLinkageType());
        distance_results_metadata_writer.println("Records: " + getSourceType());
        distance_results_metadata_writer.flush();
    }


}
