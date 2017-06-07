<?php

/**
 * @author Ravi Tamada
 * @link http://www.androidhive.info/2012/01/android-login-and-registration-with-php-mysql-and-sqlite/ Complete tutorial
 */

require_once 'include/DB_Functions2.php';
$db = new DB_Functions();

// json response array
$response = array("error" => FALSE);

if (isset($_POST['medlemsid']) && isset($_POST['ven_medlemsid'])) {

    // receiving the post params
    $medlemsid = $_POST['medlemsid'];
    $ven_medlemsid = $_POST['ven_medlemsid'];

    
    $user = $db->getCheckVennerelation($medlemsid, $ven_medlemsid);

    if ($user != false) {
        // use is found
        $response["error"] = FALSE;
        echo json_encode($response);
    } else {
        // user is not found with the credentials
        $response["error"] = TRUE;
        //$response["error_msg"] = "I er pisse meget ikke venner";
        echo json_encode($response);
    }
} else {
    // required post params is missing
    $response["error"] = TRUE;
    $response["error_msg"] = "Der er ingen værdier...";
    echo json_encode($response);
}
?>

