<?php

class DB_Functions {

    private $db;

    //put your code here
    // constructor
    function __construct() {
        require_once 'DB_Connect.php';
        // connecting to database
        $this->db = new DB_Connect();
        $this->db->connect();
    }

    // destructor
    function __destruct() {
        
    }

    /**
     * Storing new user
     * returns user details
     */
    public function storeUser($username, $password, $email, $firstname, $lastname, $city, $gender, $age, $avatar) {
		mysql_query("set names 'utf8'");
		$point = "0";
		$favorite = "";
        $result = mysql_query("INSERT INTO Users(Username, Password, Email, Firstname, Lastname, City, Gender, Age, Avatar, Favorite, Point) VALUES('$username', '$password', '$email', '$firstname', '$lastname', '$city', '$gender', '$age', '$avatar', '$favorite', '$point')");
        // check for successful store
        if ($result) {
            // get user details 
            $id = mysql_insert_id(); // last inserted id
            $result = mysql_query("SELECT * FROM Users WHERE ID = $id");
            // return user details
            return mysql_fetch_array($result);
        } else {
            return false;
        }
    }
	
	/**
     * Check user is existed or not
     */
	 
	 
	 public function getRestaurantByCity($city) {
		mysql_query("set names 'utf8'");
        $result = mysql_query("SELECT * FROM Restaurants WHERE City LIKE '%$city%'");
		$no_of_rows = mysql_num_rows($result);
        if ($no_of_rows > 0) {
            // restaurants existed 
            return $result;
        } else {
            // user not existed
            return false;
        }
    }
	
	public function getRestaurantByName($nameRestaurant) {
		mysql_query("set names 'utf8'");
        $result = mysql_query("SELECT * FROM Restaurants WHERE Name LIKE '%$nameRestaurant%'");
		$no_of_rows = mysql_num_rows($result);
        if ($no_of_rows > 0) {
            // restaurants existed 
            return $result;
        } else {
            // user not existed
            return false;
        }
    }
	
	public function getFavoriteRestaurant($restaurantID) {
		mysql_query("set names 'utf8'");
        $result = mysql_query("SELECT * FROM Restaurants WHERE ID = '$restaurantID'");
		$no_of_rows = mysql_num_rows($result);
        if ($no_of_rows > 0) {
            // restaurants existed 
            return $result;
        } else {
            // user not existed
            return false;
        }
	}
	
	public function getFoodByRestaurant($restaurantID) {
		mysql_query("set names 'utf8'");
		$result = mysql_query("SELECT * FROM Foods WHERE RestaurantID = '$restaurantID'");
		$no_of_rows = mysql_num_rows($result);
        if ($no_of_rows > 0) {
            // restaurants existed 
            return $result;
        } else {
            // user not existed
            return false;
        }
	}
	

    /**
     * Check user is existed or not
     */
    public function isUserExisted($username) {
		mysql_query("set names 'utf8'");
        $result = mysql_query("SELECT Username from Users WHERE Username = '$username'");
        $no_of_rows = mysql_num_rows($result);
        if ($no_of_rows > 0) {
            // user existed 
            return true;
        } else {
            // user not existed
            return false;
        }
    }
	
	public function getUserByUsernameAndPassword($username, $password) {
		mysql_query("set names 'utf8'");
        $result = mysql_query("SELECT * FROM Users WHERE Username = '$username' AND Password = '$password'");
        // check for result 
        $no_of_rows = mysql_num_rows($result);
        if ($no_of_rows > 0) {
            return mysql_fetch_array($result);
            
        } else {
            // user not found
            return false;
        }
    }
	
	public function changeUserInformation($username, $key, $value) {
		mysql_query("set names 'utf8'");
		$result = mysql_query("UPDATE Users SET $key = '".$value."' WHERE Username = '".$username."'");
		return $result;
	}
	
	public function getFeedbackByFood($foodID) {
		mysql_query("set names 'utf8'");
		$result = mysql_query("SELECT * FROM Feedback WHERE FoodID = '$foodID'");
        // check for result 
        $no_of_rows = mysql_num_rows($result);
        if ($no_of_rows > 0) {
            return $result;
            
        } else {
            // user not found
            return false;
        }
	}
	
	public function sendFeedback($username, $comment, $foodID) {
		mysql_query("set names 'utf8'");
		$result = mysql_query("INSERT INTO Feedback(Username, Comment, FeedbackAt, FoodID) VALUES('$username', '$comment', NOW(), '$foodID')");
		if ($result) {
            $id = mysql_insert_id(); // last inserted id
            $result = mysql_query("SELECT * FROM Feedback WHERE ID = $id");
            return mysql_fetch_array($result);
        } else {
            return false;
        }
	
	}
	
}

?>
