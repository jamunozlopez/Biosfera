<?php
// Contact subject
$subject ="$subject"; 
// Details
$message="Mensaje enviado de $customer_mail : \n$detail"; 

// Mail of sender
$mail_from="$customer_mail"; 
// From 
$header="from: $name <$mail_from>"; 

// Enter your email address
$to ='jamunoz@platea.pntic.mec.es'; 

$send_contact=mail($to,$subject,$message,$header);

// Check, if message sent to your email 
// display message "We've recived your information"
if($send_contact){
$mensaje= "Hemos recibido tu informaci�n de contacto. En breve recibir�s una invitaci�n para colaborar en el blog.";
}
else {
$mensaje= "Se ha producido un error y no se ha podido enviar la informaci�n de contacto. Por favor, comprueba que los datos son correctos e int�ntalo de nuevo. Si el error persiste env�a un email a biosfera@sauce.pntic.mec.es";
}
?>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
	<title>Email de contacto</title>
</head>
<body>
	<h3><?php echo $mensaje; ?></h3>
	<a href="http://recursos.cnice.mec.es/biosfera/blog/" title="Volver al Blog de Biosfera">Volver al Blog de Biosfera</a>
</body>
</html>