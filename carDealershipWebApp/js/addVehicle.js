$(document).ready(function () {


});

function createVehicle() {

    $.ajax({
        type: 'POST',
        url: 'http://localhost:8080/vehicle/createvehicle',
        data: JSON.stringify({
            year: $('#yearChoice').val(),
            mileage: $('#mileageChoice').val(),
            isNew: $('#typeChoice').val(),
            salePrice: $('#saleChoice').val(),
            style: $('#styleChoice').val(),
            interior: $('#interiorChoice').val(),
            trans: $('#transmissionChoice').val(),
            msrp: $('#msrpChoice').val(),
            color: $('#colorChoice').val(),
            vin: $('#vinChoice').val(),
            description: $('#description').val(),
            featured: true,
            sold: false,
            fileImg: $('#customFile').val().substring(12),
            make: {makeId: $('#makeChoice').val()},
            model: {modelId: $('#modelChoice').val()}, 
            user: {userId: 1}
        }),
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        'dataType': 'json',
        success: function (data, status) {
            $('#errorMessages').empty();

        },
        error: function () {
            $('#errorMessages')
                .append($('<li>')
                    .attr({ class: 'list-group-item list-group-item-danger' })
                    .text('Error calling web service.  Please try again later!'));
        }
    })
}
