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

	
	// Sjekker om brukeren allerede eksisterer
	if(!userExists($username)){
 
		// Henter en unik Salt
		$salt         = getSalt();
		
		// Generer en unik passord hash
		$passwordHash = password_hash(concatPasswordWithSalt($password,$salt),PASSWORD_DEFAULT);
		
		// Spørring for registrering av ny bruker
		$insertQuery   "INSERT INTO user (username, password, full_name, email) VALUES (?, ?, ?, ?)";
		if($stmt = $con->prepare($insertQuery)){
			$stmt->bind_param("ssss",$username,$fullName,$passwordHash,$salt);
			$stmt->execute();
			$response["status"] = 0;
			$response["message"] = "Bruker opprettet";
			$stmt->close();
		}
	}
	else{
		$response["status"] = 1;
		$response["message"] = "Bruker finnes allerede";
	}
}
else{
	$response["status"] = 2;
	$response["message"] = "Alle felt må fylles inn";
}
echo json_encode($response);
?>