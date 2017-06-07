<?php

/*
 * Following code will list all the products
 */
//require "init.php";
// array for JSON response
//$response = array();


// include db connect class
require_once __DIR__ . '/db_connect.php';

// connecting to db
$db = new DB_CONNECT();


// json response array
//$response = array("error" => FALSE);
$response["venner"] = array();

// HER VIL VI HENTE INFORMATIONEN DER SENDES FRA APPEN
if (isset($_POST['medlemsid'])) {

	$medlemsid = $_POST['medlemsid'];

	// get all products from products table
	$result = mysql_query("SELECT u.navn, v.ven_medlemsid, v.medlemsid FROM users u INNER JOIN vennerelation v ON u.medlemsid = v.ven_medlemsid WHERE v.medlemsid = $medlemsid") or die(mysql_error());


	// check for empty result
	if (mysql_num_rows($result) > 0) {
    
		// looping through all results
		// products node
    
		while ($row = mysql_fetch_array($result)) {
			// temp user array
			$venner = array();
			$venner["navn"] = $row["navn"];
			$venner["ven_medlemsid"] = $row["ven_medlemsid"];

			// push single product into final response array
			array_push($response["venner"], $venner);
		}
		
		$response["error"] = false;

    // echoing JSON response
    echo json_encode($response);
	} else {
		// required post params is missing
		$response["error"] = TRUE;
		$response["error_msg"] = "Du har ingen venner at hente";
		// echo no users JSON
    echo json_encode($response);
}} else{
	// required post params is missing
      $response["error"] = TRUE;
      $response["error_msg"] = "Informationen kunne ikke videresendes!";
	  echo json_encode($response);
}

?>
