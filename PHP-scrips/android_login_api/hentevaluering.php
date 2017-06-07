<?php

/**
 * @author Ravi Tamada
 * @link http://www.androidhive.info/2012/01/android-login-and-registration-with-php-mysql-and-sqlite/ Complete tutorial
 */

require_once 'include/DB_Functions2.php';
$db = new DB_Functions();

// json response array
$response = array("error" => FALSE);

if (isset($_POST['medlemsid']) && isset($_POST['kondi_type']) && isset($_POST['helbredstilstand'])) {

    // receiving the post params
    $medlemsid = $_POST['medlemsid'];
	$kondi_type = $_POST['kondi_type'];
	$helbredstilstand = $_POST['helbredstilstand'];

    // get the user by medlemsid and adgangskode
    $eva = $db->getevaluering($medlemsid, $kondi_type, $helbredstilstand);

    if ($eva != false) {
        // use is found
        $response["error"] = FALSE;
        $response["eva"]["evaluering"] = $eva["evaluering"];
        echo json_encode($response);
    } else {
        // user is not found with the credentials
        $response["error"] = TRUE;
        $response["error_msg"] = "Du har ingen evaluering at vise";
        echo json_encode($response);
    }
} else {
    // required post params is missing
    $response["error"] = TRUE;
    $response["error_msg"] = "Fejl";
    echo json_encode($response);
}
?>

