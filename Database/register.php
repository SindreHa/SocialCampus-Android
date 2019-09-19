<?php
 
 
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
		
		
?>