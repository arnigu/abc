#! /usr/bin/perl -w

$argc=@ARGV;
if ($argc==0) {
@benchmarks=('bean', 'gregor_bean', 'gof_adapter', 'gof_bridge', 'nullptr', 'nullptrafter', 'figure',  'quicksort', 'gregor_quicksort', 'oege_quicksort', 'LoD');
} else {
@benchmarks=@ARGV;
}
#my $report="";

foreach $dir (@benchmarks) {
  print "Processing $dir\n";
  #"$dir outout comparison:\n";
  if (chdir $dir) {
  system("./abcit > abcc.out 2>abccerr.out");
  $abcout=`cat abccerr.out`;
  $abc=0;
  if ($abcout =~ m/Breakdown of abc phases/) {
    system("./runit > abc.out 2>abcerr.out");
    $abcsize=length `cat abc.out`;
    print " abc output size: $abcsize\n";
    $abc=1;
  } else {
    print " abc compilation failed\n";
  }
  system("./ajcit > ajcc.out 2> ajccerr.out");
  $ajcout=`cat ajccerr.out`;
  $ajc=0;
  if ((length $ajcout)==0) {
    system("./runit > ajc.out 2>ajcerr.out");
    $ajcsize=length `cat ajc.out`;
    print " ajc output size: $ajcsize\n";
    $ajc=1;
  } else {
   print " ajc compilation failed\n";
  }
  if ($abc==1 && $ajc==1) {
    $diff = `diff ajc.out abc.out`;
    if ((length $diff)>2000) {
      $diff="diff too long";
    }
    print (((length $diff) == 0 )  ? " same output\n" : " $diff\n");
  }
  system("rm -f abcc.out abc.out ajcc.out ajc.out abccerr.out ajccerr.out ajcerr.out abcerr.out");
  chdir "..";
 } else {
  print " Could not find $dir\n";
 }
}

#print "Summary: \n";
#print "$report\n";
