$(document).ready(function () {
    getMakes();
});

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
    let tableBody = $("tbody");
    tableBody.empty();
    makes.forEach(make => {
        $("tbody").append(`<tr>
            <th>${make.makeName}</th>
            <th>${make.dateAdded}</th>
            <th>${make.user.firstName}</th>
          </tr>`);
    });
}

function addMake() {
    let newMake = $("#new-make").val();
    let url = `http://localhost:8080/make/admin/createmake`;
    console.log(url);
    $.ajax({
        type: 'POST',
        url: url,
        data: JSON.stringify({
            makeName: newMake,
            user: { userId: 1 }
        }),
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        'dataType': 'json',
        success: function (make) {
            console.log(make);
            loadMakes();
        },
        error: function (err) {
            console.log(err);
        }
    });
}
