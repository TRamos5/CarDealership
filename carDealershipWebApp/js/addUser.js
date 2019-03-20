$(document).ready(function() {

    $('#addUser').click(function(event){
        $.ajax({
            type: 'POST',
            url: 'http://localhost:8080/user/admin/adduser',
            data: JSON.stringify({
                firstName: $('#firstName').val(),
                lastName: $('#lastName').val(),
                email: $('#email').val(),
                password: $('#password').val(),
                role: $('#role').val()
            }),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            'dataType': 'json',
            success: function (data, status) {
                $('#errorMessages').empty();
    
                $('#firstName').val('');
                $('#lastName').val('');
                $('#email').val('');
                $('#password').val('');
                $('#role').val('');
            },
            error: function() {
                $('#errorMessages')
                        .append($('<li>')
                                .attr({class: 'list-group-item list-group-item-danger'})
                                .text('Error calling web service.  Please try again later.'));
            }
    
        })
    })

});