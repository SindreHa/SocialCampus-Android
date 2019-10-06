<?php
	$returverdi = "En feil oppstod.";
	$url="localhost"; // Bruker localhost fordi PHP-skriptet kjører på samme maskin som MySQL
	$bruker="h19_6120gr10"; // Bytt ut med ditt eget brukernavn
	$passord="pw.123456"; // og passord
	$db="h19_6120db10"; //Bruker fellesdatabasen u6120felles med tabellene for hobbyhuset
	$dblink=mysqli_connect($url, $bruker, $passord, $db); // Etablerer kobling til MySQL-databasen
	mysqli_set_charset($dblink, 'utf8');
//Sjekk om mottatt request er en GET med riktige action-parametre
	if (isset($_GET["action"])) {
				if (isset($_GET["group_id"]))
					$returverdi = get_post_list_fra_db($dblink, $_GET["group_id"]);
				else
					$returverdi = "URL mangler gruppe id";
	}
	
	mysqli_close($dblink); //Lukk databasekoblingen
	exit(json_encode($returverdi, JSON_UNESCAPED_UNICODE)); //returner data til klienten som en JSON array
	
	function get_post_list_fra_db($dblink, $group_id) {
		$sql = "SELECT p.id, p.title, p.content, p.likes, p.created, u.username, g.name AS groupName FROM  post AS p, user AS u, groups AS g WHERE p.user_id = u.id AND g.id = $group_id ORDER BY p.id DESC";
		$svar = mysqli_query($dblink, $sql);
		$vare_list=array();
		$row=0;
		while($rad=mysqli_fetch_assoc($svar)) {
			$post_list[$row] = $rad;
			$row++;
		}
	return $post_list;
	}
?>