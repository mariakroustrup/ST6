<?php

/**
 * 
 * 
 */

require_once 'include/DB_Functions2.php';
$db = new DB_Functions();

   
// json response array
$response = array("error" => FALSE);

if (isset($_POST['medlemsid']) && isset($_POST['kondi_type']) && isset($_POST['helbredstilstand']) && isset($_POST['tid']) && isset($_POST['evaluering']) && isset($_POST['afstand'])){

	
    // receiving the post params

	$medlemsid = $_POST['medlemsid'];	
	$kondi_type = $_POST['kondi_type'];
	$helbredstilstand = $_POST['helbredstilstand'];
	$tid = $_POST['tid'];
	$evaluering = $_POST['evaluering'];
	$afstand = $_POST['afstand'];
    // create a new user
        $user = $db->storeKondi($medlemsid, $kondi_type, $helbredstilstand, $tid, $evaluering, $afstand);
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
	
} else {
 $response["error"] = TRUE;
 $response["error_msg"] = "Informationerne kunne ikke gemmes";
 echo json_encode($response);
}
?>

