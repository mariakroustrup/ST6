<?php

/**
 * @author Ravi Tamada
 * @link http://www.androidhive.info/2012/01/android-login-and-registration-with-php-mysql-and-sqlite/ Complete tutorial
 */

require_once 'include/DB_Functions2.php';
$db = new DB_Functions();

// json response array
$response = array("error" => FALSE);

if (isset($_POST['medlemsid'])) {

    // receiving the post params
    $medlemsid = $_POST['medlemsid'];

    // get the user by medlemsid and adgangskode
    $beloenninger = $db->getbeloenninger($medlemsid);

    if ($beloenninger != false) {
        // use is found
        $response["error"] = FALSE;
        $response["beloenninger"]["b_afstand"] = $beloenninger["b_afstand"];
        $response["beloenninger"]["b_tid"] = $beloenninger["b_tid"];
        $response["beloenninger"]["b_antal"] = $beloenninger["b_antal"];
		$response["beloenninger"]["b_kondi"] = $beloenninger["b_kondi"];
        echo json_encode($response);
    } else {
        // user is not found with the credentials
        $response["error"] = TRUE;
        $response["error_msg"] = "Du har ingen resultater at vise";
        echo json_encode($response);
    }
} else {
    // required post params is missing
    $response["error"] = TRUE;
    $response["error_msg"] = "Fejl";
    echo json_encode($response);
}
?>

