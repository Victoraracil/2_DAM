using Aracil_Victor_EcoCharger.Models;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Aracil_Victor_EcoCharger.Services
{
    /// <summary>
    /// Servicio encargado de gestionar las operaciones CRUD relacionadas con las
    /// sesiones de carga del sistema EcoCharger.
    /// </summary>
    /// <remarks>
    /// Esta clase permite administrar las sesiones de carga, incluyendo la relación
    /// con los usuarios y las sesiones asociadas, y gestiona la liberación de recursos
    /// mediante la implementación de IDisposable.
    /// </remarks>
    public class ServiceChargingSession : IDisposable
    {
        bool disposed;

        /// <summary>
        /// Inicializa una nueva instancia del servicio de sesiones de carga.
        /// </summary>
        public ServiceChargingSession()
        {
            disposed = false;
        }

        #region CRUD

        /// <summary>
        /// Obtiene la lista completa de sesiones de carga del sistema.
        /// </summary>
        /// <returns>
        /// Una lista de sesiones de carga ordenadas por su identificador,
        /// incluyendo los usuarios y la sesión asociada.
        /// </returns>
        /// <remarks>
        /// Este método consulta la base de datos para recuperar todas las sesiones
        /// de carga registradas junto con sus relaciones.
        /// </remarks>
        /// <example>
        /// Ejemplo de uso para obtener todas las sesiones de carga.
        /// </example>
        /// <code>
        /// var service = new ServiceChargingSession();
        /// var sesiones = await service.Listar();
        /// </code>
        public async Task<List<ChargingSession>> Listar()
        {
            using (var _context = new ChargingDbContext())
            {
                return await _context.ChargingSessions
                    .Include(cs => cs.Users)
                    .Include(cs => cs.SesionAsociada)
                    .AsNoTracking()
                    .OrderBy(cs => cs.Id)
                    .ToListAsync();
            }
        }

        /// <summary>
        /// Obtiene una sesión de carga concreta a partir de su identificador.
        /// </summary>
        /// <param name="id">Identificador único de la sesión de carga.</param>
        /// <returns>
        /// La sesión de carga encontrada o <c>null</c> si no existe ninguna sesión con ese identificador.
        /// </returns>
        /// <remarks>
        /// Permite buscar una sesión específica dentro del sistema mediante su identificador.
        /// </remarks>
        /// <example>
        /// Ejemplo de uso para obtener una sesión concreta.
        /// </example>
        /// <code>
        /// var service = new ServiceChargingSession();
        /// var sesion = await service.Listar(1);
        /// </code>
        public async Task<ChargingSession?> Listar(int id)
        {
            using (var _context = new ChargingDbContext())
            {
                return await _context.ChargingSessions
                    .Include(cs => cs.Users)
                    .Include(cs => cs.SesionAsociada)
                    .AsNoTracking()
                    .FirstOrDefaultAsync(cs => cs.Id == id);
            }
        }

        /// <summary>
        /// Inserta una nueva sesión de carga en la base de datos.
        /// </summary>
        /// <param name="session">Objeto sesión de carga que se desea insertar.</param>
        /// <returns>
        /// La sesión de carga insertada con sus datos actualizados.
        /// </returns>
        /// <exception cref="ArgumentNullException">
        /// Se produce cuando el parámetro <paramref name="session"/> es nulo.
        /// </exception>
        /// <remarks>
        /// Este método registra una nueva sesión de carga en la base de datos.
        /// </remarks>
        /// <example>
        /// Ejemplo de uso para insertar una nueva sesión.
        /// </example>
        /// <code>
        /// var service = new ServiceChargingSession();
        /// await service.Insertar(nuevaSesion);
        /// </code>
        public async Task<ChargingSession> Insertar(ChargingSession session)
        {
            if (session == null) throw new ArgumentNullException(nameof(session));

            using (var _context = new ChargingDbContext())
            {
                await _context.ChargingSessions.AddAsync(session);
                await _context.SaveChangesAsync();
                return session;
            }
        }

        /// <summary>
        /// Actualiza los datos de una sesión de carga existente.
        /// </summary>
        /// <param name="session">Objeto sesión de carga con los datos actualizados.</param>
        /// <returns>
        /// <c>true</c> si la sesión de carga se ha actualizado correctamente;
        /// <c>false</c> si la sesión no existe.
        /// </returns>
        /// <exception cref="ArgumentNullException">
        /// Se produce cuando el parámetro <paramref name="session"/> es nulo.
        /// </exception>
        /// <remarks>
        /// Permite modificar la información de una sesión previamente registrada.
        /// </remarks>
        /// <example>
        /// Ejemplo de uso para actualizar una sesión.
        /// </example>
        /// <code>
        /// var service = new ServiceChargingSession();
        /// await service.Actualizar(sesionActualizada);
        /// </code>
        public async Task<bool> Actualizar(ChargingSession session)
        {
            if (session == null) throw new ArgumentNullException(nameof(session));

            using (var _context = new ChargingDbContext())
            {
                var existing = await _context.ChargingSessions.FirstOrDefaultAsync(cs => cs.Id == session.Id);
                if (existing is null) return false;

                existing.EndTime = session.EndTime;
                existing.KWhConsumed = session.KWhConsumed;
                existing.TotalCost = session.TotalCost;

                await _context.SaveChangesAsync();
                return true;
            }
        }

        /// <summary>
        /// Elimina una sesión de carga de la base de datos a partir de su identificador.
        /// </summary>
        /// <param name="id">Identificador único de la sesión de carga.</param>
        /// <returns>
        /// <c>true</c> si la sesión se ha eliminado correctamente;
        /// <c>false</c> si la sesión no existe.
        /// </returns>
        /// <remarks>
        /// Este método elimina permanentemente una sesión de carga del sistema.
        /// </remarks>
        /// <example>
        /// Ejemplo de uso para borrar una sesión.
        /// </example>
        /// <code>
        /// var service = new ServiceChargingSession();
        /// await service.Borrar(1);
        /// </code>
        public async Task<bool> Borrar(int id)
        {
            using (var _context = new ChargingDbContext())
            {
                var entity = await _context.ChargingSessions.FirstOrDefaultAsync(cs => cs.Id == id);
                if (entity is null) return false;

                _context.ChargingSessions.Remove(entity);
                await _context.SaveChangesAsync();
                return true;
            }
        }

        #endregion

        #region IDisposable

        /// <summary>
        /// Libera los recursos utilizados por el servicio.
        /// </summary>
        public void Dispose()
        {
            Dispose(true);
            GC.SuppressFinalize(this);
        }

        /// <summary>
        /// Libera los recursos administrados y no administrados.
        /// </summary>
        /// <param name="disposing">
        /// Indica si se están liberando recursos administrados.
        /// </param>
        protected virtual void Dispose(bool disposing)
        {
            if (disposed) return;
            disposed = true;
        }

        /// <summary>
        /// Destructor de la clase ServiceChargingSession.
        /// </summary>
        ~ServiceChargingSession()
        {
            Dispose(false);
        }

        #endregion
    }
}

