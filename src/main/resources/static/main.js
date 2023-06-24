function updateTable(data) {
    var table = document.getElementById("trafficTable");

    // clear the table
    while (table.rows.length > 1) {
        table.deleteRow(1);
    }

    // populate the table with new data
    for (var i = 0; i < data.length; i++) {
        var row = table.insertRow(-1);
        var timestampCell = row.insertCell(0);
        var hostCell = row.insertCell(1);
        var portCell = row.insertCell(2);
        var bytesInCell = row.insertCell(3);
        var bytesOutCell = row.insertCell(4);
        timestampCell.innerHTML = data[i].timestamp;
        hostCell.innerHTML = data[i].host;
        portCell.innerHTML = data[i].port;
        bytesInCell.innerHTML = data[i].bytesIn;
        bytesOutCell.innerHTML = data[i].bytesOut;
    }
}

function updateTrafficData() {
    $.get("/api/traffic/realtime?host=localhost&port=80", function(data) {
        updateTable(data);
    });
}

// update the traffic data every 5 seconds
setInterval(updateTrafficData, 5000);
