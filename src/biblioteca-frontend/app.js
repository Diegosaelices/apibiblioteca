const API_URL = "http://localhost:8080/libros";

// Cargar libros al abrir la página
document.addEventListener("DOMContentLoaded", listarLibros);

// Función principal para cargar libros completos (incluye autores y ejemplares)
function listarLibros() {
  fetch(API_URL + "?page=0&size=100")
    .then(res => {
      if (!res.ok) throw new Error("Error al obtener libros");
      return res.json();
    })
    .then(libros => mostrarLibros(libros))
    .catch(err => {
      console.error("Error:", err);
      alert("Error al cargar libros");
    });
}

// Renderiza los libros en la tabla
function mostrarLibros(libros) {
  const tbody = document.getElementById("tablaLibros");
  tbody.innerHTML = "";

  libros.forEach(libro => {
    const autores = libro.autores.map(a => a.nombre).join(", ");
    const ejemplares = libro.ejemplares.length;

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
      </tr>
    `;
    tbody.innerHTML += fila;
  });
}

// Buscar libros por título
function buscarPorTitulo() {
  const texto = document.getElementById("busquedaTitulo").value.trim();
  if (!texto) return listarLibros();

  fetch(`${API_URL}?titulo=${encodeURIComponent(texto)}`)
    .then(res => {
      if (!res.ok) throw new Error("Error al buscar");
      return res.json();
    })
    .then(libros => mostrarLibros(libros))
    .catch(err => {
      console.error("Error:", err);
      alert("Error en la búsqueda");
    });
}

// Crear un nuevo libro
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
      listarLibros();
    })
    .catch(err => {
      console.error("Error:", err);
      alert("Error al crear libro: " + (err.error || JSON.stringify(err)));
    });
}

// Eliminar un libro por ID
function borrarLibro(id) {
  if (!confirm("¿Estás seguro de que deseas eliminar este libro?")) return;

  fetch(`${API_URL}/${id}`, { method: "DELETE" })
    .then(() => listarLibros())
    .catch(err => {
      console.error("Error:", err);
      alert("Error al eliminar el libro");
    });
}
