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
$response["traeninger"] = array();

// HER VIL VI HENTE INFORMATIONEN DER SENDES FRA APPEN
if (isset($_POST['medlemsid']) && isset($_POST['Man']) && isset($_POST['Tirs']) && isset($_POST['Ons']) && isset($_POST['Tors']) && isset($_POST['Fre']) && isset($_POST['Loer']) && isset($_POST['Soen'])){

	$medlemsid 	= $_POST['medlemsid'];
	$mandag 	= $_POST['Man'];
	$tirsdag 	= $_POST['Tirs'];
	$onsdag 	= $_POST['Ons'];
	$torsdag 	= $_POST['Tors'];
	$fredag 	= $_POST['Fre'];
	$loerdag 	= $_POST['Loer'];
	$soendag 	= $_POST['Soen'];

	// get all products from products table
	$result = mysql_query("SELECT tid_dato, kondi_tid FROM kondi WHERE medlemsid = $medlemsid AND tid_dato LIKE '$mandag%' or medlemsid = $medlemsid AND tid_dato LIKE '$tirsdag%' or medlemsid = $medlemsid AND tid_dato LIKE '$onsdag%' or medlemsid = $medlemsid AND tid_dato LIKE '$torsdag%' or medlemsid = $medlemsid AND tid_dato LIKE '$fredag%' or medlemsid = $medlemsid AND tid_dato LIKE '$loerdag%' or medlemsid = $medlemsid AND tid_dato LIKE '$soendag%'");

	// check for empty result
	if (mysql_num_rows($result) > 0) {
    
		// looping through all results
		// products node
    
		while ($row = mysql_fetch_array($result)) {
			// temp user array
			$traeninger = array();
			$traeninger["tid_dato"] = $row["tid_dato"];
			$traeninger["kondi_tid"] = $row["kondi_tid"];

			// push single product into final response array
			array_push($response["traeninger"], $traeninger);
		}
		
		$response["error"] = false;

    // echoing JSON response
    echo json_encode($response);
	} else {
		// required post params is missing
		$response["error"] = TRUE;
		$response["error_msg"] = "Du har ingen traeninger at hente";
		// echo no users JSON
    echo json_encode($response);
	
}} else{
	// required post params is missing
      $response["error"] = TRUE;
      $response["error_msg"] = "Informationen kunne ikke videresendes!";
	  echo json_encode($response);
}

?>
