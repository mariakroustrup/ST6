<?php

/**
 * 
 * 
 */

require_once 'include/DB_Functions.php';
$db = new DB_Functions();

// json response array
$response = array("error" => FALSE);

//if (isset($_POST['adgangskode']) && isset($_POST['medlemsid'])) {

	
    // receiving the post params
    $medlemsid = "12345"; //$_POST['medlemsid'];
	$adgangskode = "1212"; //$_POST['adgangskode'];
        // create a new user
        $user = $db->storeUser($medlemsid, $adgangskode);
		//echo "$medlemsid";
        if ($user) {
            // user stored successfully
            $response["error"] = FALSE;
            $response["uid"] = $user["unique_id"];
            echo json_encode($response);
        } else {
            // user failed to store
            $response["error"] = TRUE;
            $response["error_msg"] = "Unknown error occurred in registration!";
            echo json_encode($response);
        }
 //   }
// else {
//    $response["error"] = TRUE;
//    $response["error_msg"] = "Required parameters (name, email or password) is missing!!!!!";
//    echo json_encode($response);
//}
?>

