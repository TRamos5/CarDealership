$(document).ready(function () {
    var id = getUrlVars()["id"];

    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/vehicle/getvehicle/' + id,
        success: function (data, status) {
            var year = data.year;
            var make = data.make.makeName;
            var model = data.model.modelName;
            var salePrice = data.salePrice;
            var fileImg = data.fileImg;
            var style = data.style;
            var trans = data.trans;
            var color = data.color;
            var interior = data.interior;
            var mileage = data.mileage;
            var vin = data.vin;
            var msrp = data.msrp;
            var description = data.description;

            var row = '<div class="content-box">';
            row += '<div class="row">';
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
            row += '</table>';
            row += '</div>';

            row += '</div>';
            row += '<div class="row">';
            row += '<div class="col-lg-12 col-md-12">';
            row += '<table>';
            row += '<tr>';
            row += '<th>Description:</th>';
            row += '<td>' + description + '</td>';
            row += '<td><a href="../home/contact.html?vin='+vin+'"><button type="button" class="btn btn-primary">Contact Us</button></a></td>';
            row += '</tr>';
            row += '</table>';
            row += '</div>';
            row += '</div>';

            row += '</div>';

            $('#detailResults').append(row);
        },
        error: function () {
            $('#errorMessages')
                    .append($('<li>')
                            .attr({class: 'list-group-item list-group-item-danger'})
                            .text('Error calling web service.  Please try again later.'));
        }
    });

});

function getUrlVars() {
    var vars = {};
    var parts = window.location.href.replace(/[?&]+([^=&]+)=([^&]*)/gi, function (m, key, value) {
        vars[key] = value;
    });
    return vars;
}