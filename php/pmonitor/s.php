<?php
$urls = array(
	'http://www.saskatchewan.ca/residents/moving-to-saskatchewan/immigrating-to-saskatchewan/saskatchewan-immigrant-nominee-program/applicants-international-skilled-workers/international-skilled-worker-occupations-in-demand',

	'http://www.saskatchewan.ca/residents/moving-to-saskatchewan/immigrating-to-saskatchewan/saskatchewan-immigrant-nominee-program/maximum-number-of-sinp-applications',
	'http://www.saskatchewan.ca'
);
$len = count($urls);
if (is_int($_GET['i'])) {
	$i = $_GET['i'];
} else { 
	$i = rand();
}
echo $i; 
echo $url; 
$url=$urls[$i % $len];
$html = file_get_contents($url); 
$hash = hash('crc32b', $html); 
echo $url; 
?>
