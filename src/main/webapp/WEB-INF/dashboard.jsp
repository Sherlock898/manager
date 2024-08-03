<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>WebSocket Home</title>
    <style>
        /* Modern styling */
        * {
            box-sizing: border-box;
            margin: 0;
            padding: 0;
            font-family: Arial, sans-serif;
        }
        
        body {
            background-color: #f4f4f9;
            color: #333;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }
        
        .container {
            background: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0,0,0,0.1);
            max-width: 600px;
            width: 100%;
            text-align: center;
        }
        
        .logo {
            width: 100px;
            margin-bottom: 20px;
        }
        
        h1 {
            font-size: 24px;
            margin-bottom: 20px;
        }
        
        .points {
            display: flex;
            flex-direction: column;
            gap: 20px;
        }
        
        .charge-point {
            background: #e9ecef;
            padding: 15px;
            border-radius: 8px;
        }
        
        .points-name, .point-name, .connector-name {
            font-weight: bold;
            font-size: 18px;
            margin-bottom: 10px;
        }
        
        .points-status, .connector-status {
            font-size: 14px;
            margin-bottom: 10px;
        }
        
        .points-buttons, .connector-buttons {
            display: flex;
            justify-content: center;
            gap: 10px;
            margin-bottom: 20px;
        }
        
        .btn {
            padding: 10px 15px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            transition: 0.3s;
        }
        
        .start-btn {
            background: #28a745;
            color: #fff;
        }
        
        .start-btn:hover {
            background: #218838;
        }
        
        .stop-btn {
            background: #dc3545;
            color: #fff;
        }
        
        .stop-btn:hover {
            background: #c82333;
        }

        .sidenav {
            height: 100%;
            width: 0;
            position: fixed;
            z-index: 1;
            top: 0;
            left: 0;
            background-color: #111;
            overflow-x: hidden;
            transition: 0.5s;
            padding-top: 60px;
          }
          
          .sidenav a {
            padding: 8px 8px 8px 32px;
            text-decoration: none;
            font-size: 25px;
            color: #818181;
            display: block;
            transition: 0.3s;
          }
          
          .sidenav a:hover {
            color: #f1f1f1;
          }
          
          .sidenav .closebtn {
            position: absolute;
            top: 0;
            right: 25px;
            font-size: 36px;
            margin-left: 50px;
          }
          
            .open {
                font-size:30px;
                cursor:pointer;
                position: absolute;
                top: 10px;
                left: 10px;
            }

        svg{
            height: 4em;
            width: 4em;
            margin: 20px;
        }
    </style>
</head>
<body>
    <div id="mySidenav" class="sidenav">
        <a href="javascript:void(0)" class="closebtn" onclick="closeNav()">&times;</a>
        <a href="#">About</a>
        <a href="#">Services</a>
        <a href="#">Clients</a>
        <a href="#">Contact</a>
    </div>

    <span class="open" style="font-size:30px;cursor:pointer" onclick="openNav()">&#9776;</span>

    <div class="container">
        <img src="https://goldenelectric.cl/wp-content/uploads/2023/03/Firma.png" alt="logo" class="logo">
        <h1>Management System</h1>
        <div class="points">
            <p>Waiting for connections</p>
        </div>
    </div>


    <script>
        // Charge point array
        let chargePoints = []

        // WebSocket connection
        const socket = new WebSocket('wss://manager-production-0131.up.railway.app/user/${user.id}');

        // Function to start loading
        function startLoading(pointsId) {
            // Send a message to the server to start loading for the specified pointsId
            socket.send(JSON.stringify({ action: 'startLoading', pointsId }));
        }

        // Handle incoming messages from the server
        socket.addEventListener('message', ({data}) =>{
            const cp = JSON.parse(data);
            console.log('Received message:', cp);
            // Update charge point data and status
            chargePoints = cp;
            // Update the UI
            const pointsContainer = document.querySelector('.points');
            if(length(chargePoints) == 0){
                pointsContainer.innerHTML = '<p class="waiting">Waiting for connections</p>';
                return;
            }
            pointsContainer.innerHTML = '';
            chargePoints.forEach(point => {
                const pointHtml = pointUi(point);
                pointsContainer.innerHTML += pointHtml;
            })
        });
        function pointUi(point){
            htmlUi = `
            <div class="charge-point">
                ` + '<div class="point-name">Charger: ' + point.chargePointModel + ' ID:' + point.id + ', ' + point.chargePointVendor + '</div><div class="point-name">Owned By: ' + point.chargePointUser + `</div> 
            `;
            htmlUi += '<svg xmlns="http://www.w3.org/2000/svg" height="1.2em" width="1.2em" viewBox="0 0 512 512"><path d="M271 48q49 0 49 49v286q0 49-49 49h-94q-49 0-49-49V97q0-49 49-49h94Z" fill="transparent" stroke="currentColor" stroke-width="15"></path><path d="m233 339-50-102q-7-13 8-13h33l-14-81q-2-15 5-2l50 102q7 13-8 13h-33l14 81q2 15-5 2ZM-16 127v66q0 15-15 15h-34q-15 0-29-5l-20-6q-14-5-23 7l-46 56q-9 12-17 0l-16-24q-8-12 3-23l90-90q11-11 26-11h66q15 0 15 15Z" fill="transparent" stroke="currentColor" stroke-width="15"></path><path stroke="currentColor" stroke-width="19" stroke-linecap="round" d="m-208 256-32 48"></path><path d="M329 172q77 109 100 229l4 20a64.498 64.498 0 0 1-128 16q-1-5 6-18 9-19 9-52V175q0-15 9-3Z" fill="transparent" stroke="currentColor" stroke-width="15"></path></svg>'

            let i = 1;
            while(point.status[i] != undefined){
                htmlUi += `
                <div class="connector">
                    <div class="connector-name">Connector: `+ i + `</div>
                    <div class="connector-status">Status: ` + point.status[i] + `</div>
                    <div class="connector-buttons">
                `;
                if(point.status[i] == "Preparing"){
                    htmlUi += `
                        <button class="btn start-btn" onclick="startCharge(` + point.id + "," + i  + `)">Start Charging</button>
                    </div>
                    `;
                }else if(point.status[i] == "Charging"){
                    htmlUi += `
                        <button class="btn stop-btn" onclick="stopCharge(` + point.id + "," + i  + `)">Stop Charging</button>
                    </div>
                    `;
                }
                htmlUi += "</div>";
                i++
            }
            htmlUi += "</div>";


            return htmlUi;
        }

        // Function to start charging
        function startCharge(chargePointId, connectorId){
            // Start transaction messsage: [StartTransaction, ChargePointId, ConnectorId]
            socket.send(JSON.stringify(['StartTransaction', chargePointId, connectorId]));
            console.log("Start charging", chargePointId, connectorId);
        }

        function stopCharge(chargePointId, connectorId){
            // Stop transaction messsage: [StopTransaction, ChargePointId, ConnectorId]
            socket.send(JSON.stringify(['StopTransaction', chargePointId, connectorId]));
            console.log("Stop charging", chargePointId, connectorId);
        }


        /* Set the width of the side navigation to 250px */
function openNav() {
    document.getElementById("mySidenav").style.width = "250px";
  }
  
  /* Set the width of the side navigation to 0 */
  function closeNav() {
    document.getElementById("mySidenav").style.width = "0";
  }
  

    </script>
</body>
</html>
