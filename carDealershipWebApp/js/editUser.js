$(document).ready(function() {
    var id = getUrlVars()["id"];
    loadPlaceholders(id);
    saveIt();
  });
  
  function loadPlaceholders(id){
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/user/admin/getuser/' + id,
  
        success: function (data, status) {
            $('#errorMessages').empty();
            $('#edit-first-name').val(data.firstName);
            $('#edit-last-name').val(data.lastName);
            $('#edit-email').val(data.email);
  
        },
        error: function() {
            $('#errorMessages')
                    .append($('<li>')
                            .attr({class: 'list-group-item list-group-item-danger'})
                            .text('Error calling web service.  Please try again later.'));
        }
  
    });
  
  }
  
  function getUrlVars() {
      var vars = {};
      var parts = window.location.href.replace(/[?&]+([^=&]+)=([^&]*)/gi, function (m, key, value) {
          vars[key] = value;
      });
      return vars;
  }
  
  function saveIt(){
    $('#save').click(function(event){
      var userId = getUrlVars()["id"];
      var firstNameNew = $('#edit-first-name').val();
      var lastNameNew = $('#edit-last-name').val();
      var emailNew = $('#edit-email').val();
      var roleNew = $('#edit-select-role').val();
      var passwordNew = $('#edit-password').val();
        $.ajax({
            type: 'PUT',
            url: 'http://localhost:8080/user/admin/updateuser',
            data: JSON.stringify({
                userId : userId,
                firstName: firstNameNew,
                lastName: lastNameNew,
                email: emailNew,
                role: roleNew,
                password: passwordNew
  
              }),
              headers: {
                  'Accept': 'application/json',
                  'Content-Type': 'application/json'
              },
              'dataType': 'json',
            success: function (data, status) {
                $('#errorMessages').empty();
  
                $('#edit-first-name').val('');
                $('#edit-last-name').val('');
                $('#edit-email').val('');
                $('#edit-password').val('');
                $('#edit-role').val('');
                $('#edit-c-password').val('');
  
                window.location.replace("users.html");
            },
            error: function(data) {
                $('#errorMessages')
                        .append($('<li>')
                                .attr({class: 'list-group-item list-group-item-danger'})
                                .text(data.responseText));
            }
  
        })
    });
  }