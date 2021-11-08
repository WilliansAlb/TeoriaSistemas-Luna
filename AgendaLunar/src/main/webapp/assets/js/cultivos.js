function agregarCultivo() {
    var cult = document.getElementById("cultivo").value;
    if (cult == "") {
        document.getElementById("img_tipo").src = "../assets/img/o6.png";
        $("#tipo_mensaje").text("FALTA");
        $("#mensaje").text("Te falta poner el tipo de cultivo");
        $("#oculto3").show();
    } else {
        var data = {tipo: cult};
        $.ajax({
            type: "POST",
            url: "../Cultivos",
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
                    $("#tipo_mensaje").text("CULTIVO AGREGADO");
                    $("#mensaje").text("Se ha agregado exitosamente el cultivo");
                    var tbody = document.getElementById("tbody_siembras");
                    var tr = document.createElement("tr");
                    tr.id = data;
                    var td = document.createElement("td");
                    td.textContent = cult;
                    tr.appendChild(td);
                    tbody.appendChild(tr);
                    document.getElementById("cultivo").value = "";
                }
            },
            error: function (data) {
                alert("Problemas al tratar de enviar el formulario");
            }
        });
    }
}