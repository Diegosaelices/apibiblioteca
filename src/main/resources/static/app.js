const API_URL = "http://localhost:8080/libros";

document.addEventListener("DOMContentLoaded", listarLibros);

function listarLibros() {
  fetch(API_URL)
    .then(res => res.json())
    .then(libros => {
      mostrarLibros(libros);
      document.getElementById("volverBtn").classList.add("d-none");
    })
    .catch(err => {
      console.error("Error:", err);
      alert("Error al cargar libros");
    });
}

function mostrarLibros(libros) {
  const tbody = document.getElementById("tablaLibros");
  tbody.innerHTML = "";

  libros.forEach(libro => {
    const ejemplares = libro.numeroEjemplares !== undefined
      ? libro.numeroEjemplares
      : (libro.ejemplares ? libro.ejemplares.length : 0);

    const autores = libro.autores ? libro.autores.map(a => a.nombre).join(", ") : "";

    const fila = `
      <tr>
        <td>${libro.id}</td>
        <td>${libro.titulo}</td>
        <td>${libro.isbn}</td>
        <td>${libro.fechaPublicacion}</td>
        <td>${autores}</td>
        <td>${ejemplares}</td>
        <td>
          <button class="btn btn-sm btn-danger" onclick="borrarLibro(${libro.id})">Eliminar</button>
        </td>
      </tr>`;
    tbody.innerHTML += fila;
  });
}

function buscarPorTitulo() {
  const texto = document.getElementById("busquedaTitulo").value.trim();
  if (!texto) return listarLibros();

  fetch(`${API_URL}?titulo=${encodeURIComponent(texto)}`)
    .then(res => {
      if (!res.ok) throw new Error("Error al buscar por título");
      return res.json();
    })
    .then(libros => {
      mostrarLibros(libros);
      document.getElementById("volverBtn").classList.remove("d-none");
    })
    .catch(err => {
      console.error("Error:", err);
      alert("Error al buscar por título");
    });
}

function buscarPorAutor() {
  const nombre = document.getElementById("busquedaAutor").value.trim();
  if (!nombre) return listarLibros();

  fetch(`${API_URL}/porAutor?nombre=${encodeURIComponent(nombre)}`)
    .then(res => {
      if (!res.ok) throw new Error("Error al buscar por autor");
      return res.json();
    })
    .then(libros => {
      mostrarLibros(libros);
      document.getElementById("volverBtn").classList.remove("d-none");
    })
    .catch(err => {
      console.error("Error:", err);
      alert("Error al buscar por autor");
    });
}

function volverListado() {
  document.getElementById("busquedaTitulo").value = "";
  document.getElementById("busquedaAutor").value = "";
  listarLibros();
}

function crearLibro() {
  const titulo = document.getElementById("tituloNuevo").value.trim();
  const isbn = document.getElementById("isbnNuevo").value.trim();
  const fecha = document.getElementById("fechaNuevo").value;

  if (!titulo || !isbn || !fecha) {
    alert("Todos los campos son obligatorios");
    return;
  }

  fetch(API_URL, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ titulo, isbn, fechaPublicacion: fecha })
  })
    .then(res => {
      if (!res.ok) return res.json().then(err => { throw err; });
      return res.json();
    })
    .then(() => {
      document.getElementById("tituloNuevo").value = "";
      document.getElementById("isbnNuevo").value = "";
      document.getElementById("fechaNuevo").value = "";
      document.getElementById("modalNuevoLibro").classList.remove("show");
      document.querySelector(".modal-backdrop")?.remove();
      document.body.classList.remove("modal-open");
      alert("Libro añadido correctamente");
      listarLibros();
    })
    .catch(err => {
      console.error("Error:", err);
      alert("Error al crear libro");
    });
}

function borrarLibro(id) {
  if (!confirm("¿Estás seguro de eliminar este libro?")) return;

  fetch(`${API_URL}/${id}`, { method: "DELETE" })
    .then(() => listarLibros())
    .catch(err => {
      console.error("Error:", err);
      alert("Error al eliminar libro");
    });
}


