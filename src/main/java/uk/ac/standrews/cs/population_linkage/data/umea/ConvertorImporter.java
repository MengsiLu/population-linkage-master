/*
 * Copyright 2020 Systems Research Group, University of St Andrews:
 * <https://github.com/stacs-srg>
 */
package uk.ac.standrews.cs.population_linkage.data.umea;
import uk.ac.standrews.cs.population_records.record_types.Birth;
import uk.ac.standrews.cs.population_records.record_types.Death;
import uk.ac.standrews.cs.population_records.record_types.Marriage;
import uk.ac.standrews.cs.storr.impl.exceptions.BucketException;
import uk.ac.standrews.cs.utilities.dataset.DataSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import uk.ac.standrews.cs.storr.interfaces.IStore;

public class ConvertorImporter {
    private List<Birth> births;
    private List<Death> deaths;
    private List<Marriage> marriages;

    private IStore store;

    public ConvertorImporter() {
        this.births = new ArrayList<>();
        this.deaths = new ArrayList<>();
        this.marriages = new ArrayList<>();
    }

    public Iterable<Birth> getBirths() {
        return this.births;
    }
    public Iterable<Death> getDeaths() {
        return this.deaths;
    }
    public Iterable<Marriage> getMarriages() {
        return this.marriages;
    }

    public void addBirth(Birth birth) throws BucketException {
        this.births.add(birth);
    }

    public void addDeath(Death death) throws BucketException {
        this.deaths.add(death);
    }

    public void addMarriage(Marriage marriage) throws BucketException {
        this.marriages.add(marriage);
    }


    public void importBirthRecords(DataSet birth_records) throws BucketException {
        Iterator var2 = Birth.convertToRecords(birth_records).iterator();
        while(var2.hasNext()) {
            Birth birth = (Birth)var2.next();
            this.addBirth(birth);
        }
    }

    public void importDeathRecords(DataSet death_records) throws BucketException {
        Iterator var2 = Death.convertToRecords(death_records).iterator();
        while(var2.hasNext()) {
            Death death = (Death)var2.next();
            this.addDeath(death);
        }
    }

    public void importMarriageRecords(DataSet marriage_records) throws BucketException {
        Iterator var2 = Marriage.convertToRecords(marriage_records).iterator();
        while(var2.hasNext()) {
            Marriage marriage = (Marriage) var2.next();
            this.addMarriage(marriage);
        }
    }


    public void stopStoreWatcher() {
        this.store.getWatcher().stopService();
    }

    public int getNumberOfBirths() throws BucketException {
        return this.births.size();
    }

    public int getNumberOfDeaths() throws BucketException {
        return this.deaths.size();
    }

    public int getNumberOfMarriages() throws BucketException {
        return this.marriages.size();
    }

//    public void setBirthsCacheSize(int size) {
//        try {
//            this.births.setCacheSize(size);
//        } catch (Exception var3) {
//            throw new RuntimeException(var3);
//        }
//    }
//
//    public void setDeathsCacheSize(int size) {
//        try {
//            this.deaths.setCacheSize(size);
//        } catch (Exception var3) {
//            throw new RuntimeException(var3);
//        }
//    }
//
//    public void setMarriagesCacheSize(int size) {
//        try {
//            this.marriages.setCacheSize(size);
//        } catch (Exception var3) {
//            throw new RuntimeException(var3);
//        }
//    }

}