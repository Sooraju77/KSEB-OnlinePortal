	function validateLogin() {
		if (document.login.username.value == "") {
			alert("Please enter your username");
			document.login.username.focus();
			return false;
		}
		if (document.login.password.value == "") {
			alert("Please enter your password");
			document.login.password.focus();
			return false;
		}
		if (document.login.usertype.value == "") {
			alert("Please select usertype");
			document.login.usertype.focus();
			return false;
		}

	}