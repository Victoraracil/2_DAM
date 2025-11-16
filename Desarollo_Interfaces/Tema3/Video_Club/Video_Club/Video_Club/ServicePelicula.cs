using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Video_Club
{
    class ServicePelicula : IDisposable
    {
        bool disposed;
        public ServicePelicula()
        {
            disposed = false;
        }



        // METODOS CRUD

        // LISTAR TODAS LAS PELÍCULAS CON CLIENTE Y CATEGORÍA
        public async Task<List<Pelicula>> Listar()
        {
            using (var _context = new VideoClubDbContext())
            {
                return await _context.Peliculas
                    .Include(p => p.Cliente)        // Carga el cliente que alquila la pelicula
                    .Include(p => p.Categoria)      // Carga la Catergoria asicada a la pelicula
                    .AsNoTracking()                 // Propiedad de Linq para mejorar el rendimiento cuando no se modifican los datos
                    .OrderBy(p => p.Id)             // Ordena los resultado por Id
                    .ToListAsync();                 // Ejecuta la consulta y devuelve los resultados, es asincrono por lo que devuelve una Task<List<Pelicula>>
            }
        }

        // BUSCAR POR ID
        public async Task<Pelicula?> Listar(int id)
        {
            using (var _context = new VideoClubDbContext())
            {
                return await _context.Peliculas
                    .Include(p => p.Cliente)                // Carga el cliente que alquila la pelicula
                    .Include(p => p.Categoria)              // Carga la Catergoria asicada a la pelicula
                    .AsNoTracking()
                    .FirstOrDefaultAsync(p => p.Id == id);  // Devuelve el primer objeto que cumpla la condicion de la expresión lambda
            }
        }

        // BUSCAR POR TÍTULO
        public async Task<Pelicula?> Listar(string titulo)
        {
            using (var _context = new VideoClubDbContext())
            {
                return await _context.Peliculas
                    .Include(p => p.Cliente)        // Carga el cliente que alquila la pelicula
                    .Include(p => p.Categoria)      // Carga la Catergoria asicada a la pelicula
                    .AsNoTracking()
                    .FirstOrDefaultAsync(p => p.Titulo == titulo);
            }
        }

        public async Task<Pelicula> Insertar(Pelicula peli)
        {
            if (peli == null) throw new ArgumentNullException(nameof(peli));

            using (var _context = new VideoClubDbContext())
            {
                await _context.Peliculas.AddAsync(peli);
                await _context.SaveChangesAsync();
                return peli;
            }
        }

        public async Task<bool> Actualizar(Pelicula peli)
        {
            if (peli == null) throw new ArgumentNullException(nameof(peli));

            using (var _context = new VideoClubDbContext())
            {
                var existing = await _context.Peliculas.FirstOrDefaultAsync(p => p.Id == peli.Id);
                if (existing is null) return false;

                // Actualiza campos permitidos
                existing.Titulo = peli.Titulo;
                existing.Director = peli.Director;
                existing.Year = peli.Year;
                existing.ClienteId = peli.ClienteId;
                existing.CategoriaId = peli.CategoriaId;


                await _context.SaveChangesAsync(); // Llamamos directamente a SaveChangesAsync gracias a que EF mantiene el objeto EXISTING trakeado con lo que se llama Change Tracking
                return true;
            }
        }

        public async Task<bool> Borrar(int id)
        {
            using (var _context = new VideoClubDbContext())
            {
                var entity = await _context.Peliculas.FirstOrDefaultAsync(p => p.Id == id);
                if (entity is null) return false;

                _context.Peliculas.Remove(entity); // Marca el objeto entity para ser eliminado en el siguiente SaveChangesAsync()
                await _context.SaveChangesAsync();
                return true;
            }
        }


        // MÉTODOS DE LIBERACIÓN DE RECURSOS ----------

        public void Dispose()
        {
            Dispose(true);
            GC.SuppressFinalize(this); 
        }

        protected virtual void Dispose(bool disposing)
        {
            if (disposed)
                return;

            if (disposing)
            {
            }

            disposed = true;
        }

        ~ServicePelicula()
        {
            Dispose(false);
        }
    }
}
