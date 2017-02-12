<?
require 'config.php';
$query = "select node_title from cc2530_master_table";
$result = $dbo->query($query);
$jsonArray = array();
if ($result->num_rows > 0) {
		  while($row = $result->fetch_assoc()) {
			$jsonArrayItem = array();
		    $jsonArrayItem['node'] = $row['node_title'];
		    array_push($jsonArray, $jsonArrayItem);
												}
							}

$dbo->close();		
header('Content-type: application/json');
echo json_encode($jsonArray);
?>		