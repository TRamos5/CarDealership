$(document).ready(function () {
    getModels();
    getMakes();
});

function getModels() {
    $.ajax({
        type: "GET",
        url: `http://localhost:8080/model/admin/allmodels`,
        success: function (model) {
            loadModels(model);
        },
        error: function (err) {
            alert("error with model");
        }
    });
}

function getMakes() {
    $.ajax({
        type: "GET",
        url: `http://localhost:8080/make/admin/allmakes`,
        success: function (makes) {
            loadMakes(makes);
        },
        error: function (err) {
            alert("error with makes");
        }
    });
}

function loadMakes(makes) {
    let makesDropdown = $("#makes");
    makesDropdown.empty();
    makes.forEach(make => {
        makesDropdown.append(`<option value="${make.makeId}">
            ${make.makeName}
          </option>`);
    });


}


function loadModels(models) {
    models.forEach(model => {
        $("tbody").append(`<tr>
          <th>${model.modelName}</th>
          <th>${model.dateAdded}</th>
          <th>${model.user.firstName}</th>
        </tr>`);
    });
}

function addModel() {
    let newModel = $("#new-model").val();
    let makeId = $("#makes option:selected").val();
    let url = `http://localhost:8080/model/admin/createmodel`;
    console.log(url);
    $.ajax({
        type: "POST",
        url: url,
        data: JSON.stringify({
            modelName: newModel,
            user: { userId: 1 },
            make: { makeId: makeId }
        }),
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        'dataType': 'json',
        success: function (model) {
            console.log(model);
        },
        error: function (err) {
            console.log(err);
        }
    });
    loadmodels();
}
