## Table of Contents
- Requirements
- Installation
- Usage
- Authors && Contributors

#### Requirements
- Backend: Maven
- Interface： Spring Boot Api
- Frontend: Vue.js 1.x or 2.x, Element UI
- SSH: It requires to generate public key and private key to install data.umea jar file, that you can get access the data and run the proj (https://stacs-srg.github.io/ciesvium/usage/encrypted-data-sets/tutorials/creating-encrypted-dataset.html)

#### **Installation**
This project uses SpringBootApi, npm, vue.js and Element UI Go check them out if you don't have them locally installed

**install SPringBootApi, add dependencies to the pom.xml**
```
<dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter</artifactId>
            <version>2.3.1.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
            <version>2.3.1.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <version>2.2.0.RELEASE</version>
        </dependency>
<dependency>
```

**install element ui**
```
$ npm install element-ui -S
```

#### Usage
Run the SpringBootApi.class where located in package uk.ac.standrews.cs.population_linkage;

when you run the service success, visit: http://localhost:8080 in the browser.


**1. Back-end:**

**Set What Metrics should be Run**

set the metric in the constant.classs

```
package uk.ac.standrews.cs.population_linkage.supportClasse
public static final List<StringMetric> BASE_METRICS = concatenate(TRUE_METRICS, PSEUDO_METRICS, PHONETIC_COMPARATORS);
```


**2. Interface:**

**Set Threshold Value**
```
package uk.ac.standrews.cs.population_linkage.groundTruth.umea;
/*set the t.getDistance < 0.2 (a certain threshhold)*/
List<LinkageList> result = LinkageAnalysis.stream()
                .filter(t -> t.getDistance() < 0.2 && t.getRecord1_id() != t.getRecord2_id())
                .collect(Collectors.toList());
```
**Set Number of Records to be Checked**

set number of records to be checked in th UmeaBirthSiblingSpringBoot
```
package uk.ac.standrews.cs.population_linkage.groundTruth.umea;
/*UmeaBirthSibling linkage Json = new UmeaBirthSibling(number of records to be checkc, number of runs)*/
UmeaBirthSibling linkageJson =  new UmeaBirthSibling(500,1);
linkageJson.run();
```

**3. Front-end**

If you want to change any codes in the front-end, and run the project, you should do the following instructions:

```
 $ cd /your directory/population-linkage-master/src/frontend/
 $ npm run build
```

#### Authors && Contributors
Authors: Mengsi Lu, Special thank to the Professor Alan Dearl who gives me lots of support during the dissertation. This project is based on the code from https://github.com/stacs-srg