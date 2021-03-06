/*
 * Copyright 2020 Systems Research Group, University of St Andrews:
 * <https://github.com/stacs-srg>
 */
package uk.ac.standrews.cs.population_linkage.data.umea;

import uk.ac.standrews.cs.data.umea.UmeaBirthsDataSet;
import uk.ac.standrews.cs.data.umea.UmeaDeathsDataSet;
import uk.ac.standrews.cs.data.umea.UmeaMarriagesDataSet;
import uk.ac.standrews.cs.population_linkage.ApplicationProperties;
import uk.ac.standrews.cs.utilities.dataset.DataSet;

import java.nio.file.Path;
import java.util.List;

public class MyImportUmeaRecordsToStore {

//    private final Path store_path;
//    private final String repo_name;

    public MyImportUmeaRecordsToStore() {

//        this.store_path = store_path;
//        this.repo_name = repo_name;
    }

    public void run() throws Exception {

       // RecordRepository record_repository = new RecordRepository(store_path, repo_name);

        ConvertorImporter record_repository = new ConvertorImporter();
        System.out.println("Importing Umea records into list: ");
        System.out.println();

        DataSet birth_records = new UmeaBirthsDataSet();
        record_repository.importBirthRecords(birth_records);
        System.out.println("Imported " + birth_records.getRecords().size() + " birth records");

//        for(String se :  birth_records.getRecords().get(0)) {
//            System.out.println(se);
//        }

        for(List<String> list : birth_records.getRecords()){
            for(String s1 : list){
                System.out.print(s1 + " ");
            }
            System.out.println();
        }

        DataSet death_records = new UmeaDeathsDataSet();
        record_repository.importDeathRecords(death_records);
        System.out.println("Imported " + death_records.getRecords().size() + " death records");

        DataSet marriage_records = new UmeaMarriagesDataSet();
        record_repository.importMarriageRecords(marriage_records);
        System.out.println("Imported " + marriage_records.getRecords().size() + " marriage records");

        System.out.println();
        System.out.println("Complete");
    }

    public static void main(String[] args) throws Exception {

        Path store_path = ApplicationProperties.getStorePath();
        String repo_name = ApplicationProperties.getRepositoryName();

        new MyImportUmeaRecordsToStore().run();
    }
}
