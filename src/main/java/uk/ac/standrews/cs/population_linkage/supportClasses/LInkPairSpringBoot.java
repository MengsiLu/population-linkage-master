/*
 * Copyright 2020 Systems Research Group, University of St Andrews:
 * <https://github.com/stacs-srg>
 */
//package uk.ac.standrews.cs.population_linkage.supportClasses;
//
//import com.google.gson.Gson;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.RestController;
//import uk.ac.standrews.cs.population_linkage.characterisation.LinkStatus;
//import uk.ac.standrews.cs.population_linkage.data.umea.ConvertorImporter;
//import uk.ac.standrews.cs.population_linkage.groundTruth.SingleSourceLinkageAnalysis;
//import uk.ac.standrews.cs.population_linkage.groundTruth.ThresholdAnalysis;
//import uk.ac.standrews.cs.population_linkage.groundTruth.umea.UmeaBirthSibling;
//import uk.ac.standrews.cs.population_linkage.supportClasses.LinkageList;
//import uk.ac.standrews.cs.storr.impl.LXP;
//import uk.ac.standrews.cs.utilities.metrics.coreConcepts.Metric;
//
//import javax.annotation.Resource;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//@RestController
//public class LinkPairSpringBoot {
//
//
//    public static List<LinkageList> LinkageAnalysis = new ArrayList<>();
//    public static String LinkageLinkageJson;
//
////    @RequestMapping("/test")
////    @ResponseBody
////    public String LinkageAnalysisOutput() throws Exception {
////
////        LinkageAnalysis.clear();
////        UmeaBirthSibling linkageJson =  new UmeaBirthSibling(500,1);
////        linkageJson.run();
////
////        //LinkageLinkageJson = new Gson().toJson(LinkageAnalysis);
////        for (int i = 0; i < LinkageAnalysis.size(); i++) {
////            System.out.println(LinkageAnalysis.get(i).toString());
////        }
////
////        return "yes, ok";
////    }
//
//    @Autowired
//    private uk.ac.standrews.cs.population_linkage.groundTruth.umea.UmeaBirthSiblingSpringBoot umeaBirthSiblingSpringBoot;
//
//    @RequestMapping("/test")
//    @ResponseBody
//    public String LinkageJson() throws Exception {
//        LinkageAnalysis.clear();
//        UmeaBirthSibling linkageJson =  new UmeaBirthSibling(500,1);
//        linkageJson.run();
//        //System.out.println(LinkageLinkageJson);
//
//        //LinkageList s = LinkageAnalysis.get(10);
//
//        // UmeaBirthSiblingSpringBoot.LinkageLinkageJson = new Gson().toJson(s);
//        uk.ac.standrews.cs.population_linkage.groundTruth.umea.UmeaBirthSiblingSpringBoot.LinkageLinkageJson = new Gson().toJson(LinkageAnalysis.subList(0,10));
//        //System.out.println(s);
//
//        return uk.ac.standrews.cs.population_linkage.groundTruth.umea.UmeaBirthSiblingSpringBoot.LinkageLinkageJson;
//        //return "ok";
//    }
//
//
//}
