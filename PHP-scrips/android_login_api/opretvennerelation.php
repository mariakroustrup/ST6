<?php

/**
 * 
 * 
 */

require_once 'include/DB_Functions2.php';
$db = new DB_Functions();

   
// json response array
$response = array("error" => FALSE);

if (isset($_POST['medlemsid']) && isset($_POST['ven_medlemsid'])) {

	
    // receiving the post params
    $medlemsid = $_POST['medlemsid'];
	$ven_medlemsid = $_POST['ven_medlemsid'];	
    // create a new user
        $user = $db->storeVennerelation($medlemsid, $ven_medlemsid);
		
        if ($user) {
            // user stored successfully
            $response["error"] = FALSE;
           // $response["medlemsid"] = $user["medlemsid"];
            echo json_encode($response);
        } else {
            // user failed to store
            $response["error"] = TRUE;
            $response["error_msg"] = "Vennerelation er ikke gemt";
            echo json_encode($response);
        }
	}
 else {
   $response["error"] = TRUE;
   $response["error_msg"] = "Required parameters (name, email or password) is missing!!!!!";
   echo json_encode($response);
}
?>

