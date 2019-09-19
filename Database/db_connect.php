<?php
// Definer variabler for phpmyadmin database
define('DB_SERVER', 'localhost');
define('DB_USERNAME', 'h19_6120gr10');
define('DB_PASSWORD', 'pw.123456');
define('DB_NAME', 'h19_6120db10');
 
// Kobler til mysql database med definerte variabler 
$link = mysqli_connect(DB_SERVER, DB_USERNAME, DB_PASSWORD, DB_NAME);

if(session_id() == '') {
    session_start();
}

?>

