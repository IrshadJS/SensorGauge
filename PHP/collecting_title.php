<?

require 'config.php';
$selected_node=$_GET['title'];
$query = "select node_id from cc2530_master_table";
$result = $dbo->query($query);
$jsonArray = array();
if ($result->num_rows > 0) {
		  while($row = $result->fetch_assoc()) {
			$jsonArrayItem = array();
		    $jsonArrayItem['title'] = $row['node_id'];
		    array_push($jsonArray, $jsonArrayItem);
												}
							}

$dbo->close();
header('Content-type: application/json');
echo json_encode($jsonArray);
?>		