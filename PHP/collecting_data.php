
<?php
		require 'config.php';
		$selected_node=$_GET['selected_node'];
		$selected_property=$_GET['selected_property'];
		$query = "SELECT * FROM `cc2530_val_table` WHERE Property_Id = $selected_property and  Node_Id=$selected_node order by id desc limit 1 ";
		$result = $dbo->query($query);
		$jsonArray = array();
		if ($result->num_rows > 0) {
		  while($row = $result->fetch_assoc()) {
		    $jsonArrayItem = array();
		    $jsonArrayItem['label'] = $row['timestamp'];
		    $jsonArrayItem['value'] = $row['property_val'];
			$jsonArrayItem['date'] = $row['date'];
		  }
		}
		$dbo->close();
		header('Content-type: application/json');
		echo json_encode($jsonArrayItem);		
?>
	
