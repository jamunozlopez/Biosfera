<?php
/*

This script was created by Bryan Helmig at midmodesign.com. It is licensed under http://creativecommons.org/licenses/by-nc-sa/3.0/us/. 
1)   A quick primer: place this on your server. Create a form like below:
--------------------------------------------------------------------------------------------------------
<form action="sendmail.php" method="post" id="contactForm">
	<p>Name:</p> <input type="text" name="name" value="" id="name" />
	<p>Email:</p> <input type="text" name="email" value="" id="email" />
	<p>Telephone:</p> <input type="text" name="tele" value="" id="tele" />
	<span style="display:none;"><p>Honeypot:</p> <input type="text" name="last" value="" id="last" /></span>
	<p>Message:</p> <textarea rows="5" name="message"></textarea>
	<input type="submit" value="Send Message" />
</form
--------------------------------------------------------------------------------------------------------
2)   This will work fine for a standard form. If you want ajax power, add this div above or below and hide it with css.
--------------------------------------------------------------------------------------------------------
<div class="message"><div id="alert"></div></div>
--------------------------------------------------------------------------------------------------------
3)   And add this to the head: Also download jquery-latest.pack.js and jquery.form.js and point to those appropriately.
--------------------------------------------------------------------------------------------------------
<script type="text/javascript" src="jquery-latest.pack.js"></script>
<script type="text/javascript" src="jquery.form.js"></script>
<script type="text/javascript">
$(document).ready(function() { 
var options = { 
target:        '#alert',
beforeSubmit:  showRequest,
success:       showResponse
}; 
$('#contactForm').ajaxForm(options); 
}); 
function showRequest(formData, jqForm, options) { 
var queryString = $.param(formData); 
return true; 
} 
function showResponse(responseText, statusText)  {  
} 
$.fn.clearForm = function() {
  return this.each(function() {
	var type = this.type, tag = this.tagName.toLowerCase();
	if (tag == 'form')
	  return $(':input',this).clearForm();
	if (type == 'text' || type == 'password' || tag == 'textarea')
	  this.value = '';
	else if (type == 'checkbox' || type == 'radio')
	  this.checked = false;
	else if (tag == 'select')
	  this.selectedIndex = -1;
  });
};
</script>
--------------------------------------------------------------------------------------------------------

Boom. There it is. 
*/

//        Who you want to recieve the emails from the form. (Hint: generally you.)
$sendto = 'biosfera@sauce.pntic.mec.es';

//        The subject you'll see in your inbox
$subject = 'Mensaje desde Proyecto Biosfera';

//        Message for the user when he/she doesn't fill in the form correctly.
$errormessage = 'Oops! Parece que hay algún problema. Sugerimos que...';

//        Message for the user when he/she fills in the form correctly.
$thanks = "¡Gracias por el email! ¡Responderemos lo más pronto posible!";

//        Message for the bot when it fills in in at all.
$honeypot = "You filled in the honeypot! If you're human, try again!";

//        Various messages displayed when the fields are empty.
$emptyname =  'Introduzca un nombre';
$emptyemail = 'Introduzca su dirección de correo electrónico';
$emptytele = 'Introduzca un asunto';
$emptymessage = 'Introduzca un mensaje';

//       Various messages displayed when the fields are incorrectly formatted.
$alertname =  'Introduzca su nombre con caracteres del alfabeto estándar';
$alertemail = 'Introduzca el email en este formato: <i>name@example.com</i>?';
$alerttele = 'Introduzca el asunto con caracteres del alfabeto estándar';
$alertmessage = "Asegúrese de que no esté usando paréntesis u otros caracteres conflictivos en el mensaje.";

// --------------------------- Thats it! don't mess with below unless you are really smart! ---------------------------------

//Setting used variables.
$alert = '';
$pass = 0;

// Sanitizing the data, kind of done via error messages first. Twice is better!  ;-)
function clean_var($variable) {
    $variable = strip_tags(stripslashes(trim(rtrim($variable))));
  return $variable;
}

//The first if for honeypot.
if ( empty($_REQUEST['last']) ) {

	// A bunch of if's for all the fields and the error messages.
if ( empty($_REQUEST['name']) ) {
	$pass = 1;
	$alert .= "<li>" . $emptyname . "</li>";
} elseif ( ereg( "[][{}()*+?.\\^$|]", $_REQUEST['name'] ) ) {
	$pass = 1;
	$alert .= "<li>" . $alertname . "</li>";
}
if ( empty($_REQUEST['email']) ) {
	$pass = 1;
	$alert .= "<li>" . $emptyemail . "</li>";
} elseif ( !eregi("^[_a-z0-9-]+(.[_a-z0-9-]+)*@[a-z0-9-]+(.[a-z0-9-]+)*(.[a-z]{2,3})$", $_REQUEST['email']) ) {
	$pass = 1;
	$alert .= "<li>" . $alertemail . "</li>";
}
if ( empty($_REQUEST['subject']) ) {
	$pass = 1;
	$alert .= "<li>" . $emptytele . "</li>";
/*} elseif ( !ereg( "\(?[0-9]{3}\)?[-. ]?[0-9]{3}[-. ]?[0-9]{4}", $_REQUEST['tele'] ) ) {*/
} elseif ( ereg( "[][{}()*+?.\\^$|]", $_REQUEST['subject'] ) ) {
	$pass = 1;
	$alert .= "<li>" . $alerttele . "</li>";
}
if ( empty($_REQUEST['message']) ) {
	$pass = 1;
	$alert .= "<li>" . $emptymessage . "</li>";
} elseif ( ereg( "[][{}()*+?\\^$|]", $_REQUEST['message'] ) ) {
	$pass = 1;
	$alert .= "<li>" . $alertmessage . "</li>";
}

	//If the user err'd, print the error messages.
	if ( $pass==1 ) {

		//This first line is for ajax/javascript, comment it or delete it if this isn't your cup o' tea.
	echo "<script>$(\".message\").hide(\"slow\").show(\"slow\"); </script>";
	echo "<b>" . $errormessage . "</b>";
	echo "<ul>";
	echo $alert;
	echo "</ul>";

	// If the user didn't err and there is in fact a message, time to email it.
	} elseif (isset($_REQUEST['message'])) {
	    
		//Construct the message.
	    $message = "From: " . clean_var($_REQUEST['name']) . "\n";
		$message .= "Email: " . clean_var($_REQUEST['email']) . "\n";
	    $message .= "Subject: " . clean_var($_REQUEST['subject']) . "\n";
	    $message .= "Message: \n" . clean_var($_REQUEST['message']);
	    $header = 'From:'. clean_var($_REQUEST['email']);
	    
//Mail the message - for production
		mail($sendto, $subject, $message, $header);
//This is for javascript, 
		echo "<script>$(\".message\").hide(\"slow\").show(\"slow\").animate({opacity: 1.0}, 4000).hide(\"slow\"); $(':input').clearForm() </script>";
		echo $thanks;
		die();

//Echo the email message - for development
		echo "<br/><br/>" . $message;

	}
	
//If honeypot is filled, trigger the message that bot likely won't see.
} else {
	echo "<script>$(\".message\").hide(\"slow\").show(\"slow\"); </script>";
	echo $honeypot;
}
?>
