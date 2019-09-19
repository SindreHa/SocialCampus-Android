<?php
//Returnerer eventuelle feilmeldinger
$response = array();
include 'db_connect.php';
include 'functions.php';
 
// Får tak i input parameterene og konverterer JSON til array
$inputJSON = file_get_contents('php://input');
$input = json_decode($inputJSON, TRUE); 
 
// Sjekker om alle 4 parametere er fylt inn
if(isset($input['username']) && isset($input['password']) && && isset($input['full_name'])isset($input['email'])){
	$username = $input['username'];
	$password = $input['password'];
	$fullName = $input['full_name'];
    $email    = $input['email'];
 
	if($stmt = $con->prepare($query)){
		$stmt->bind_param("s",$username);
		$stmt->execute();
		$stmt->bind_result($fullName,$passwordHashDB,$salt);
		if($stmt->fetch()){
			
            //Validere passord
			if(password_verify(concatPasswordWithSalt($password,$salt),$passwordHashDB)){
				$response["status"] = 0;
				$response["message"] = "Du er logget inn";
				$response["full_name"] = $fullName;
			}
		else{
			$response["status"] = 1;
			$response["message"] = "Feil kombinasjon av brukernavn/passord";
			}
		}
		else{
			$response["status"] = 1;
			$response["message"] = "Feil kombinasjon av brukernavn/passord";
		}
		
		$stmt->close();
	}
}
else{
	$response["status"] = 2;
	$response["message"] = "Alle felt må fylles inn";
}
//Viser JSON respons
echo json_encode($response);
?>