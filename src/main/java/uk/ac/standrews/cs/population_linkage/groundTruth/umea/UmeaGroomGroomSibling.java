/*
 * Copyright 2020 Systems Research Group, University of St Andrews:
 * <https://github.com/stacs-srg>
 */
package uk.ac.standrews.cs.population_linkage.groundTruth.umea;

import uk.ac.standrews.cs.population_linkage.ApplicationProperties;
import uk.ac.standrews.cs.population_linkage.characterisation.LinkStatus;
import uk.ac.standrews.cs.population_linkage.data.umea.ConvertorImporter;
import uk.ac.standrews.cs.population_linkage.groundTruth.SymmetricSingleSourceLinkageAnalysis;
import uk.ac.standrews.cs.population_linkage.linkageRecipes.GroomGroomSiblingLinkageRecipe;
import uk.ac.standrews.cs.population_linkage.supportClasses.RecordPair;
import uk.ac.standrews.cs.population_linkage.supportClasses.Utilities;
import uk.ac.standrews.cs.storr.impl.LXP;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

/**
 * Performs linkage analysis on data from marriages.
 * It compares the grooms' parents' names on two marriage records.
 * The fields used for comparison are listed in getComparisonFields().
 * This is indirect sibling linkage between the grooms on two marriage records.
 */
public class UmeaGroomGroomSibling extends SymmetricSingleSourceLinkageAnalysis {

    UmeaGroomGroomSibling(Path store_path, String repo_name, int number_of_records_to_be_checked, int number_of_runs) throws IOException {
        super(getLinkageResultsFilename(), getDistanceResultsFilename(),getLinkageAnalysisFilename(), number_of_records_to_be_checked, number_of_runs, true);
    }

    @Override
    public Iterable<LXP> getSourceRecords(ConvertorImporter record_repository) {
        return Utilities.getMarriageRecords(record_repository);
    }

    @Override
    public List<Integer> getComparisonFields() {
        return GroomGroomSiblingLinkageRecipe.LINKAGE_FIELDS;
    }

    @Override
    public int getIdFieldIndex() {
        return GroomGroomSiblingLinkageRecipe.ID_FIELD_INDEX;
    }

    @Override
    public LinkStatus isTrueMatch(LXP record1, LXP record2) {
        return trueMatch(record1, record2);
    }

    public static LinkStatus trueMatch(LXP record1, LXP record2) {
        return GroomGroomSiblingLinkageRecipe.trueMatch(record1, record2);
    }

    @Override
    public boolean isViableLink(RecordPair proposedLink) {
        return GroomGroomSiblingLinkageRecipe.isViable(proposedLink);
    }

    @Override
    public String getDatasetName() {
        return "Umea";
    }

    @Override
    public String getLinkageType() {
        return "sibling bundling between grooms on marriage records";
    }

    @Override
    public String getSourceType() {
        return "marriages";
    }

    public static void main(String[] args) throws Exception {

        Path store_path = ApplicationProperties.getStorePath();
        String repo_name = "umea";

        int NUMBER_OF_RUNS = 1;

        new UmeaGroomGroomSibling(store_path, repo_name, DEFAULT_NUMBER_OF_RECORDS_TO_BE_CHECKED, NUMBER_OF_RUNS).run();
    }
}
