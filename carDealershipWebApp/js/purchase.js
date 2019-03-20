$(document).ready(function () {
    var id = getUrlVars()["id"];

    var vehicleDisplay = $('#vehicle');
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/vehicle/getvehicle/' + id,
        success: function (vehicle, status) {

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
            var description = vehicle.description;
            
            var priceLow = msrp * .95;


            var makeId = vehicle.make.makeId;
            var modelId = vehicle.model.modelId;
            var userId = vehicle.user.userId;
            var row = '<div class="content-box">\n\
                                <h4>' + year + ' ' + make + ' ' + model + '</h4>\n\
                                <div class="col-lg-3 col-md-3">\n\
                                    <img src="../' + fileImg + '" alt="" style="width:70%;">\n\
                                </div>\n\
                                <div class="row">\n\
                                    <div class="col-lg-3 col-md-3">\n\
                                        <table>\n\
                                            <tr>\n\
                                                <th>Body Style:</th>\n\
                                                <td>' + style + '</td>\n\
                                            </tr>\n\
                                            <tr>\n\
                                                <th>Trans:</th>\n\
                                                <td>' + trans + '</td>\n\
                                            </tr>\n\
                                            <tr>\n\
                                                <th>Color:</th>\n\
                                                <td>' + color + '</td>\n\
                                            </tr>\n\
                                        </table>\n\
                                    </div>\n\
                                    <div class="col-lg-3 col-md-3">\n\
                                        <table>\n\
                                            <tr>\n\
                                                <th>Interior:</th>\n\
                                                <td>' + interior + '</td>\n\
                                            </tr>\n\
                                            <tr>\n\
                                                <th>Mileage:</th>\n\
                                                <td>' + mileage + '</td>\n\
                                            </tr>\n\
                                            <tr>\n\
                                                <th>VIN:</th>\n\
                                                <td>' + vin + '</td>\n\
                                            </tr>\n\
                                        </table>\n\
                                    </div>\n\
                                    <div class="col-lg-3 col-md-3">\n\
                                        <table>\n\
                                            <tr>\n\
                                                <th>Sales Price:</th>\n\
                                                <td>' + salePrice + '</td>\n\
                                            </tr>\n\
                                            <tr>\n\
                                                <th>MSRP:</th>\n\
                                                <td>' + msrp + '</td>\n\
                                            </tr>\n\
                                            <tr>\n\
                                                <th></th>\n\
                                                <td></td>\n\
                                            </tr>\n\
                                        </table>\n\
                                    </div>\n\
                                </div>\n\
                                <div class="row" style="margin-top: 20px;">\n\
                                    <div class="col-lg-3"></div>\n\
                                    <div calss="col-lg-9">\n\
                                        <table>\n\
                                            <th style="text-align: right;">Description:</th>\n\
                                            <td style="text-align: left;">' + description + '</td>\n\
                                        </table>\n\
                                    </div>\n\
                                </div>\n\
                                <div class="row">\n\
                                    <div class="col-md-9">\n\
                                    </div>\n\
                                </div>\n\
                            </div>';
            $('#vehicle').append(row);

            var cashRow = '<label for="userPrice">Price</label> <input type="number" min="' + priceLow + '" max="' + msrp + '" class="form-control inputLength" id="userPrice" required>';


            $('#detailResults').append(row);
            $('#cashInput').append(cashRow);

            var submitRow = '<button type="button" onclick="buyVehicle(' + id + ',' + userId + ')">Save</button>';
            $('#submitInput').append(submitRow);
        },
        error: function (response) {
            $('#errorMessage')
                    .append($('<li>')
                            .attr({class: 'list-group-item list-group-item-danger'})
                            .text(response.responseText));
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

function buyVehicle(id, userId) {
    

    var cost = $('#userPrice').val();
    var type = $('#buyType').val();
    var firstName = $('#userName').val();
    var lastName = $('#userLastName').val();
    var street1 = $('#userStreet1').val();
    var street2 = $('#userStreet2').val();
    var city = $('#userCity').val();
    var state = $('#userState').val();
    var zip = $('#userZip').val();
    var phone = $('#userPhone').val();
    var email = $('#userEmail').val();


    $.ajax({
        type: 'POST',
        url: 'http://localhost:8080/createpurchase',
        data: JSON.stringify({

            purchaseCost: cost,
            purchaseType: type,
            firstName: firstName,
            lastName: lastName,
            street: street1,
            street2: street2,
            city: city,
            state: state,
            zip: zip,
            phone: phone,
            email: email,
            vehicle: {vehicleId: id},
            user: {userId: userId}

        }),
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        'dataType': 'json',
        success: function (data, status) {
            // clear errorMessages
            $('#errorMessages').empty();
            $('#thanks').show();

            $('#userPrice').val('');
            $('#buyType').val('');
            $('#userName').val('');
            $('#userLastName').val('');
            $('#userStreet1').val('');
            $('#userStreet2').val('');
            $('#userCity').val('');
            $('#userState').val('');
            $('#userZip').val('');
            $('#userPhone').val('');
            $('#userEmail').val('');
            
            alert("purchase Saved!");
        },
        error: function (response) {
            $('#errorMessages')
                    .append($('<li>')
                            .attr({class: 'list-group-item list-group-item-danger'})
                            .text("Add fields required"));
        }
    });


}

function showVehicleDetails(id) {
    $('#newVehicle').hide();
    $('#thanks').hide();
    $('#detailsVehicle').show();
    $('#detailResults').empty();
    $('#submitInput').empty();
    $('#cashInput').empty();

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
            var mileage = data.milage;
            var vin = data.vin;
            var msrp = data.msrp;
            var id = data.vehicleId;
            var description = data.description;
            var priceLow = msrp * .95;

            var makeId = data.make.makeId;
            var modelId = data.model.modelId;
            var userId = data.user.userId;

            var row = '<div class="content-box">';
            row += '<div class="row">';
            row += '<h4>' + year + ' ' + make + ' ' + model + '</h4>';
            row += '<div class="col-lg-3 col-md-3">';
            row += '<img src="../images/' + fileImg + '" alt="" style="width:70%;">';
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

            row += '</tr>';
            row += '</table>';
            row += '</div>';
            row += '</div>';

            row += '</div>';

            var cashRow = '<label for="userPrice">Price</label> <input type="number" min="' + priceLow + '" max="' + msrp + '" class="form-control inputLength" id="userPrice" required>';


            $('#detailResults').append(row);
            $('#cashInput').append(cashRow);

            var submitRow = '<button type="button" onclick="buyVehicle(' + id + ',';
            submitRow += userId + ')" class="btn btn-primary">Buy</button>';
            $('#submitInput').append(submitRow);
        },
        error: function () {
            $('#errorMessages')
                    .append($('<li>')
                            .attr({class: 'list-group-item list-group-item-danger'})
                            .text('Error calling web service.  Please try again later.'));
        }
    });

}