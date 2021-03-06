/*
 * Copyright 2020 Systems Research Group, University of St Andrews:
 * <https://github.com/stacs-srg>
 */
package uk.ac.standrews.cs.population_linkage.groundTruth;

import uk.ac.standrews.cs.population_linkage.data.umea.ConvertorImporter;
import uk.ac.standrews.cs.population_linkage.supportClasses.Utilities;
import uk.ac.standrews.cs.population_records.RecordRepository;
import uk.ac.standrews.cs.population_records.record_types.Death;
import uk.ac.standrews.cs.storr.impl.LXP;

import java.io.IOException;

/**
 **
 **/
public class UmeaDeathSiblingExamineRecordsAlExperiment extends UmeaBirthSiblingExamineRecordsAlExperiment {

    UmeaDeathSiblingExamineRecordsAlExperiment() throws IOException {
        super();
    }

    @Override
    public Iterable<LXP> getSourceRecords(ConvertorImporter record_repository) {
        System.out.println("Umea Deaths");
        return Utilities.getDeathRecords(record_repository);
    }

    @Override
    protected void run() {
        for( LXP record : records ) {
            String father_surname = record.getString(Death.FATHER_SURNAME);
            String father_forname = record.getString(Death.FATHER_FORENAME);
            String mother_surname = record.getString(Death.MOTHER_MAIDEN_SURNAME);
            String mother_forname = record.getString(Death.FATHER_FORENAME);

            addToCounts( father_surname,father_forname,mother_surname,mother_forname );
            if( bothParentsKnown(record) ) {
                parents_known_counter++;
                addToMap(combined_both_known_parental_map, father_forname + father_surname + mother_forname + mother_surname );
            }
            record_counter++;
        }
        printAnalysis();
    }

    @Override
    protected boolean bothParentsKnown(LXP record) {

        final String b1_mother_id = record.getString(Death.MOTHER_IDENTITY);
        final String b1_father_id = record.getString(Death.FATHER_IDENTITY);

        return !b1_mother_id.isEmpty() && b1_father_id.isEmpty();
    }

    public static void main(String[] args) throws Exception {

        new UmeaDeathSiblingExamineRecordsAlExperiment().runAll();
    }
}
