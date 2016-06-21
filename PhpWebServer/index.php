<?php
header("Content-Type: text/html;charset=UTF-8");
/**
 * File to handle all API requests
 * Accepts GET and GET
 * 
 * Each request will be identified by TAG
 * Response will be JSON data

  /**
 * check for GET request 
 */
if (isset($_POST['tag']) && $_POST['tag'] != '') {
    // get tag
    $tag = $_POST['tag'];

    // include db handler
    require_once 'include/DB_Functions.php';
    $db = new DB_Functions();

    // response Array
    $response = array("tag" => $tag, "success" => 0, "error" => 0);

    // check for tag type
    if ($tag == 'login') {
        // Request type is check Login
        $username = $_POST['username'];
        $password = $_POST['password'];
		$user = $db->getUserByUsernameAndPassword($username, $password);
        if ($user != false) {
            // user found
            // echo json with success = 1
            $response["success"] = 1;
            $response["id"] = $user["ID"];
            $response["user"]["username"] = $user["Username"];
            $response["user"]["password"] = $user["Password"];
            $response["user"]["email"] = $user["Email"];
            $response["user"]["firstname"] = $user["Firstname"];
			$response["user"]["lastname"] = $user["Lastname"];
			$response["user"]["city"] = $user["City"];
			$response["user"]["gender"] = $user["Gender"];
			$response["user"]["age"] = $user["Age"];
			$response["user"]["avatar"] = $user["Avatar"];
			$response["user"]["point"] = $user["Point"];
			$response["user"]["favorite"] = $user["Favorite"];
			
			
            echo json_encode($response);
        } else {
            // user not found
            // echo json with error = 1
            $response["error"] = 1;
            $response["error_msg"] = "Incorrect username or password!";
            echo json_encode($response);
        }

    } 
	else if ($tag == 'register') {
        // Request type is Register new user
        $username = $_POST['username'];
		$password = $_POST['password'];
        $email = $_POST['email'];
		$firstname = $_POST['firstname'];
		$lastname = $_POST['lastname'];
		$city = $_POST['city'];
		$gender = $_POST['gender'];
		$age = $_POST['age'];
		$avatar = $_POST['avatar'];

		

        // check if user is already existed
        if ($db->isUserExisted($username)) {
            // user is already existed - error response
            $response["error"] = 2;
            $response["error_msg"] = "User already existed";
            echo json_encode($response);
        } 
		else {
            // store user
            $user = $db->storeUser($username, $password, $email, $firstname, $lastname, $city, $gender, $age, $avatar);
            if ($user) {
                // user stored successfully
                $response["success"] = 1;
                $response["user"]["username"] = $user["Username"];
                $response["user"]["password"] = $user["Password"];
                $response["user"]["email"] = $user["Email"];
                $response["user"]["firstname"] = $user["Firstname"];
				$response["user"]["lastname"] = $user["Lastname"];
				$response["user"]["city"] = $user["City"];
				$response["user"]["gender"] = $user["Gender"];
				$response["user"]["age"] = $user["Age"];
				$response["user"]["avatar"] = $user["Avatar"];
				$response["user"]["favorite"] = $user["Favorite"];
				$response["user"]["point"] = $user["Point"];
				
                echo json_encode($response);
            } 
			else {
                // user failed to store
                $response["error"] = 1;
                $response["error_msg"] = "Error occured in Registartion";
                echo json_encode($response);
            }
        }
    } 
	else if ($tag == 'get_restaurant_by_city') {
		$city = $_POST['city'];
		$restaurants = $db->getRestaurantByCity($city);
        if ($restaurants != false) {
  
			while ($row = mysql_fetch_assoc($restaurants)) {
				$restaurant_array[] = $row;	
			}
			echo json_encode($restaurant_array);
			
        } else {
            // user not found
            // echo json with error = 1
            $response["error"] = 1;
            $response["error_msg"] = "No restaurant found!";
            echo json_encode($response);
        }
		
		
	}
	
	else if ($tag == 'get_food_by_restaurant') {
		$restaurantID = $_POST['restaurantID'];
		$foods = $db->getFoodByRestaurant($restaurantID);
        if ($foods != false) {
  
			while ($row = mysql_fetch_assoc($foods)) {
				$food_array[] = $row;	
			}
			echo json_encode($food_array);
			
        } else {
            // user not found
            // echo json with error = 1
            $response["error"] = 1;
            $response["error_msg"] = "No food found!";
            echo json_encode($response);
        }
	}
	
	else if ($tag == 'change_info_user') {
		$username = $_POST['username'];
		$key = $_POST['key'];
		$value = $_POST['value'];
		$result = $db->changeUserInformation($username, $key, $value);
		echo $result;
	}
	
	else if ($tag == 'get_feedback_by_food') {
		$foodID = $_POST['foodID'];
		$feedbacks = $db->getFeedbackByFood($foodID);
        if ($feedbacks != false) {
  
			while ($row = mysql_fetch_assoc($feedbacks)) {
				$feedback_array[] = $row;	
			}
			echo json_encode($feedback_array);
			
        } else {
            // user not found
            // echo json with error = 1
            $response["error"] = 1;
            $response["error_msg"] = "No feedback found!";
            echo json_encode($response);
        }
	}
	
	else if ($tag == 'send_feedback') {
		$username = $_POST['username'];
		$comment = $_POST['comment'];
        $foodID = $_POST['foodID'];
		$feedback = $db->sendFeedback($username, $comment, $foodID);
		if ($feedback) {
			$response["success"] = 1;
            $response["Feedback"]["ID"] = $feedback["ID"];
            $response["Feedback"]["Username"] = $feedback["Username"];
            $response["Feedback"]["Comment"] = $feedback["Comment"];
            $response["Feedback"]["FoodID"] = $feedback["FoodID"];
			$response["Feedback"]["FeedbackAt"] = $feedback["FeedbackAt"];
			echo json_encode($response);
		}
		else {
			$response["error"] = 1;
            $response["error_msg"] = "Insert feedback failed!";
            echo json_encode($response);
		}
	}
	
	else if ($tag == 'get_favorite') {
		$restaurantID = $_POST['restaurantID'];
		$restaurants = $db->getFavoriteRestaurant($restaurantID);
        if ($restaurants != false) {
  
			while ($row = mysql_fetch_assoc($restaurants)) {
				$restaurant_array[] = $row;	
			}
			echo json_encode($restaurant_array);
			
        } else {
            // user not found
            // echo json with error = 1
            $response["error"] = 1;
            $response["error_msg"] = "No restaurant found!";
            echo json_encode($response);
        }
	}
	
	else if ($tag == 'get_restaurant_by_name') {
		$nameRestaurant = $_POST['nameRestaurant'];
		$restaurants = $db->getRestaurantByName($nameRestaurant);
        if ($restaurants != false) {
  
			while ($row = mysql_fetch_assoc($restaurants)) {
				$restaurant_array[] = $row;	
			}
			echo json_encode($restaurant_array);
			
        } else {
            // user not found
            // echo json with error = 1
            $response["error"] = 1;
            $response["error_msg"] = "No restaurant found!";
            echo json_encode($response);
        }
	}
	
	else {
        echo "Invalid Request";
    }
}
else {
    echo "Access Denied";
}
?>
