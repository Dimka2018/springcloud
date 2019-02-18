$( document ).ready(() => {
	new WelcomeView().init();
});

class WelcomeView{
	
	init(){
		$(".registration-link").click((event) => {
	        event.preventDefault();
	        $(".log-in-form").removeClass("move-center");
	        $(".log-in-form").removeClass("scale-increase");
	        $(".log-in-form").addClass("move-left");
	        $(".log-in-form").addClass("scale-decrease");
	        $(".registration-form").removeClass("move-right");
	        $(".registration-form").removeClass("scale-decrease");
	        $(".registration-form").addClass("move-center");
	        $(".registration-form").addClass("scale-increase");
        });
		
		$(".log-in-link").click((event) => {
	        event.preventDefault();
	        $(".registration-form").removeClass("move-center");
	        $(".registration-form").removeClass("scale-increase");
	        $(".registration-form").addClass("move-right");
	        $(".registration-form").addClass("scale-decrease");
	        $(".log-in-form").removeClass("move-left");
	        $(".log-in-form").removeClass("scale-decrease");
	        $(".log-in-form").addClass("move-center");
	        $(".log-in-form").addClass("scale-increase");

        });
		
		$(".registration-form").submit(function(event){
		    event.preventDefault();
		    let user = new User($(".registration-login").val(), $(".registration-password").val());
			$.ajax({
				url: "user",
				type: "POST",
				contentType: "application/json",
				data: JSON.stringify(user),
				success: () => {
					    window.location = "main";
			    },
				error: (jqxhr, status) => {
					$(".registration-message").text(extractErrorMessage(jqxhr));
				}

			});
		});
		
		$(".log-in-form").submit(function(event){
			event.preventDefault();
			let user = new FormData(this);
			$.ajax({
				url: "user/session",
				type: "POST",
				data: user,
				contentType : false,
				processData : false,
				success: () => {
					    window.location = "main";
			    },
			    error: (jqxhr, status) => {
			    	$(".log-in-message").text(extractErrorMessage(jqxhr));
			    }

			});
		});
		
		function extractErrorMessage(jqxhr){
			let startPattern = "Message</b> ";
			let startPosition = jqxhr.responseText.indexOf(startPattern) + startPattern.length;
			let endPosition = jqxhr.responseText.indexOf("</p><p><b>Description");
			console.log(startPosition);
			console.log(endPosition);
			return jqxhr.responseText.substring(startPosition, endPosition);
		}
		
	}
	
}