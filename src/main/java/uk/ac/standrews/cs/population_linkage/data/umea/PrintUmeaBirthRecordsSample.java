/*
 * Copyright 2020 Systems Research Group, University of St Andrews:
 * <https://github.com/stacs-srg>
 */
package uk.ac.standrews.cs.population_linkage.data.umea;

import uk.ac.standrews.cs.data.umea.UmeaBirthsDataSet;
import uk.ac.standrews.cs.population_linkage.supportClasses.Utilities;

public class PrintUmeaBirthRecordsSample {

    public void run() throws Exception {
        Utilities.printSampleRecords(new UmeaBirthsDataSet(), "birth", PrintUmeaRecordsFromStoreSample.NUMBER_TO_PRINT);
    }

    public static void main(String[] args) throws Exception {

        new PrintUmeaBirthRecordsSample().run();
    }
}
