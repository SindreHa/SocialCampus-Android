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
 
	// Viser JSON respons
echo json_encode($response);
?>