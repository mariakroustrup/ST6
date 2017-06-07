<?php

/**
 * @author Ravi Tamada
 * @link http://www.androidhive.info/2012/01/android-login-and-registration-with-php-mysql-and-sqlite/ Complete tutorial
 */

require_once 'include/DB_Functions2.php';
$db = new DB_Functions();

// json response array
$response = array("error" => FALSE);

if (isset($_POST['ven_medlemsid'])) {

    // receiving the post params
    $ven_medlemsid = $_POST['ven_medlemsid'];


    // get the user by medlemsid and adgangskode
    $user = $db->getUserByVenMedlemsid($ven_medlemsid);

    if ($user != false) {
        // use is found
        $response["error"] = FALSE;
        $response["uid"] = $user["unique_id"];
        $response["user"]["navn"] = $user["navn"];
        $response["user"]["medlemsid"] = $user["medlemsid"];
       // $response["user"]["kategorisering"] = $user["kategorisering"];
        echo json_encode($response);
    } else {
        // user is not found with the credentials
        $response["error"] = TRUE;
        $response["error_msg"] = "MedlemsID'et findes ikke i databasen";
        echo json_encode($response);
    }
} else {
    // required post params is missing
   $response["error"] = TRUE;
    $response["error_msg"] = "Intet medlemsID er indtastet";
    echo json_encode($response);
}
?>

