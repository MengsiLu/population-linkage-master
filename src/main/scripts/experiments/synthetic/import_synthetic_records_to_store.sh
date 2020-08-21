#!/bin/sh
#
# Copyright 2020 Systems Research Group, University of St Andrews:
# <https://github.com/stacs-srg>
#


if [[ -n "$1" ]];
then
    export MAVEN_OPTS="-Xmx"$1"G"
    echo Setting heap size: $1GB
fi

cd ~/population-linkage
mvn exec:java -q -Dexec.cleanupDaemonThreads=false -Dexec.mainClass="uk.ac.standrews.cs.population_linkage.data.synthetic.ImportSyntheticScotlandRecordsToStore" -e -Dexec.args="$2 $3 $4"