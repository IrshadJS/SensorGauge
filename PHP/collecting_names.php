<?php
require 'config.php';

$selected_node=$_GET['selected_node'];
$selected_property=$_GET['selected_property'];

$query = "SELECT property_name FROM `cc2530_property_table` WHERE property_id =$selected_property and  node_id=$selected_node ";
$result = $dbo->query($query);
$row = $result->fetch_assoc();

$jsonArrayItem = array();
$jsonArrayItem['property'] = $row['property_name'];

$query2 = "SELECT node_title FROM cc2530_master_table WHERE node_id = $selected_node";
$result1 = $dbo->query($query2);
$row1 = $result1->fetch_assoc();

$jsonArrayItem['node'] = $row1['node_title'];

$query3 = "SELECT property_xlabel,property_xunit FROM `cc2530_property_table` WHERE property_id =$selected_property and  node_id=$selected_node ";
$result2 = $dbo->query($query3);
$row2 = $result2->fetch_assoc();

$jsonArrayItem['unit'] = $row2['property_xunit'];

header('Content-type: application/json');
		
echo json_encode($jsonArrayItem);	

?>