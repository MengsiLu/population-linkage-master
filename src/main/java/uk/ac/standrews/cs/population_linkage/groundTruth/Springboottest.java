/*
 * Copyright 2020 Systems Research Group, University of St Andrews:
 * <https://github.com/stacs-srg>
 */
package uk.ac.standrews.cs.population_linkage.groundTruth;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.ac.standrews.cs.population_linkage.groundTruth.ThresholdAnalysis;

@RestController
public class Springboottest {
    @RequestMapping("/LinkageAnalysis")
    public String sayHi(){ return "Hello World";}

}
