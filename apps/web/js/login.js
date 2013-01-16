$(function () {

	function loggedInRedirect() {
		window.location.href = "recommend.html";
	}

	function firstTimeRedirect() {
		window.location.href = "recommend.html"
	}

	function registerUser(email) {
		$.getJSON(server + "/api/register_user/?username=" + encodeURIComponent(email),
			function (data) {
				if (data.registered) {
					saveEmail(email);
					firstTimeRedirect();
				}
				else {
					$('#FailedToRegister').fadeIn(300);
				}
		});
	}

	function saveEmail(email) {
		// Persist the email as a cookie.
		$.cookie(cookieName, email);
	}

	/* Sign in process. Register if necessary. Redirect to the proper place. */
	$('#SignInButton').click( function() {
		var email = $('#EmailAddress').val();
		
		if (email == "") {
			$('#EmptyEmailAlert').fadeIn(300);	
		} 
		else {
			$('#EmptyEmailAlert').fadeOut(300);

			$.getJSON(
				server + "/api/user/?username=" + encodeURIComponent(email),
				function(data) {
					if (data.registered) {
						saveEmail(email);
						loggedInRedirect();
					}
					else {
						registerUser(email);
					}
				});
		}
		return false; // Prevent actual form submission.
	});


	// Check if we're already "logged in".
	if ($.cookie(cookieName) != null) loggedInRedirect();
});