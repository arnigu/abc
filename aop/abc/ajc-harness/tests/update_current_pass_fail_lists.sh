#!/bin/sh

cd ../../ &&
ant clean > /dev/null &&
ant >/dev/null &&
cd ajc-harness/tests &&
nice -n 5 ./run_cases.pl ajcTests.xml >full.output 2>&1 &&
./calc_updates.pl | mail -s"test run results" abc@comlab.ox.ac.uk &&
cp failed.xml failed_current.xml &&
cp passed.xml passed_current.xml &&
cp failed.output failed_current.output &&
cp full.output full_current.output &&
cp failed_check.xml  failed_check_current.xml && 
cp passed_check.xml  passed_check_current.xml && 
cp skipped.xml skipped_current.xml &&
cvs commit -m"automatic update" full_current.output failed_current.output failed_current.xml passed_current.xml >/dev/null
