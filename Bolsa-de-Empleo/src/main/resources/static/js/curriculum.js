// Js para la experiencia


function guardarExperiencia() {
        let cargo = document.getElementById("cargo").value;
        let empresa = document.getElementById("empresa").value;
        let responsabilidades = document.getElementById("responsabilidades").value;
        let fechaInicio = document.getElementById("fechaInicio").value;
        let fechaFinalizacion = document.getElementById("fechaFinalizacion").value;
        let id_candidato = document.getElementById("id_candidato").value;
        console.log(JSON.stringify(id_candidato, null, 2));
        // Validar que los campos no estén vacíos
        if (!cargo || !empresa || !responsabilidades || !fechaInicio || !fechaFinalizacion) {
            alert("Por favor, completa todos los campos.");
            return;
        }

        // Crear una nueva fila en la tabla
        let tabla = document.getElementById("tablaExperiencia");
        let nuevaFila = tabla.insertRow();

        nuevaFila.innerHTML = `
            <td>${cargo}</td>
            <td>${empresa}</td>
            <td>${responsabilidades}</td>
            <td>${fechaInicio}</td>
            <td>${fechaFinalizacion}</td>
            <td class="hidden-td">${id_candidato}</td>
            <td><button class="btn btn-danger btn-sm" onclick="eliminarFila(this)">Eliminar</button></td>
        `;
        nuevaFila.querySelector(".hidden-td").style.display = "none";
        // Mostrar la tabla si tiene datos
        document.getElementById("tableContainer").style.display = "block";

        // Ocultar el formulario
        document.getElementById("formularioExperiencia").style.display = "none";

        // Mostrar la pregunta si desea agregar otra experiencia
        document.getElementById("preguntaOtraExperiencia").style.display = "block";

        // Limpiar el formulario
        document.getElementById("experienceForm").reset();
}

function mostrarFormulario() {
    // Mostrar el formulario nuevamente

    document.getElementById("formularioExperiencia").style.display = "block";

    // Ocultar la pregunta de agregar otra experiencia
    document.getElementById("preguntaOtraExperiencia").style.display = "none";
}

function eliminarFila(boton) {
    let fila = boton.parentNode.parentNode;
    fila.parentNode.removeChild(fila);

    let tabla = document.getElementById("tablaExperiencia");
    if (tabla.rows.length === 0) {
        document.getElementById("tableContainer").style.display = "none";
    }
}





// Js Para la informacion Academica

function guardarInformacionAcademica() {


        let titulo = document.getElementById("Titulo").value;
        let especialidad = document.getElementById("Especialidad").value;
        let institucion = document.getElementById("Institucion").value;
        let fechaFinalizacionI = document.getElementById("FechaFinalizacionI").value;
        let id_candidato2 = document.getElementById("id_candidato2").value;
         console.log(JSON.stringify(id_candidato2, null, 2));
        if (!titulo || !especialidad || !institucion || !fechaFinalizacionI) {
            alert("Por favor, completa todos los campos.");
            return;
        }

        // Crear una nueva fila en la tabla
        let tabla2 = document.getElementById("tablaAcademica");
        let nuevaFila2 = tabla2.insertRow();
        console.log(tabla2);
        nuevaFila2.innerHTML = `
            <td>${titulo}</td>
            <td>${especialidad}</td>
            <td>${institucion}</td>
            <td>${fechaFinalizacionI}</td>
            <td class="hidden-td">${id_candidato2}</td>
            <td><button class="btn btn-danger btn-sm" onclick="eliminarFilaAcademica(this)">Eliminar</button></td>
        `;
        nuevaFila2.querySelector(".hidden-td").style.display = "none";

        // Mostrar la tabla si tiene datos
        document.getElementById("tableContainer2").style.display = "block";

        // Ocultar el formulario
        document.getElementById("formularioInformacionAcademica").style.display = "none";

        // Mostrar la pregunta si desea agregar otra experiencia
        document.getElementById("preguntaOtraAcademica").style.display = "block";

        // Limpiar el formulario
        document.getElementById("AcademicForm").reset();
}

function mostrarFormulario2() {
     // Mostrar el formulario nuevamente
     document.getElementById("formularioInformacionAcademica").style.display = "block";

     // Ocultar la pregunta de agregar otra experiencia
     document.getElementById("preguntaOtraAcademica").style.display = "none";
}

function eliminarFilaAcademica(boton) {
    let fila = boton.parentNode.parentNode;
    fila.parentNode.removeChild(fila);

    let tabla = document.getElementById("tablaAcademica");
    if (tabla.rows.length === 0) {
        document.getElementById("tableContainer2").style.display = "none";
    }
}



    // funcion para cancelar el proceso y eliminar
function cancelarProceso() {
        alert("Se han eliminado los datos temporales.");
        document.getElementById("tablaExperiencia").innerHTML = "";
        document.getElementById("tableContainer").style.display = "none";
}

async function enviarDatos() {
    alert("Ingresa a la funcion");
    try {
        let [expResponse, infoResponse] = await Promise.all([
            EnviarExpApi(),
            EnviarInfoApi()
        ]);

        if (expResponse && infoResponse) {
            let modal = new bootstrap.Modal(document.getElementById("successModal"));
            modal.show();
        }
    } catch (error) {
        console.error("Error al enviar los datos:", error);
        alert("Ocurrió un error al enviar los datos.");
    }
}



async function EnviarExpApi() {
    let tabla = document.getElementById("tablaExperiencia");
    let experiencias = [];

    for (let i = 0; i < tabla.rows.length; i++) {
        let celdas = tabla.rows[i].cells;
        let experiencia = {
            cargo: celdas[0].innerText,
            empresa: celdas[1].innerText,
            responsabilidades: celdas[2].innerText,
            fechaInicio: celdas[3].innerText,
            fechaFinalizacion: celdas[4].innerText,
            candidato: { id: celdas[5].innerText }
        };
        experiencias.push(experiencia);
    }

    let response = await fetch("http://localhost:8862/api/experiencia/guardar", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(experiencias)
    });

    return response.ok;
}

async function EnviarInfoApi() {
    let tabla = document.getElementById("tablaAcademica");
    let informacionAcademica = [];

    for (let i = 0; i < tabla.rows.length; i++) {
        let celdas = tabla.rows[i].cells;
        let info = {
            titulo: celdas[0].innerText,
            especialidad: celdas[1].innerText,
            institucion: celdas[2].innerText,
            fechaFinalizacion: celdas[3].innerText,
            candidato: { id: celdas[4].innerText }
        };
        informacionAcademica.push(info);
    }

    let response = await fetch("http://localhost:8862/api/academica/guardar", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(informacionAcademica)
    });

    return response.ok;
}
