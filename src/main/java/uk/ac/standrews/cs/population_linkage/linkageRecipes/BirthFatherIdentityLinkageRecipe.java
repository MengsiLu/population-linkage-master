/*
 * Copyright 2020 Systems Research Group, University of St Andrews:
 * <https://github.com/stacs-srg>
 */
package uk.ac.standrews.cs.population_linkage.linkageRecipes;

import uk.ac.standrews.cs.population_linkage.characterisation.LinkStatus;
import uk.ac.standrews.cs.population_linkage.linkageRunners.BitBlasterLinkageRunner;
import uk.ac.standrews.cs.population_linkage.supportClasses.Link;
import uk.ac.standrews.cs.population_linkage.supportClasses.LinkageConfig;
import uk.ac.standrews.cs.population_linkage.supportClasses.RecordPair;
import uk.ac.standrews.cs.population_records.record_types.Birth;
import uk.ac.standrews.cs.storr.impl.LXP;
import uk.ac.standrews.cs.storr.impl.exceptions.BucketException;
import uk.ac.standrews.cs.utilities.metrics.JensenShannon;

import java.util.List;
import java.util.Map;

public class BirthFatherIdentityLinkageRecipe extends LinkageRecipe {

    public static final List<Integer> LINKAGE_FIELDS = list(
            Birth.FORENAME,
            Birth.SURNAME
    );

    public static final List<Integer> SEARCH_FIELDS = list(
            Birth.FATHER_FORENAME,
            Birth.FATHER_SURNAME
    );

    public static final int ID_FIELD_INDEX1 = Birth.STANDARDISED_ID;
    public static final int ID_FIELD_INDEX2 = Birth.STANDARDISED_ID;

    @SuppressWarnings("unchecked")
    public static final List<List<Pair>> TRUE_MATCH_ALTERNATIVES = list(
            list(pair(Birth.CHILD_IDENTITY, Birth.FATHER_IDENTITY)),
            list(pair(Birth.STANDARDISED_ID, Birth.FATHER_BIRTH_RECORD_IDENTITY))
    );

    public static void main(String[] args) throws BucketException {

        String sourceRepo = args[0]; // e.g. synthetic-scotland_13k_1_clean
        String resultsRepo = args[1]; // e.g. synth_results

        LinkageRecipe linkageRecipe = new BirthFatherIdentityLinkageRecipe(sourceRepo,
                resultsRepo, LINKAGE_TYPE + "-links");

        LinkageConfig.numberOfROs = 20;

        new BitBlasterLinkageRunner()
                .run(linkageRecipe, new JensenShannon(2048), 0.2, true,
                        2, false, false, true, false
                );
    }

    public static final String LINKAGE_TYPE = "birth-father-identity";

    public BirthFatherIdentityLinkageRecipe(String source_repository_name, String results_repository_name, String links_persistent_name) {
        super(source_repository_name, results_repository_name, links_persistent_name);
    }

    @Override
    public LinkStatus isTrueMatch(LXP record1, LXP record2) {
        return trueMatch(record1, record2);
    }

    public static LinkStatus trueMatch(LXP record1, LXP record2) {
        return trueMatch(record1, record2, TRUE_MATCH_ALTERNATIVES);
    }

    @Override
    public String getLinkageType() {
        return LINKAGE_TYPE;
    }

    @Override
    public Class<? extends LXP> getStoredType() {
        return Birth.class;
    }

    @Override
    public Class<? extends LXP> getSearchType() {
        return Birth.class;
    }

    @Override
    public String getStoredRole() {
        return Birth.ROLE_BABY;
    }

    @Override
    public String getSearchRole() {
        return Birth.ROLE_FATHER;
    }

    @Override
    public List<Integer> getLinkageFields() {
        return LINKAGE_FIELDS;
    }

    @Override
    public boolean isViableLink(RecordPair proposedLink) {
        return isViable( proposedLink );
    }

    public static boolean isViable(RecordPair proposedLink) {

        // Proposed link is between a person being born on the first record, and the same person
        // appearing as father on the second record. Check that a plausible period has elapsed for
        // the person to be the father.

        return birthParentIdentityLinkIsViable(proposedLink);
    }

    @Override
    public List<Integer> getSearchMappingFields() { return SEARCH_FIELDS; }

    @Override
    public Map<String, Link> getGroundTruthLinks() {
        throw new RuntimeException("ground truth implementation not consistent with trueMatch()");
//        return getGroundTruthLinksOn(Birth.CHILD_IDENTITY, Birth.FATHER_IDENTITY);
    }

    @Override
    public int getNumberOfGroundTruthTrueLinks() {
        throw new RuntimeException("ground truth implementation not consistent with trueMatch()");
//        return getNumberOfGroundTruthTrueLinksOn(Birth.CHILD_IDENTITY, Birth.FATHER_IDENTITY);
    }

    @Override
    public int getNumberOfGroundTruthTrueLinksPostFilter() {
        throw new RuntimeException("ground truth implementation not consistent with trueMatch()");
//        return getNumberOfGroundTruthTrueLinksPostFilterOn(Birth.CHILD_IDENTITY, Birth.FATHER_IDENTITY);
    }

    @Override
    public Iterable<LXP> getPreFilteredStoredRecords() {
        return filterBySex(super.getPreFilteredStoredRecords(), Birth.SEX, "m");
    }
}
