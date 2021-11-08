window.onload = function (){
	var fecha = new Date();
	document.getElementById("fecha").value = fecha.toLocaleDateString('en-CA');
};


function crear_evento() {
    var nombre = document.getElementById("nombre").value;
    var fecha = document.getElementById("fecha").value;
	var cult = document.getElementById("cultivos").value;
	var luga = document.getElementById("lugares").value;
    if (nombre== "") {
        alert("Colocale un nombre a la siembra");
		return;
    } else if (fecha==""){
        alert("Colocale una fecha a la siembra");
		return;
	} else if (cult==-1){
        alert("Crea un cultivo antes");
		return;
	} else if (luga==-1){
        alert("Crea un lugar antes");
		return;
	} else {
		crear_evento_siembra(nombre, nombre, fecha, cult, luga);
    }
}

function crear_evento_siembra(nombre, descripcion, fecha, cultivo, lugar) {
	var cult = document.getElementById("cultivos");
	var textCult = cult.options[cult.selectedIndex].text.split("-")[0];
	var luga = document.getElementById("lugares");
	var textLuga = luga.options[luga.selectedIndex].text;
    var data = {nombre: nombre, descripcion: descripcion, esSiembra: "true", fecha: fecha, cultivo: cultivo, lugar: lugar};
    $.ajax({
        type: "POST",
        url: "../Calendario",
        data: data,
        beforeSend: function () {
            $("#cargando").show();
        },
        complete: function (data) {
            setTimeout(() => {
                document.getElementById("cargando").style.display = "none";
                $("#oculto3").show();
            }, 1000);
        },
        success: function (data) {
            if (data.includes("ERROR")) {
                document.getElementById("img_tipo").src = "../assets/img/o6.png";
                $("#tipo_mensaje").text("ERROR");
                $("#mensaje").text("Ha ocurrido un error inesperado");
            } else {
                document.getElementById("img_tipo").src = "../assets/img/o7.png";
                $("#tipo_mensaje").text("SIEMBRA CREADA EXITOSAMENTE");
				var tr = document.createElement("tr");
				tr.id = "t"+data;
				var td = document.createElement("td");
				td.textContent = textLuga;
				var td1 = document.createElement("td");
				td1.textContent = textCult;
				var td2 = document.createElement("td");
				td2.textContent = nombre;
				var td3 = document.createElement("td");
				td3.textContent = fecha;
				var td4 = document.createElement("td");
				var check = document.createElement("input");
				check.type = "checkbox";
				check.style.width = "20px";
				check.style.height = "20px";
				td4.appendChild(check);
				tr.append(td,td1,td2,td3,td4);
				document.getElementById("tbody_siembras").appendChild(tr);
                $("#mensaje").text("Se ha creado exitosamente la siembra");
            }
        },
        error: function (data) {
            alert("Problemas al tratar de enviar el formulario");
        }
    });
}

function crear_evento_id_siembra(id_siembra, checkbox) {
	var nombre = "Cosecha ";
	var nombre_siembra = checkbox.parentNode.parentNode.querySelectorAll('td')[2].textContent;
	nombre += nombre_siembra;
	var descripcion = nombre;
	var tipo  = "cosecha";
	var fecha = new Date();
	var fechan = fecha.toLocaleDateString('en-CA');
    var data = {nombre: nombre, descripcion: descripcion, esSiembra: "true", fecha: fechan, id_siembra:id_siembra, tipo:tipo};
    $.ajax({
        type: "POST",
        url: "../Calendario",
        data: data,
        beforeSend: function () {
            $("#cargando").show();
        },
        complete: function (data) {
            setTimeout(() => {
                document.getElementById("cargando").style.display = "none";
                document.querySelector(".contenedor-calendario").style.filter = "blur(0px)";
                $("#oculto3").show();
            }, 1000);
        },
        success: function (data) {
            if (data.includes("ERROR")) {
                document.getElementById("img_tipo").src = "../assets/img/o6.png";
                $("#tipo_mensaje").text("ERROR");
                $("#mensaje").text("Ha ocurrido un error inesperado");
            } else {
                document.getElementById("img_tipo").src = "../assets/img/o7.png";
                $("#tipo_mensaje").text("SIEMBRA ACTUALIZADA");
                $("#mensaje").text("Se ha actualizado exitosamente la siembra");
				var padre = checkbox.parentNode;
				padre.innerHTML = "";
				var img = document.createElement("img");
				img.src = "../assets/img/o7.png";
				img.style.width = "20px";
				img.style.height = "20px";
				padre.appendChild(img);
            }
        },
        error: function (data) {
            alert("Problemas al tratar de enviar el formulario");
        }
    });
}