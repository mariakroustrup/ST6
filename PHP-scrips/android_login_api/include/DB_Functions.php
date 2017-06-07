<?php

/**
 * @author Ravi Tamada
 * @link http://www.androidhive.info/2012/01/android-login-and-registration-with-php-mysql-and-sqlite/ Complete tutorial
 */

class DB_Functions {

    private $conn;

    // constructor
    function __construct() {
        require_once 'DB_Connect.php';
        // connecting to database
        $db = new Db_Connect();
        $this->conn = $db->connect();
    }

    // destructor
    function __destruct() {
        
    }
    
	
	 /**
     * Storing new user
     * returns user details
     */
    public function storeUser($medlemsid, $adgangskode) {
		
        //$uuid = uniqid('', true);

		echo "$medlemsid . $adgangskode";
        $stmt = $this->conn->prepare("INSERT INTO users(medlemsid, db_adgangskode) VALUES(?, ?)");
        $stmt->bind_param("ss", $medlemsid, $adgangskode);
		
        $result = $stmt->execute();
        $stmt->close();
		echo "$result";

        // check for successful store
        if ($result) {
            $stmt = $this->conn->prepare("SELECT * FROM users WHERE unique_id = ?");
            $stmt->bind_param("s", $uuid);
            $stmt->execute();
            $user = $stmt->get_result()->fetch_assoc();
            $stmt->close();

            return $user;
        } else {
            return false;
        }
    }
	
	/*
     * Storing new user
     * returns user details
     
    public function storeUser($adgangskode) {
        $uuid = uniqid('', true);
        //$hash = $this->hashSSHA($password);
        //$encrypted_password = $hash["encrypted"]; // encrypted password
        //$salt = $hash["salt"]; // salt

		//mysql_query("UPDATE users SET adgangskode = '$adgangskode' WHERE user_medlemsid = $user_medlemsid");
		
        $stmt = $this->conn->prepare("INSERT INTO users(unique_id, db_adgangskode) VALUES(?, ?)");
        $stmt->bind_param("ss", $uuid, $adgangskode);
        $result = $stmt->execute();
        $stmt->close();

        // check for successful store
        if ($result) {
            $stmt = $this->conn->prepare("SELECT * FROM users WHERE medlemsid = ?");
            $stmt->bind_param("s", $email);
            $stmt->execute();
            $user = $stmt->get_result()->fetch_assoc();
            $stmt->close();

            return $user;
        } else {
            return false;
        }
    }*/
	
    /**
     * Get user by medlemsid and adgangskode
     */
    public function getUserByMedlemsidAndAdgangskode($medlemsid, $adgangskode) {

        $stmt = $this->conn->prepare("SELECT * FROM users WHERE medlemsid = ?");

        $stmt->bind_param("s", $medlemsid);

        if ($stmt->execute()) {
            $user = $stmt->get_result()->fetch_assoc();
            $stmt->close();

            // verifying user adgangskode
            $db_adgangskode = $user['db_adgangskode'];
            $user_adgangskode = $adgangskode; //$this->checkhashSSHA($salt, $adgangskode);
            // check for adgangskode equality
            if ($db_adgangskode == $user_adgangskode) {
                // user authentication details are correct
                return $user;
            }
        } else {
            return NULL;
        }
    }

    /**
     * Check user is existed or not
     */
    public function isUserExisted($medlemsid) {
        $stmt = $this->conn->prepare("SELECT medlemsid from users WHERE medlemsid = ?");

        $stmt->bind_param("s", $medlemsid);

        $stmt->execute();

        $stmt->store_result();

        if ($stmt->num_rows > 0) {
            // user existed 
            $stmt->close();
            return true;
        } else {
            // user not existed
            $stmt->close();
            return false;
        }
    }
}

?>
