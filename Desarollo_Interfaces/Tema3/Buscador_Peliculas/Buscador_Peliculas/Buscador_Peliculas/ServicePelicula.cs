using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Buscador_Peliculas
{
    class ServicePelicula : IDisposable
    {
        // Campo para saber si ya se liberaron los recursos
        bool disposed;
        // Creamos un constructor vacio
        public ServicePelicula()
        {
            // Flag: Se ha llamado al método Dispose?
            disposed = false;
        }



        // METODOS CRUD

        public async Task<List<Pelicula>> Listar()
        {
            using (var _context = new ListaPeliculasDbContext())
            {
                return await _context.Peliculas
                    .AsNoTracking() // Propiedad de Linq para mejorar el rendimiento cuando no se modifican los datos
                    .OrderBy(u => u.Id) // Ordena los resultado por Id
                    .ToListAsync(); // Ejecuta la consulta y devuelve los resultados, es asincrono por lo que devuelve una Task<List<Pelicula>>
            }
        }

        public async Task<Pelicula?> Listar(int id)
        {
            using (var _context = new ListaPeliculasDbContext())
            {
                return await _context.Peliculas
                .AsNoTracking()
                .FirstOrDefaultAsync(p => p.Id == id); // Devuelve el primer objeto que cumpla la condicion de la expresión lambda
            }
        }

        public async Task<Pelicula?> Listar(string titulo)
        {
            using (var _context = new ListaPeliculasDbContext())
            {
                return await _context.Peliculas
                    .AsNoTracking()
                    .FirstOrDefaultAsync(p => p.Titulo == titulo);
            }
        }

        public async Task<Pelicula> Insertar(Pelicula peli)
        {
            if (peli == null) throw new ArgumentNullException(nameof(peli));

            using (var _context = new ListaPeliculasDbContext())
            {
                await _context.Peliculas.AddAsync(peli); // Agrega un nuevo objeto User al DbSet y lo inserta de forma asincrona
                await _context.SaveChangesAsync(); // Ejecuta todas las operaciones pendientes en la base de datos
                return peli;
            }
        }

        public async Task<bool> Actualizar(Pelicula peli)
        {
            if (peli == null) throw new ArgumentNullException(nameof(peli));

            using (var _context = new ListaPeliculasDbContext())
            {
                var existing = await _context.Peliculas.FirstOrDefaultAsync(p => p.Id == peli.Id);
                if (existing is null) return false;

                // Actualiza campos permitidos
                existing.Titulo = peli.Titulo;
                existing.Director = peli.Director;
                existing.Year = peli.Year;
                existing.Vista = peli.Vista;
          

                await _context.SaveChangesAsync(); // Llamamos directamente a SaveChangesAsync gracias a que EF mantiene el objeto EXISTING trakeado con lo que se llama Change Tracking
                return true;
            }
        }

        public async Task<bool> Borrar(int id)
        {
            using (var _context = new ListaPeliculasDbContext())
            {
                var entity = await _context.Peliculas.FirstOrDefaultAsync(p => p.Id == id);
                if (entity is null) return false;

                _context.Peliculas.Remove(entity); // Marca el objeto entity para ser eliminado en el siguiente SaveChangesAsync()
                await _context.SaveChangesAsync();
                return true;
            }
        }


        // MÉTODOS DE LIBERACIÓN DE RECURSOS ----------

        // Método público de IDisposable
        public void Dispose()
        {
            Dispose(true); // Método que libera los recursos cuando terminas de usar la clase
            GC.SuppressFinalize(this); // Evita llamar al finalizador dos veces
        }

        // Método protegido: libera los recursos
        protected virtual void Dispose(bool disposing)
        {
            if (disposed)
                return;

            if (disposing)
            {
                //Liberar recursos no manejados como ficheros, conexiones a bd, etc.
            }

            disposed = true;
        }

        // Finalizador (por si se olvida llamar a Dispose), tambien conocido como Destructor
        ~ServicePelicula()
        {
            Dispose(false);
        }
    }
}
