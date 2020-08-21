/*
 * Copyright 2020 Systems Research Group, University of St Andrews:
 * <https://github.com/stacs-srg>
 */
package uk.ac.standrews.cs.population_linkage.groundTruth.umea;

import com.google.gson.Gson;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import uk.ac.standrews.cs.population_linkage.SpringBootApi;
import uk.ac.standrews.cs.population_linkage.groundTruthML.UmeaSiblingBundlingML;
import uk.ac.standrews.cs.population_linkage.supportClasses.*;
import uk.ac.standrews.cs.population_records.record_types.Birth;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class UmeaBirthSiblingSpringBoot  {

    public static String LinkageLinkageJson;
    public static String PairDistanceJson;
    public static String PairJson;
    private static String FilterPairDistance;
    public static List<PairList> PairDistance = new ArrayList<>();
    public static List<RecordDetailList> PairDistanceDetail = new ArrayList<>();
    public static List<LinkageList> LinkageAnalysis = new ArrayList<>();
    public static List<BirthSiblingML> BirthSiblingML = new ArrayList<>();

    //public static List<PairList> result = new ArrayList<>();

    Set<PairList> linkageSet = new HashSet<>(PairDistance);
    List<PairList> result = new ArrayList<PairList>(linkageSet);


    @Autowired
    private UmeaBirthSiblingSpringBoot umeaBirthSiblingSpringBoot;

    /**Output the total distance of linkage calculated by various metrics*/
    @RequestMapping("/LinkageJson")
    @ResponseBody
    public String getLinkageGroundTruth() throws Exception {
        LinkageAnalysis.clear();
        UmeaBirthSibling linkageJson =  new UmeaBirthSibling(500,1);
        linkageJson.run();

        List<LinkageList> result = LinkageAnalysis.stream()
                .filter(t -> t.getDistance() < 0.2 && t.getRecord1_id() != t.getRecord2_id())
                .collect(Collectors.toList());

        Set<LinkageList> linkageSet = new HashSet<>(result);
        List<LinkageList> list = new ArrayList<>(linkageSet);
        String FilterLinkageDistance= new Gson().toJson(list);

        return FilterLinkageDistance;
        //return "ok";
    }


    @RequestMapping("/PairJson")
    @ResponseBody
    public String getPairGroundTruth() throws Exception {
//        PairDistance.clear();
//        result.clear();
//        UmeaBirthSibling PairJson =  new UmeaBirthSibling(500,1);
//        PairJson.run();
        result = PairDistance.stream()
                //.filter(t -> t.getField_index() <= 11 && t.getLinkStatus().equals("NOT_TRUE_MATCH"))
                .filter(t -> t.getField_index() <= 11 && t.getRecord1_id() != t.getRecord2_id())
                .distinct()
                .collect(Collectors.toList());

        Set<PairList> linkageSet = new HashSet<>(result);
        List<PairList> list = new ArrayList<>(linkageSet);

        PairDistanceJson= new Gson().toJson(list);
        return UmeaBirthSiblingSpringBoot.PairDistanceJson;
    }

    /**The Ground Truth of Birth-birth Sibling linkage*/
    // used to return the allpair which is true-match compared with the non-match
    @RequestMapping("/AllLinkageGroundTruth")
    @ResponseBody
    public String getAllGroundTruth(String record1, String record2) throws Exception {

        UmeaSiblingBundlingML birthSibling =  new UmeaSiblingBundlingML("BirthSibling");
        birthSibling.run();

        List<BirthSiblingML> result = BirthSiblingML.stream()
                .filter(t -> t.getLinkStatus().equals("TRUE_LINK") && t.getRecord1_id() != t.getRecord2_id() && t.getRecord1_id().equals(record1) || t.getRecord2_id().equals(record2))
                .collect(Collectors.toList());

        Set<BirthSiblingML> linkageSet = new HashSet<>(result);
        List<BirthSiblingML> list = new ArrayList<>(linkageSet);
        String FilterLinkageML= new Gson().toJson(list);

        return FilterLinkageML;

    }

    /**Output the filter pair distance of linkage calculated by various metrics
     * 1. Receive the parameters from the frontend
     * 2. Based on the parameters to filter the data
     * 3. return the filterd data to the frontend
     */

// eachPair
    @RequestMapping(value = "/PairRecord")
    public String getPairRecord(String RecordPair, String record1_id, String record2_id, String field_value1, String field_value2){

        System.out.println(RecordPair+ " " + record1_id + " " + record2_id + " " + field_value1 + " " + field_value2);

        List<RecordDetailList> FilterData = PairDistanceDetail.stream()
                .filter(t -> (t.getRecordPair()).equals(RecordPair) && (t.getRecord1_id().equals(record1_id)) && (t.getRecord2_id().equals(record2_id))  && (t.getField_value1().equals(field_value1)) && (t.getField_value2().equals(field_value2)))
                .collect(Collectors.toList());

        Set<RecordDetailList> linkageSet = new HashSet<>(FilterData);
        List<RecordDetailList> list = new ArrayList<>(linkageSet);

        UmeaBirthSiblingSpringBoot.FilterPairDistance= new Gson().toJson(list);
        //System.out.println(FilterPairDistance);
        return FilterPairDistance;
    }

    @RequestMapping(value = "/LinkageAllPairRecord")
    public String getLinkageAllPairRecord(String record1_id, String record2_id, String metric_name){
        System.out.println(record1_id + "," + record2_id + "," + metric_name);

        List<RecordDetailList> FilterData = PairDistanceDetail.stream()
                .filter(t -> ((t.getRecord1_id().equals(record1_id)) && (t.getRecord2_id().equals(record2_id))  && (t.getMetric_name().equals(metric_name))))
                .collect(Collectors.toList());

        Set<RecordDetailList> linkageSet = new HashSet<>(FilterData);
        List<RecordDetailList> list = new ArrayList<>(linkageSet);

//        for (PairList t: list) {
//            System.out.println(t);
//        }

        UmeaBirthSiblingSpringBoot.FilterPairDistance= new Gson().toJson(list);
        //System.out.println(FilterPairDistance);
        return FilterPairDistance;
    }
}

//       save the Json to the local
//        try {
//
//            FileWriter writer = new FileWriter("/home/mengsilu/Dissertation/population-linkage-master-csv/population-linkage-master/src/main/java/UmeaRecordPairJson.json");
//            writer.write(PairDistanceJson);
//            writer.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }



//    /**Read Json File Locally*/
//    @RequestMapping("/ReadJson")
//    @ResponseBody
//    public String getPairRecordDetail() throws Exception {
//        File file = ResourceUtils.getFile("UmeaRecordPairJson.json");
//        String PairJson = new String(Files.readAllBytes(file.toPath()));
//        return PairJson;
//    }

