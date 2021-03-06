/*
 * Copyright 2020 Systems Research Group, University of St Andrews:
 * <https://github.com/stacs-srg>
 */
package uk.ac.standrews.cs.population_linkage.groundTruth;

//import com.google.gson.Gson;
import uk.ac.standrews.cs.population_linkage.supportClasses.Constants;
import uk.ac.standrews.cs.population_linkage.supportClasses.Sigma;
import uk.ac.standrews.cs.storr.impl.LXP;
import uk.ac.standrews.cs.utilities.metrics.coreConcepts.Metric;
import uk.ac.standrews.cs.utilities.metrics.coreConcepts.StringMetric;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * This class performs linkage analysis on data pulled from a single data sources, for example births.
 */
public abstract class SymmetricSingleSourceLinkageAnalysis extends SingleSourceLinkageAnalysis {

//    protected SymmetricSingleSourceLinkageAnalysis(final Path store_path, final String repo_name, final String linkage_results_filename, final String distance_results_filename, final int number_of_records_to_be_checked, final int number_of_runs, final boolean allow_multiple_links) throws IOException {
//
//        super(store_path, repo_name, linkage_results_filename, distance_results_filename, number_of_records_to_be_checked, number_of_runs, allow_multiple_links);
//    }

    protected SymmetricSingleSourceLinkageAnalysis(final String linkage_results_filename, final String distance_results_filename, final String linkage_analysis_filename, final int number_of_records_to_be_checked, final int number_of_runs, final boolean allow_multiple_links) throws IOException {

          super(linkage_results_filename, distance_results_filename, linkage_analysis_filename, number_of_records_to_be_checked, number_of_runs, allow_multiple_links);
      }

    public List<Metric<LXP>> getCombinedMetrics() {

        final List<Metric<LXP>> result = new ArrayList<>();

           result.add(new Sigma(Constants.JARO_WINKLER, getComparisonFields(), getIdFieldIndex()));
//
//        for (final StringMetric base_metric : Constants.BASE_METRICS) {
//            result.add(new Sigma(base_metric, getComparisonFields(), getIdFieldIndex()));
//        }

        return result;
    }

}
