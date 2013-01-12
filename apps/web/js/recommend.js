$(function () {

	var email = $.cookie(cookieName);
	if (email == null) window.location.href = "index.html";
	var encodedEmail = encodeURIComponent(email);

	$('#Logout').click(function() {
		$.removeCookie(cookieName);
		window.location.href = "index.html"
	});

	var requestUrl = '/api/get_ranked_items/?username=' + encodedEmail + /*'&lat=' + '374735.89' + '&lon=' + '-1222338.64' + '&radius=' + '40' + */'&page=';	
	var tileTemplate = $("#Template");	
	var currentPage = 1;
	$('#NextPage').attr('href', requestUrl + '2');

	function loadPage(page) {
		$.getJSON(requestUrl + page, function (json) {
			for (var i = 0; i < json.length; i++) {
				var newItem = $("#Template").clone();
				newItem.removeAttr('id');
				newItem.find('h2').text(json[i].title);
				newItem.data('item', json[i]);
				newItem.appendTo('#ScrollView');
			}

			$('.tile').click(function() {
				var currentTile = $(this);
				if (currentTile.hasClass('dontClick')) return;
				var data = $(this).data('item');
				var newTile = $('#Popup .PopupContainer').clone();

				newTile.data('currentLocuId', data.locu_id);
				newTile.children('.FoodName').text(data.title);
				newTile.children('.FoodDetails').text(data.description == "" ? "No description available." : data.description);
				newTile.children('.RestaurantName').text(data.venue.name);
				newTile.children('.RestaurantAddress').text(data.venue.address + ", " + data.venue.locality);
				newTile.children('.RestaurantWebsite a').attr('href', data.venue.website);
				newTile.children('.RestaurantWebsite a').text(data.venue.website);

				var children = 	currentTile.children();
				var parent = currentTile.parent();
				children.fadeToggle(function() {
					currentTile.append(newTile);

					currentTile.addClass('dontClick');

					/* rebind to the new close x and the yes/no buttons */
					newTile.children('#YesButton').click(function() {
						$.getJSON('/api/rate/?item=' + $(this).parent().data('currentLocuId') + '&username=' + encodedEmail + '&rating=like');
					});

					newTile.children('#NoButton').click(function() {
						$.getJSON('/api/rate/?item=' + $(this).parent().data('currentLocuId') + '&username=' + encodedEmail + '&rating=dislike');
					});

					$('.close-hitarea').click(function() {
						var tile = $(this).parent().parent();
						var newTile = $(this).parent();
						tile.removeClass('dontClick');
						newTile.remove();
						tile.children().fadeToggle();
						event.stopPropagation();
					});
				});

			});
		});
		currentPage += 1;
	}

	/* set up infinite scrolling */
	var didScroll = false;
	$(window).scroll(function () { 
		didScroll = true;
	});

	setInterval(function() {
    	if ( didScroll ) {
	        didScroll = false;
	        // Check your page position and then
	        // Load in more results
		    if ($(window).scrollTop() >= $(document).height() - $(window).height() - 5) {
		      loadPage(currentPage);
		    }
	    }
	}, 1500);


	/* load the first page of data initially */
	loadPage(currentPage);
});