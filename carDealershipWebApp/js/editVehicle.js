$(document).ready(function() {
    var idv = getUrlVars()["id"];
            
        showVehicleDetails(idv);
        
        
    });
    
    
    
    function deleteVehicle(id){
        var id = getUrlVars()["userId"];
        location.assign('vehicle.html?id=' + id + '');
        
        
    }
    
    
    
    
    function showVehicleDetails(id) {
        
        
        $.ajax ({
            type:'GET',
            url:'http://localhost:8080/vehicle/getvehicle/'+ id,
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
                var id = data.vehicleId;
                var isNew = data.isNew;
                var description = data.description;
                var featured = data.featured;
                var modelId = data.model.modelId;
                var makeId = data.make.makeId;
                var userId = data.user.userId;
                var vehicleId = id;
                
                $('#makeChoice').append(make);
                $('#modelChoice').append(model);
                $('#year').val(year);
                $('#type').val(isNew);
                $('#style').val(style);
                $('#transmission').val(trans);
                $('#exterior').val(color);
                $('#interior').val(interior);
                $('#mileage').val(mileage);
                $('#vin').val(vin);
                $('#description').val(description);
                $('#msrp').val(msrp);
                $('#salesPrice').val(salePrice);
                $('#isFeatured').attr('checked', featured); 
                
                $('#filename').val(fileImg);
    
                var row = '<div class="content-box">';
                    row+= '<div class="row">';
                    row+= '<h4>'+year+' '+make+' '+model+'</h4>';
                    row+= '</div>';
                    row+= '</div>';
                var buttonRow = '<button type="button" id="Delete-button" onclick="deleteVehicle(' + id + ')" class="btn btn-default"> Delete </button>';
                var buttonRow2 = '<button type="button" class="btn btn-primary" id="Save-button" onclick="saveVehicle(' + id + ',' + vehicleId + ',' + userId + ',' + makeId + ','+ modelId + ')" > Save </button>';
                            
                                
                $('#deleteAndSave').append(buttonRow);
                $('#deleteAndSave').append(buttonRow2);
                    
                $('#detailResults').append(row);
                var imageRow = '<div class="col-lg-3 col-md-3">';
                    imageRow+= '<img src="../'+fileImg+'" alt="" style="width:70%;">';
                    imageRow+= '</div>';
                $('#carImage').append(imageRow);
            },
            error: function() {
             $('#errorMessages')
                .append($('<li>')
                .attr({class: 'list-group-item list-group-item-danger'})
                .text('Error calling web service.  Please try again later.'));
           }
        });
        
    }
    
    
    
    $('#upload').change(function() {
        var filename = $(this).val();
        var lastIndex = filename.lastIndexOf("\\");
        if (lastIndex >= 0) {
            filename = filename.substring(lastIndex + 1);
        }
        $('#filename').val(filename);
    });
    
    function saveVehicle(idv, userId, makeId, modelId) {
        var newYear = $('#year').val();
        var newPrice = $('#salesPrice').val();
        var newImg = $('#filename').val();
        var newStyle = $('#style').val();
        var newTrans = $('#transmission').val();
        var newExterior = $('#exterior').val();
        var newInterior = $('#interior').val();
        var newMileage = $('#mileage').val();
        var newVin = $('#vin').val();
        var newMsrp = $('#msrp').val();
        var newerVehicle = $('#type').val();
        var newDescription = $('#description').val();
        var newFeatured = $('#isFeatured').val();
        var newSold = "false";
        var userId = userId;
        var vehicleId = idv;
        
        $.ajax ({
            type: 'PUT',
            url: 'http://localhost:8080/vehicle/updatevehicle',
            data: JSON.stringify({
                year: newYear,
                mileage: newMileage,
                isNew: newerVehicle,
                salePrice: newPrice,
                fileImg: newImg,
                style: newStyle,
                trans: newTrans,
                color: newExterior,
                interior: newInterior,
                vin: newVin,
                msrp: newMsrp,
                user:{userId: 1}, 
                description: newDescription,
                featured: newFeatured,
                sold: newSold,
                make: { makeId: makeId},
                model: { modelId: modelId},
                vehicleId: vehicleId,
            }),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
                    dataType: "json",
            success: function(data, status) {
                // clear errorMessages
                $('#errorMessages').empty();
    
               
            },
            error: function() {
                $('#errorMessages')
                   .append($('<li>')
                   .attr({class: 'list-group-item list-group-item-danger'})
                   .text('Error fields incorrect.  Please try again .'));
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
    