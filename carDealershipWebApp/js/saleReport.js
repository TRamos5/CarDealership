$(document).ready(function() {

    loadUser();
  
    });
  
    function clearUserRows(){
      $('#userRows').empty();
    }
  
    function loadUser(){
      clearUserRows();
      var showTable = $('#userTableDiv');
      showTable.show();
  
      $.ajax({
        type : 'GET',
        url : 'http://localhost:8080/user/admin/allusers',
        success : function(data, status){
  
          $.each(data, function(index, user){
              var userID = user.userId;
              var firstName = user.firstName;
              var lastName = user.lastName;
              var email = user.email;
              var role = user.role;
  
  
              var row = '<tr>';
              row += '<td>' + firstName + lastName + '</td>';
              row += '<td>' + "$1,205,182" + '</td>';
              row += '<td>' + "125" + '</td>';
              row += '/tr';
  
                        $('#userRows').append(row);
  
          });
  
  
        },
        error : function(data){
          $('#errorMessages').append($('<li>')
          .attr({class: 'list-group-item list-group-item-danger'})
          .text('Error calling Web Service. Please try again later'));
  
        }
      });
    }