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
    /// Servicio encargado de gestionar las operaciones CRUD relacionadas con los
    /// cargadores del sistema EcoCharger.
    /// </summary>
    /// <remarks>
    /// Esta clase permite administrar los cargadores, incluyendo su relación con
    /// otros cargadores asociados, y gestiona la liberación de recursos mediante
    /// la implementación de IDisposable.
    /// </remarks>
    public class ServiceCharger : IDisposable
    {
        bool disposed;

        /// <summary>
        /// Inicializa una nueva instancia del servicio de cargadores.
        /// </summary>
        public ServiceCharger()
        {
            disposed = false;
        }

        #region CRUD

        /// <summary>
        /// Obtiene la lista completa de cargadores del sistema.
        /// </summary>
        /// <returns>
        /// Una lista de cargadores ordenados por su identificador,
        /// incluyendo el cargador asociado.
        /// </returns>
        /// <remarks>
        /// Recupera todos los cargadores almacenados en la base de datos junto
        /// con sus relaciones asociadas.
        /// </remarks>
        /// <example>
        /// Ejemplo de uso para obtener todos los cargadores.
        /// </example>
        /// <code>
        /// var service = new ServiceCharger();
        /// var cargadores = await service.Listar();
        /// </code>
        public async Task<List<Charger>> Listar()
        {
            using (var _context = new ChargingDbContext())
            {
                return await _context.Chargers
                    .Include(c => c.CargadorAsociado)
                    .AsNoTracking()
                    .OrderBy(c => c.Id)
                    .ToListAsync();
            }
        }

        /// <summary>
        /// Obtiene un cargador concreto a partir de su identificador.
        /// </summary>
        /// <param name="id">Identificador único del cargador.</param>
        /// <returns>
        /// El cargador encontrado o <c>null</c> si no existe ningún cargador con ese identificador.
        /// </returns>
        /// <remarks>
        /// Permite localizar un cargador específico dentro del sistema mediante su identificador.
        /// </remarks>
        /// <example>
        /// Ejemplo de uso para obtener un cargador concreto.
        /// </example>
        /// <code>
        /// var service = new ServiceCharger();
        /// var cargador = await service.Listar(1);
        /// </code>
        public async Task<Charger?> Listar(int id)
        {
            using (var _context = new ChargingDbContext())
            {
                return await _context.Chargers
                    .Include(c => c.CargadorAsociado)
                    .AsNoTracking()
                    .FirstOrDefaultAsync(c => c.Id == id);
            }
        }

        /// <summary>
        /// Inserta un nuevo cargador en la base de datos.
        /// </summary>
        /// <param name="charger">Objeto cargador que se desea insertar.</param>
        /// <returns>
        /// El cargador insertado con sus datos actualizados.
        /// </returns>
        /// <exception cref="ArgumentNullException">
        /// Se produce cuando el parámetro <paramref name="charger"/> es nulo.
        /// </exception>
        /// <remarks>
        /// Registra un nuevo cargador dentro del sistema.
        /// </remarks>
        /// <example>
        /// Ejemplo de uso para insertar un cargador.
        /// </example>
        /// <code>
        /// var service = new ServiceCharger();
        /// await service.Insertar(nuevoCargador);
        /// </code>
        public async Task<Charger> Insertar(Charger charger)
        {
            if (charger == null) throw new ArgumentNullException(nameof(charger));

            using (var _context = new ChargingDbContext())
            {
                await _context.Chargers.AddAsync(charger);
                await _context.SaveChangesAsync();
                return charger;
            }
        }

        /// <summary>
        /// Actualiza los datos de un cargador existente.
        /// </summary>
        /// <param name="charger">Objeto cargador con los datos actualizados.</param>
        /// <returns>
        /// <c>true</c> si el cargador se ha actualizado correctamente;
        /// <c>false</c> si el cargador no existe.
        /// </returns>
        /// <exception cref="ArgumentNullException">
        /// Se produce cuando el parámetro <paramref name="charger"/> es nulo.
        /// </exception>
        /// <remarks>
        /// Permite modificar la información de un cargador previamente registrado.
        /// </remarks>
        /// <example>
        /// Ejemplo de uso para actualizar un cargador.
        /// </example>
        /// <code>
        /// var service = new ServiceCharger();
        /// await service.Actualizar(cargadorActualizado);
        /// </code>
        public async Task<bool> Actualizar(Charger charger)
        {
            if (charger == null) throw new ArgumentNullException(nameof(charger));

            using (var _context = new ChargingDbContext())
            {
                var existing = await _context.Chargers.FirstOrDefaultAsync(c => c.Id == charger.Id);
                if (existing is null) return false;

                existing.Type = charger.Type;
                existing.MaxPower = charger.MaxPower;
                existing.IsOccupied = charger.IsOccupied;
                existing.StationId = charger.StationId;

                await _context.SaveChangesAsync();
                return true;
            }
        }

        /// <summary>
        /// Elimina un cargador de la base de datos a partir de su identificador.
        /// </summary>
        /// <param name="id">Identificador único del cargador.</param>
        /// <returns>
        /// <c>true</c> si el cargador se ha eliminado correctamente;
        /// <c>false</c> si el cargador no existe.
        /// </returns>
        /// <remarks>
        /// Elimina permanentemente un cargador del sistema.
        /// </remarks>
        /// <example>
        /// Ejemplo de uso para borrar un cargador.
        /// </example>
        /// <code>
        /// var service = new ServiceCharger();
        /// await service.Borrar(1);
        /// </code>
        public async Task<bool> Borrar(int id)
        {
            using (var _context = new ChargingDbContext())
            {
                var entity = await _context.Chargers.FirstOrDefaultAsync(c => c.Id == id);
                if (entity is null) return false;

                _context.Chargers.Remove(entity);
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
        /// Destructor de la clase ServiceCharger.
        /// </summary>
        ~ServiceCharger()
        {
            Dispose(false);
        }

        #endregion
    }
}


