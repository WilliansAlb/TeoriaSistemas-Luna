function agregarLugar() {
    var nombre = document.getElementById("nombre").value;
    var ubicacion = document.getElementById("ubicacion").value;
    var clima = document.getElementById("clima").value;
    if (nombre != "" && ubicacion != "") {
        var data = {nombre: nombre, ubicacion: ubicacion, clima: clima};
        $.ajax({
            type: "POST",
            url: "../Lugares",
            data: data,
            beforeSend: function () {
                $("#cargando").show();
            },
            complete: function (data) {
                setTimeout(() => {
                    document.getElementById("cargando").style.display = "none";
                    $("#oculto3").show();
                }, 500);
            },
            success: function (data) {
                if (data.includes("ERROR")) {
                    document.getElementById("img_tipo").src = "../assets/img/o6.png";
                    $("#tipo_mensaje").text("ERROR");
                    $("#mensaje").text("Ha ocurrido un error inesperado");
                } else {
                    document.getElementById("img_tipo").src = "../assets/img/o7.png";
                    $("#tipo_mensaje").text("LUGAR AGREGADO");
                    $("#mensaje").text("Se ha agregado exitosamente el lugar");
                    var tr = document.createElement("tr");
                    tr.id = data;
                    var td = document.createElement("td");
                    td.textContent = nombre;
                    var td1 = document.createElement("td");
                    td1.textContent = ubicacion;
                    var td2 = document.createElement("td");
                    td2.textContent = clima;
                    tr.append(td, td1, td2);
                    document.getElementById("tbody_siembras").appendChild(tr);
                    document.getElementById("nombre").value = "";
                    document.getElementById("ubicacion").value = "";
                }
            },
            error: function (data) {
                alert("Problemas al tratar de enviar el formulario");
            }
        });
    } else {
        document.getElementById("img_tipo").src = "../assets/img/o6.png";
        $("#tipo_mensaje").text("FALTA");
        $("#mensaje").text("Rellena todos los campos");
        document.getElementById("oculto3").style.display = "flex";
    }
}