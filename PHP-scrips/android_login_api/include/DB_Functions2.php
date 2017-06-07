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
    public function storeAdgangskode($adgangskode, $medlemsid) {
		
        //$uuid = uniqid('', true);
		//echo "$medlemsid . $adgangskode";
        $stmt = $this->conn->prepare("UPDATE users SET db_adgangskode = '$adgangskode' WHERE medlemsid = '$medlemsid'");
        $stmt->bind_param("ss", $adgangskode, $medlemsid);
        $result = $stmt->execute();
        $stmt->close();

        // check for successful store
        if ($result) {
            $stmt = $this->conn->prepare("SELECT * FROM users WHERE medlemsid = ? ");
            //$stmt->bind_param("s", $medlemsid);
            $stmt->execute();
            $user = $stmt->get_result()->fetch_assoc();
            $stmt->close();

            return $user;
        } else {
            return false;
        }
    }


	public function storeKategorisering($ABCD, $medlemsid) {
		
        $stmt = $this->conn->prepare("UPDATE users SET kategorisering = '$ABCD' WHERE medlemsid = '$medlemsid'");
        $stmt->bind_param("ss", $ABCD, $medlemsid);
        $result = $stmt->execute();
        $stmt->close();

        // check for successful store
        if ($result) {
            $stmt = $this->conn->prepare("SELECT * FROM users WHERE medlemsid = ?");
            $stmt->bind_param("s", $medlemsid);
            $stmt->execute();
            $user = $stmt->get_result()->fetch_assoc();
            $stmt->close();

            return $user;
        } else {
            return false;
        }
    }
	
	public function Updatebeloenninger($medlemsid, $b_afstand, $b_tid, $b_antal, $b_kondi){
		$stmt = $this->conn->prepare("SELECT * FROM beloenninger WHERE medlemsid = ?");
        $stmt->bind_param("s", $medlemsid);
        
		if ($stmt->execute()){
			$user = $stmt->get_result()->fetch_assoc();
            $stmt->close();
			$db_medlemsid = $user['medlemsid'];
			$user_medlemsid = $medlemsid;
			
			if ($db_medlemsid == $user_medlemsid) {
				$stmt = $this->conn->prepare("UPDATE beloenninger SET b_afstand = '$b_afstand', b_tid = '$b_tid', b_antal = '$b_antal', b_kondi = '$b_kondi' WHERE medlemsid = '$medlemsid'");
				$stmt->execute();
				$stmt->close();
				return $user;
				
		} else{
			$stmt = $this->conn->prepare("INSERT INTO beloenninger (medlemsid, b_afstand, b_tid, b_antal, b_kondi) VALUES (?,?,?,?,?)");
			$stmt->bind_param("sssss", $medlemsid, $b_afstand, $b_tid, $b_antal, $b_kondi);
			$stmt->execute();
            $stmt->close();
			return $user;
		}}
		else {
			return NULL;
		}
	}
	
	
	
	public function storeVennerelation($medlemsid, $ven_medlemsid) {
		$stmt = $this->conn->prepare("INSERT INTO vennerelation(medlemsid, ven_medlemsid) VALUES(?,?)");
        $stmt->bind_param("ss", $medlemsid, $ven_medlemsid);
        $result = $stmt->execute();
        $stmt->close();

        // check for successful store
        if ($result) {
            $stmt = $this->conn->prepare("SELECT ven_medlemsid FROM vennerelation WHERE medlemsid = ? AND ven_medlemsid = ? ");
            $stmt->bind_param("ss", $medlemsid, $ven_medlemsid);
            $stmt->execute();
            $user = $stmt->get_result()->fetch_assoc();
            $stmt->close();

            return $user;
        } else {
            return false;
        }
	}
	
	public function FjernVennerelation($medlemsid, $ven_medlemsid) {
		$stmt = $this->conn->prepare("DELETE FROM vennerelation WHERE medlemsid = ? AND ven_medlemsid = ?");
        $stmt->bind_param("ss", $medlemsid, $ven_medlemsid);
        $result = $stmt->execute();
        $stmt->close();

        // check for successful store
        if ($result) {
            $stmt = $this->conn->prepare("SELECT ven_medlemsid FROM vennerelation WHERE medlemsid = ? AND ven_medlemsid = ? ");
            $stmt->bind_param("ss", $medlemsid, $ven_medlemsid);
            $stmt->execute();
            $user = $stmt->get_result()->fetch_assoc();
            $stmt->close();

            return $user;
        } else {
            return false;
        }
		
	}
	
	
	// Dette skal laves om på et tidspunkt!
	public function storeKondi($medlemsid, $kondi_type, $helbredstilstand, $tid, $evaluering, $afstand) {
		
        $stmt = $this->conn->prepare("INSERT INTO kondi(medlemsid, kondi_type, helbredstilstand, kondi_tid, evaluering, afstand_km) VALUES(?,?,?,?,?,?)"); 
        $stmt->bind_param("ssssss", $medlemsid, $kondi_type, $helbredstilstand, $tid, $evaluering, $afstand);
        $result = $stmt->execute();
        $stmt->close();

        // check for successful store
        if ($result) {
            $stmt = $this->conn->prepare("SELECT * FROM kondi WHERE medlemsid = ?");
            $stmt->bind_param("s", $medlemsid);
            $stmt->execute();
            $user = $stmt->get_result()->fetch_assoc();
            $stmt->close();

            return $user;
        } else {
            return false;
        }
    }
	

    /**
     * Get user by medlemsid and adgangskode
     */
    public function dbHentBruger($medlemsid, $adgangskode) {

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
	
	   public function getbeloenninger($medlemsid) {
        $stmt = $this->conn->prepare("SELECT * FROM beloenninger WHERE medlemsid = ?");

        $stmt->bind_param("s", $medlemsid);

        if ($stmt->execute()) {
            $beloenninger = $stmt->get_result()->fetch_assoc();
            $stmt->close();
			return $beloenninger;
        } else {
            return NULL;
        }
    }
	
	
	public function getvenbeloenninger($ven_medlemsid) {

        $stmt = $this->conn->prepare("SELECT * FROM beloenninger WHERE medlemsid = ?");

        $stmt->bind_param("s", $ven_medlemsid);

        if ($stmt->execute()) {
            $beloenninger = $stmt->get_result()->fetch_assoc();
            $stmt->close();
			return $beloenninger;
        } else {
            return NULL;
        }
    }
	
	public function getevaluering($medlemsid, $kondi_type, $helbredstilstand){
	 $stmt = $this->conn->prepare("SELECT evaluering FROM kondi WHERE medlemsid = ? AND kondi_type = ? AND helbredstilstand = ? ORDER BY tid_dato DESC LIMIT 1");

        $stmt->bind_param("sss", $medlemsid, $kondi_type, $helbredstilstand);
        if ($stmt->execute()) {
            $eva = $stmt->get_result()->fetch_assoc();
            $stmt->close();
			return $eva;
        } else {
            return NULL;
        }
	
	}
	
	
	   public function getb_resultater($medlemsid) {

        $stmt = $this->conn->prepare("SELECT COUNT(tid_dato), SUM(afstand_km), SUM(kondi_tid) FROM kondi WHERE medlemsid = ?");
        $stmt->bind_param("s", $medlemsid);

        if ($stmt->execute()) {
            $b_resultater = $stmt->get_result()->fetch_assoc();
            $stmt->close();
			return $b_resultater;
        } else {
            return NULL;
        }
    }
	
	
	
	public function getUserByVenMedlemsid($ven_medlemsid) {	
		$stmt = $this->conn->prepare("SELECT * FROM users WHERE medlemsid = ?");

        $stmt->bind_param("s", $ven_medlemsid);

        if ($stmt->execute()) {
            $user = $stmt->get_result()->fetch_assoc();
            $stmt->close();
             return $user;
        } else {
            return NULL;
        }
	   }
	   
	   
   
	   
	public function getCheckVennerelation($medlemsid, $ven_medlemsid) {
	 $stmt = $this->conn->prepare("SELECT ven_medlemsid FROM vennerelation WHERE medlemsid = ? AND ven_medlemsid = ?");

        $stmt->bind_param("ss", $medlemsid, $ven_medlemsid);

        if ($stmt->execute()) {
            $user = $stmt->get_result()->fetch_assoc();
            $stmt->close();

            // verifying vennerelation
            $db_ven_medlemsid = $user['ven_medlemsid'];
            //$user_adgangskode = $adgangskode; //$this->checkhashSSHA($salt, $adgangskode);
            // check vennerelation equality
            if ($db_ven_medlemsid == $ven_medlemsid) {
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
