<?

require 'config.php';
$selected_node=$_GET['selected_node'];
$query = "select property_name from cc2530_property_table where node_id=$selected_node";
$result = $dbo->query($query);
$jsonArray = array();
if ($result->num_rows > 0) {
		  while($row = $result->fetch_assoc()) {
			$jsonArrayItem = array();
		    $jsonArrayItem['property'] = $row['property_name'];
		    array_push($jsonArray, $jsonArrayItem);
												}
							}

$dbo->close();
header('Content-type: application/json');
echo json_encode($jsonArray);
?>		