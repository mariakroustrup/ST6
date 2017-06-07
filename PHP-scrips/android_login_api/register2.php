<?php

/**
 * 
 * 
 */

require_once 'include/DB_Functions2.php';
$db = new DB_Functions();

   
// json response array
$response = array("error" => FALSE);

if (isset($_POST['adgangskode']) && isset($_POST['medlemsid'])) {

	
    // receiving the post params
    $adgangskode = $_POST['adgangskode'];
	$medlemsid = $_POST['medlemsid'];	
    // create a new user
        $user = $db->storeAdgangskode($adgangskode, $medlemsid);
		//echo "$result";
        if ($user) {
            // user stored successfully
            $response["error"] = FALSE;
            $response["medlemsid"] = $user["medlemsid"];
            echo json_encode($response);
        } else {
            // user failed to store
            $response["error"] = TRUE;
            $response["error_msg"] = "Unknown error occurred in registration!";
            echo json_encode($response);
        }
	}
 else {
   $response["error"] = TRUE;
   $response["error_msg"] = "Required parameters (name, email or password) is missing!!!!!";
   echo json_encode($response);
}
?>

