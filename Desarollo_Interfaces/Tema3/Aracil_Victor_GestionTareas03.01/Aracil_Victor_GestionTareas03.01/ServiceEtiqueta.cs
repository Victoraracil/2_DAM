using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using TaskManager.Data.Models;

namespace Aracil_Victor_GestionTareas03._01
{
    internal class ServiceEtiqueta : IDisposable
    {
        bool disposed;

        public ServiceEtiqueta()
        {
            disposed = false;
        }

        // METODOS CRUD

        public async Task<List<Etiqueta>> Listar()
        {
            using (var _context = new TaskManagerDbContext())
            {
                return await _context.Etiquetas
                    .AsNoTracking()
                    .OrderBy(t => t.Id)
                    .Include(t => t.Tareas)
                    .ToListAsync();
            }
        }

        public async Task<Etiqueta?> Listar(int id)
        {
            using (var _context = new TaskManagerDbContext())
            {
                return await _context.Etiquetas
                    .AsNoTracking()
                    .Include(t => t.Tareas)
                    .FirstOrDefaultAsync(t => t.Id == id);
            }
        }

        public async Task<List<Etiqueta>> Listar(string titulo)
        {
            using (var _context = new TaskManagerDbContext())
            {
                return await _context.Etiquetas
                    .AsNoTracking()
                    .Where(t => t.Nombre.Contains(titulo))
                    .ToListAsync();
            }
        }

        public async Task<Etiqueta> Insertar(Etiqueta etiqueta)
        {
            if (etiqueta == null) throw new ArgumentNullException(nameof(etiqueta));

            using (var _context = new TaskManagerDbContext())
            {
                await _context.Etiquetas.AddAsync(etiqueta);
                await _context.SaveChangesAsync();
                return etiqueta;
            }
        }

        // LIBERACIÓN DE RECURSOS

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
                // Liberar recursos si fueran necesarios
            }

            disposed = true;
        }

        ~ServiceEtiqueta()
        {
            Dispose(false);
        }

    }
}