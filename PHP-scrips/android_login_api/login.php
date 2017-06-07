<?php

/**
 * @author Ravi Tamada
 * @link http://www.androidhive.info/2012/01/android-login-and-registration-with-php-mysql-and-sqlite/ Complete tutorial
 */

require_once 'include/DB_Functions.php';
$db = new DB_Functions();

// json response array
$response = array("error" => FALSE);

if (isset($_POST['medlemsid']) && isset($_POST['adgangskode'])) {

    // modtager POST parametre: medlemsid og adgangskode
    $medlemsid = $_POST['medlemsid'];
    $adgangskode = $_POST['adgangskode'];

    // hent brugeroplysninger med medlemsid og adgangskode
    $user = $db->getUserByMedlemsidAndAdgangskode($medlemsid, $adgangskode);

    if ($user != false) {
        // use is found
        $response["error"] = FALSE;
        $response["uid"] = $user["unique_id"];
        $response["user"]["navn"] = $user["navn"];
        $response["user"]["medlemsid"] = $user["medlemsid"];
        $response["user"]["kategorisering"] = $user["kategorisering"];
		$response["v_msg"] = "Velkommen " .$user["navn"];
        echo json_encode($response);
    } else {
        // user is not found with the credentials
        $response["error"] = TRUE;
        $response["error_msg"] = "Det indtastede medlemsid eller adgangskode er forkert. Ved glemt log ind kontakt rehabiliteringssundhedspersonale.";
        echo json_encode($response);
    }
} else {
    // required post params is missing
    $response["error"] = TRUE;
    $response["error_msg"] = "Indtast medlemsid og adgangskode for log ind.";
    echo json_encode($response);
}
?>
