$(document).ready(function() {
	
	loadSpecials();
	loadFeatured();
	
});
function loadSpecials() {
	
	clearSpecials();
	var start = 0;
	var indicate = $('.carousel-indicators') ;
	var inner = $('.carousel-inner');
	
	$.ajax ({
		type:'GET',
		url:'http://localhost:8080/special/allspecial',
		success: function(data, status) {
			$.each(data, function(index, special) {
				var title = special.title;
				var details = special.description;
				
				var listIndicate;
				var listInner;
				
				listIndicate = '<li data-target="#myCarousel" data-slide-to="';
					listIndicate += start + '"></li>';
					
				listInner = '<div class="item"><img src="images/sale.jpg">';
					listInner += '<div class="carousel-caption ">';
					listInner += '<h3 id="title">'+title+'</h3><p id="details">'+details+'</p></div>';
				
				start++;
				
				indicate.append(listIndicate);
				inner.append(listInner);
				
				$('.item').first().addClass('active');
				$('.carousel-indicators > li').first().addClass('active');
				$('#myCarousel').carousel();
			
			});
		}, 
		error: function() {
			$('#errorMessage')
				.append($('<li>')
				.attr({class: 'list-group-item list-group-item-danger'})
				.text('Error calling web service.  Please try again later.'));
		}
	});
}
function clearSpecials() {
	$('.carousel-indicators').empty() ;
	$('.carousel-inner').empty();
}

function loadFeatured() {

	clearFeaturedVehicles();
	
	var cardDeck = $('.card-deck');
	
	$.ajax ({
		type:'GET',
		url:'http://localhost:8080/vehicle/getfeatured',
		success: function(data, status) {
			$.each(data, function(index, vehicle) {
				var year = vehicle.year;
				var make = vehicle.make.makeName;
				var model = vehicle.model.modelName;
				var price = vehicle.salePrice;
				var image = vehicle.fileImg
				
				var card = '<div class="col-sm-4" id="homeCard" style="padding-right: 10px">';
					card += '<div class="card">';
					card += '<img class="card-img-top" id="imageCard" src="'+image+'" alt="Card img cap">';
					card += '<div class="card-body">';
					card += '<h5 class="card-title">' + year + ' ' + make + ' ' + model +'</h5>';
					card += '<p class="card-text">$' + price + '</p></div></div></div>';
				
				cardDeck.append(card);
			});
		}, 
		error: function() {
			$('#errorMessage')
				.append($('<li>')
				.attr({class: 'list-group-item list-group-item-danger'})
				.text('Error calling web service.  Please try again later.'));
		}
	});
}
	
function clearFeaturedVehicles() {
	$('.card-deck').empty();
}