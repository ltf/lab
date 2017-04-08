<?php
$urls = array(
	'http://www.saskatchewan.ca'
	'http://www.saskatchewan.ca'
	'http://www.saskatchewan.ca'
);
$html = file_get_contents($url); 
$hash = hash('crc32b', $html); 
echo $hash; 
?>
