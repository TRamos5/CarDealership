$(document).ready(function() {
    function getUrlVars() {
        var vars = {};
        var parts = window.location.href.replace(/[?&]+([^=&]+)=([^&]*)/gi, function(m,key,value) {
            vars[key] = value;
        });
        return vars;
    }

    var vin = getUrlVars()["vin"];
    $('#add-message').append(vin);
});

function sendContactUsForm(){
    $.ajax({
        type: 'POST',
        url: 'http://localhost:8080/createcontact',
        data: JSON.stringify({
            name: $('#add-name').val(),
            phone: $('#add-phone').val(),
            email: $('#add-email').val(),
            message: $('#add-message').val()
        }),
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        'dataType': 'json',
        success: function (data, status) {
            // clear errorMessages
            $('#errorMessages').empty();
            // Clear the form
            $('#add-name').val('');
            $('#add-phone').val('');
            $('#add-email').val('');
            $('#add-message').val('');
        },
        error: function () {
            $('#errorMessages')
                    .append($('<li>')
                            .attr({class: 'list-group-item list-group-item-danger'})
                            .text('Error calling web service.  Please try again later.'));
        }
    });
}