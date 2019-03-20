$(document).ready(function () {
    loadSearchParameters();

    $('#search-button').click(function (event) {

        $('#searchResults').empty();

        $.ajax({
            type: 'POST',
            url: 'http://localhost:8080/vehicle/search',
            data: JSON.stringify({
                stringSearch: $('#select-item').val(),
                minPrice: $('#priceMin').val(),
                maxPrice: $('#priceMax').val(),
                minYear: $('#yearMin').val(),
                maxYear: $('#yearMax').val(),
                newVehicle: null
            }),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'

            },
            'dataType': 'json',
            success: function (data, status) {
                $.each(data, function (index, vehicle) {
                    var year = vehicle.year;
                    var make = vehicle.make.makeName;
                    var model = vehicle.model.modelName;
                    var salePrice = vehicle.salePrice;
                    var fileImg = vehicle.fileImg;
                    var style = vehicle.style;
                    var trans = vehicle.trans;
                    var color = vehicle.color;
                    var interior = vehicle.interior;
                    var mileage = vehicle.mileage;
                    var vin = vehicle.vin;
                    var msrp = vehicle.msrp;
                    var id = vehicle.vehicleId;

                    var row = '<div class="row">';
                    row += '<h4>' + year + ' ' + make + ' ' + model + '</h4>';
                    row += '<div class="col-lg-3 col-md-3">';
                    row += '<img src="../' + fileImg + '" alt="" style="width:70%;">';
                    row += '</div>';

                    row += '<div class="col-lg-3 col-md-3">';
                    row += '<table>';
                    row += '<tr>';
                    row += '<th>Body Style:</th>';
                    row += '<td>' + style + '</td>';
                    row += '</tr>';
                    row += '<tr>';
                    row += '<th>Trans:</th>';
                    row += '<td>' + trans + '</td>';
                    row += '</tr>';
                    row += '<tr>';
                    row += '<th>Color:</th>';
                    row += '<td>' + color + '</td>';
                    row += '</tr>';
                    row += '</table>';
                    row += '</div>';

                    row += '<div class="col-lg-3 col-md-3">';
                    row += '<table>';
                    row += '<tr>';
                    row += '<th>Interior:</th>';
                    row += '<td>' + interior + '</td>';
                    row += '</tr>';
                    row += '<tr>';
                    row += '<th>Mileage:</th>';
                    row += '<td>' + mileage + '</td>';
                    row += '</tr>';
                    row += '<tr>';
                    row += '<th>VIN:</th>';
                    row += '<td>' + vin + '</td>';
                    row += '</tr>';
                    row += '</table>';
                    row += '</div>';

                    row += '<div class="col-lg-3 col-md-3">';
                    row += '<table>';
                    row += '<tr>';
                    row += '<th>Sales Price:</th>';
                    row += '<td>' + salePrice + '</td>';
                    row += '</tr>';
                    row += '<tr>';
                    row += '<th>MSRP:</th>';
                    row += '<td>' + msrp + '</td>';
                    row += '</tr>';
                    row += '<tr>';
                    row += '<th></th>';
                    row += '<td><a href="editVehicle.html?id='+id+'"><button type="button" class="btn btn-primary">Edit</button></a></td>';
                    row += '</tr>';
                    row += '</table>';
                    row += '</div>';

                    row += '</div>';

                    $('#searchResults').append(row);
                });
            },
            error: function () {
                $('#errorMessage')
                        .append($('<li>')
                                .attr({class: 'list-group-item list-group-item-danger'})
                                .text('Error calling web service.  Please try again later.'));
            }
        });
    });

});

function loadSearchParameters() {
    var date = new Date();
    var currentYear = date.getFullYear();
    var years = currentYear - 2000;
    var year = 2000;
    $('#yearMin').append("<option value="+1+">N/A</option>");
    $('#yearMax').append("<option value="+1+">N/A</option>");
    $('#priceMin').append("<option value="+1+">N/A</option>");
    $('#priceMax').append("<option value="+1+">N/A</option>");

    for (var i = 1; i <= years + 2; i++) {
        $('#yearMin').append("<option value = " + (i + 1) + ">" + year + "</option>");
        $('#yearMax').append("<option value = " + (i + 1) + ">" + year + "</option>");
        year++;
    }
    

    var money = 1000;
    for (var i = 1; i <= 1000; i++) {
        $('#priceMin').append("<option value = " + (i + 1) + ">" + money + "</option>");
        $('#priceMax').append("<option value = " + (i + 1) + ">" + money + "</option>");
        money += 1000;
    }
    
}