using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Video_Club
{
    internal class ServiceCategoria : IDisposable
    {
        // Campo para saber si ya se liberaron los recursos
        bool disposed;
        // Creamos un constructor vacio
        public ServiceCategoria()
        {
            // Flag: Se ha llamado al método Dispose?
            disposed = false;
        }



        // METODOS CRUD

        public async Task<List<Categoria>> Listar()
        {
            using (var _context = new VideoClubDbContext())
            {
                return await _context.Categorias
                    .AsNoTracking() 
                    .OrderBy(cat => cat.Id) 
                    .ToListAsync();
            }
        }

        public async Task<Categoria?> Listar(int id)
        {
            using (var _context = new VideoClubDbContext())
            {
                return await _context.Categorias
                .AsNoTracking()
                .FirstOrDefaultAsync(cat => cat.Id == id);
            }
        }

        public async Task<Categoria?> Listar(string clasificacion)
        {
            using (var _context = new VideoClubDbContext())
            {
                return await _context.Categorias
                    .AsNoTracking()
                    .FirstOrDefaultAsync(cat => cat.Clasificacion == clasificacion);
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
        ~ServiceCategoria()
        {
            Dispose(false);
        }
    }
}
