/*
 * Copyright 2020 Systems Research Group, University of St Andrews:
 * <https://github.com/stacs-srg>
 */
package uk.ac.standrews.cs.population_linkage.groundTruth.umea;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.ac.standrews.cs.data.umea.UmeaBirthsDataSet;
import uk.ac.standrews.cs.population_linkage.ApplicationProperties;
import uk.ac.standrews.cs.population_linkage.characterisation.LinkStatus;
import uk.ac.standrews.cs.population_linkage.data.umea.ConvertorImporter;
import uk.ac.standrews.cs.population_linkage.data.umea.MyImportUmeaRecordsToStore;
import uk.ac.standrews.cs.population_linkage.groundTruth.SymmetricSingleSourceLinkageAnalysis;
import uk.ac.standrews.cs.population_linkage.linkageRecipes.BirthSiblingLinkageRecipe;
import uk.ac.standrews.cs.population_linkage.supportClasses.LinkageList;
import uk.ac.standrews.cs.population_linkage.supportClasses.RecordPair;
import uk.ac.standrews.cs.population_linkage.supportClasses.Utilities;
import uk.ac.standrews.cs.storr.impl.LXP;
import uk.ac.standrews.cs.storr.impl.exceptions.BucketException;
import uk.ac.standrews.cs.utilities.dataset.DataSet;
import uk.ac.standrews.cs.utilities.metrics.coreConcepts.Metric;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Performs linkage analysis on data from births.
 * It compares the parents' names, date and place of marriage on two birth records.
 * The fields used for comparison are listed in getComparisonFields().
 * This is indirect sibling linkage between the babies on two birth records.
 *
 */
public class UmeaBirthSibling extends SymmetricSingleSourceLinkageAnalysis {

//    UmeaBirthSibling(Path store_path, String repo_name, int number_of_records_to_be_checked, int number_of_runs) throws IOException {
//        super(store_path, repo_name, getLinkageResultsFilename(), getDistanceResultsFilename(), number_of_records_to_be_checked, number_of_runs, true);
//    }


      UmeaBirthSibling(int number_of_records_to_be_checked, int number_of_runs) throws IOException {
          super(getLinkageResultsFilename(), getDistanceResultsFilename(), getLinkageAnalysisFilename(), number_of_records_to_be_checked, number_of_runs, true);
      }


    @Override
    public Iterable<LXP> getSourceRecords(ConvertorImporter record_repository) {
        return Utilities.getBirthRecords(record_repository);
    }

    @Override
    public List<Integer> getComparisonFields() {
        return BirthSiblingLinkageRecipe.LINKAGE_FIELDS;
    }

    @Override
    public int getIdFieldIndex() {
        return BirthSiblingLinkageRecipe.ID_FIELD_INDEX;
    }

    @Override
    public LinkStatus isTrueMatch(LXP record1, LXP record2) {
        return trueMatch(record1, record2);
    }

    public static LinkStatus trueMatch(LXP record1, LXP record2) {
        return BirthSiblingLinkageRecipe.trueMatch(record1, record2);
    }

    @Override
    public boolean isViableLink(RecordPair proposedLink) {
        return BirthSiblingLinkageRecipe.isViable(proposedLink);
    }

    @Override
    public String getDatasetName() {
        return "Umea";
    }

    @Override
    public String getLinkageType() {
        return "sibling bundling between babies on birth records";
    }

    @Override
    public String getSourceType() {
        return "births";
    }


    public static void main(String[] args) throws Exception {

//        Path store_path = ApplicationProperties.getStorePath();
//        String repo_name = "umea";

        final int NUMBER_OF_RUNS = 1;

        // number_of_records_to_be_checked = CHECK_ALL_RECORDS for exhaustive otherwise DEFAULT_NUMBER_OF_RECORDS_TO_BE_CHECKED or some other specific number.

        //new UmeaBirthSibling(DEFAULT_NUMBER_OF_RECORDS_TO_BE_CHECKED, NUMBER_OF_RUNS).run();
        new UmeaBirthSibling(500, NUMBER_OF_RUNS).run();

    }
}
