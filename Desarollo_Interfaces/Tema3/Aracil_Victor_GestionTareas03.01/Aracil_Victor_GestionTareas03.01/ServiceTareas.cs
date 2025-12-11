using Aracil_Victor_GestionTareas03._01;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using TaskManager.Data.Models;

internal class ServiceTarea : IDisposable
{
    bool disposed;

    public ServiceTarea()
    {
        disposed = false;
    }

    // METODOS CRUD

    public async Task<List<Tarea>> Listar()
    {
        using (var _context = new TaskManagerDbContext())
        {
            return await _context.Tareas
                .OrderBy(t => t.User)
                .Include(t => t.Etiqueta)
                .AsNoTracking()
                .ToListAsync();
        }
    }

    public async Task<Tarea?> Listar(int id)
    {
        using (var _context = new TaskManagerDbContext())
        {
            return await _context.Tareas
                .AsNoTracking()
                .Include(t => t.User)
                .FirstOrDefaultAsync(t => t.Id == id);
        }
    }

    public async Task<List<Tarea>> Listar(string titulo)
    {
        using (var _context = new TaskManagerDbContext())
        {
            return await _context.Tareas
                .AsNoTracking()
                .Where(t => t.Titulo.Contains(titulo))
                .ToListAsync();
        }
    }

    public async Task<Tarea> Insertar(Tarea tarea)
    {
        if (tarea == null) throw new ArgumentNullException(nameof(tarea));

        using (var _context = new TaskManagerDbContext())
        {
            tarea.FechaCreacion = DateTime.Now;
            await _context.Tareas.AddAsync(tarea);
            await _context.SaveChangesAsync();
            return tarea;
        }
    }

    public async Task<bool> Actualizar(Tarea tarea)
    {
        if (tarea == null) throw new ArgumentNullException(nameof(tarea));

        using (var _context = new TaskManagerDbContext())
        {
            var existing = await _context.Tareas.FirstOrDefaultAsync(t => t.Id == tarea.Id);
            if (existing is null) return false;

            existing.Titulo = tarea.Titulo;
            existing.Descripcion = tarea.Descripcion;
            existing.EtiquetaId = tarea.EtiquetaId;
            existing.Color = tarea.Color;
            existing.Vencimiento = tarea.Vencimiento;
            existing.Completado = tarea.Completado;
            existing.PorcentajeCompletado = tarea.PorcentajeCompletado;
            existing.Estado = tarea.Estado;
            existing.UserId = tarea.UserId; 

            await _context.SaveChangesAsync();
            return true;
        }
    }

    // Borrado lógico = Archivar tarea
    public async Task<bool> Borrar(int id)
    {
        using (var _context = new TaskManagerDbContext())
        {
            var entity = await _context.Tareas.FirstOrDefaultAsync(t => t.Id == id);
            if (entity is null) return false;

            entity.Estado = EstadoTarea.Archivada;
            await _context.SaveChangesAsync();
            return true;
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

    ~ServiceTarea()
    {
        Dispose(false);
    }
}
