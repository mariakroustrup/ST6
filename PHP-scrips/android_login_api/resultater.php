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
$response["kondi"] = array();

// HER VIL VI HENTE INFORMATIONEN DER SENDES FRA APPEN
if (isset($_POST['medlemsid'])) {

	$medlemsid = $_POST['medlemsid'];

	// get all products from products table
	$result = mysql_query("SELECT kondi_tid, afstand_km FROM kondi WHERE medlemsid = $medlemsid") or die(mysql_error());


	// check for empty result
	if (mysql_num_rows($result) > 0) {
    
		// looping through all results
		// products node
    
		while ($row = mysql_fetch_array($result)) {
			// temp user array
			$kondi = array();
			$kondi["kondi_tid"] = $row["kondi_tid"];
			$kondi["afstand_km"] = $row["afstand_km"];

			// push single product into final response array
			array_push($response["kondi"], $kondi);
		}
		
		$response["error"] = false;

    // echoing JSON response
    echo json_encode($response);
	} else {
		// required post params is missing
		$response["error"] = TRUE;
		$response["error_msg"] = "Du har ingen resultater at hente";
		// echo no users JSON
    echo json_encode($response);
}} else{
	// required post params is missing
      $response["error"] = TRUE;
      $response["error_msg"] = "Informationen kunne ikke videresendes!";
	  echo json_encode($response);
}

?>
