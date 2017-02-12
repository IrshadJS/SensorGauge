<?Php
/////// Update your database login details here /////
$dbhost_name = "XXX"; // Your host name 
$database = "XXX";       // Your database name
$username = "XXX";            // Your login userid 
$password = "XXX";            // Your password 

$dbo = new mysqli($dbhost_name, $username, $password, $database);
if ($dbo->connect_error) {
		  die("Connection failed: " . $dbo->connect_error);
		}
?> 