$(document).ready(function() {
	loadSpecials();
});

function loadSpecials() {
	
	clearSpecials();
	
	var specials = $('#special');
	
	$.ajax ({
		type:'GET',
		url:'http://localhost:8080/special/allspecial',
		success: function(data, status) {
			$.each(data, function(index, special) {
				var title = special.title;
				var details = special.description;
				
				var card = '<div class="row" style="border-style: solid; margin-left: 25px;';
					card += 'margin-right: 25px;margin-top: 10px;margin-bottom: 10px;padding: 10px;">';
            		card += '<div class="col-md-2"><img src="../images/sale.jpg" alt="Special Image" ';
                    card += 'style="display: block;width: 100%;height: 100%;margin-top: auto;margin-bottom: auto;">';
					card += '</div><div class="col-md-10">';
					card += '<h2>'+title+'</h2><p> '+details+'</p></div></div>';
				
				specials.append(card);
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
	$('#special').empty();
}