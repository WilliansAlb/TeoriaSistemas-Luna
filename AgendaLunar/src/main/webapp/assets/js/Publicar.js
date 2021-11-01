/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


 function publicar() {
     //id usuario
     // contenido
                    var ta=getTags();
                    
                    var com = document.getElementById('textop')
                    var data = {comentario: com.value, etiqueta:ta};

                    $.ajax({
                        type: "POST",
                        url: "../PublicarPost",
                        data: data,
                        beforeSend: function () {
                        },
                        complete: function (data) {

                        },
                        success: function (data) {
                            com.value='';
                            location.reload(true);
                        },
                        error: function (data) {
                            alert("Error al realizar una publicacion intentalo mas tarde");
                            
                        }
                    });
                }