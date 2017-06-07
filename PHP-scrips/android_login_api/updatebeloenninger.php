<?php

/**
 * 
 * 
 */

require_once 'include/DB_Functions2.php';
$db = new DB_Functions();

   
// json response array
$response = array("error" => FALSE);

//if (isset($_POST['medlemsid']) && isset($_POST['b_afstand']) && isset($_POST['b_tid']) && isset($_POST['b_antal']) && isset($_POST['b_kondi'])) {
	
    // receiving the post params
	$medlemsid = $_POST['medlemsid'];	
	$b_afstand = $_POST['b_afstand'];
	$b_tid = $_POST['b_tid'];
	$b_antal = $_POST['b_antal'];
	$b_kondi = $_POST['b_kondi'];
	
    // create a new user
        $user = $db->Updatebeloenninger($medlemsid, $b_afstand, $b_tid, $b_antal, $b_kondi);
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
//	}
// else {
//   $response["error"] = TRUE;
//   $response["error_msg"] = "Required parameters (name, email or password) is missing!!!!!";
//   echo json_encode($response);
//}
?>

