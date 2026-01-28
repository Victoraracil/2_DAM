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
    /// Servicio encargado de gestionar las operaciones CRUD relacionadas con las estaciones
    /// de carga del sistema EcoCharger.
    /// </summary>
    /// <remarks>
    /// Esta clase permite administrar estaciones de carga, incluyendo sus cargadores asociados,
    /// y gestiona correctamente la liberación de recursos mediante IDisposable.
    /// </remarks>
    public class ServiceStation : IDisposable
    {
        bool disposed;

        /// <summary>
        /// Inicializa una nueva instancia del servicio de estaciones.
        /// </summary>
        public ServiceStation()
        {
            disposed = false;
        }

        #region CRUD
        //LISTAR

        /// <summary>
        /// Obtiene la lista completa de estaciones de carga del sistema.
        /// </summary>
        /// <returns>
        /// Una lista de estaciones ordenadas por su identificador, incluyendo
        /// los cargadores asociados a cada estación.
        /// </returns>
        public async Task<List<Station>> Listar()
        {
            using (var _context = new ChargingDbContext())
            {
                return await _context.Stations
                    .Include(s => s.Chargers)
                    .AsNoTracking()
                    .OrderBy(s => s.Id)
                    .ToListAsync();
            }
        }

        //ListarId

        /// <summary>
        /// Obtiene una estación de carga concreta a partir de su identificador.
        /// </summary>
        /// <param name="id">Identificador único de la estación.</param>
        /// <returns>
        /// La estación encontrada o <c>null</c> si no existe ninguna estación con ese identificador.
        /// </returns>
        public async Task<Station?> Listar(int id)
        {
            using (var _context = new ChargingDbContext())
            {
                return await _context.Stations
                    .Include(s => s.Chargers)
                    .AsNoTracking()
                    .FirstOrDefaultAsync(s => s.Id == id);
            }
        }

        //Insertar

        /// <summary>
        /// Inserta una nueva estación de carga en la base de datos.
        /// </summary>
        /// <param name="station">Objeto estación que se desea insertar.</param>
        /// <returns>
        /// La estación insertada con sus datos actualizados.
        /// </returns>
        /// <exception cref="ArgumentNullException">
        /// Se produce cuando el parámetro <paramref name="station"/> es nulo.
        /// </exception>
        public async Task<Station> Insertar(Station station)
        {
            if (station == null) throw new ArgumentNullException(nameof(station));

            using (var _context = new ChargingDbContext())
            {
                await _context.Stations.AddAsync(station);
                await _context.SaveChangesAsync();
                return station;
            }
        }

        //Actualizar

        /// <summary>
        /// Actualiza los datos de una estación de carga existente.
        /// </summary>
        /// <param name="station">Objeto estación con los datos actualizados.</param>
        /// <returns>
        /// <c>true</c> si la estación se ha actualizado correctamente;
        /// <c>false</c> si la estación no existe.
        /// </returns>
        /// <exception cref="ArgumentNullException">
        /// Se produce cuando el parámetro <paramref name="station"/> es nulo.
        /// </exception>
        public async Task<bool> Actualizar(Station station)
        {
            if (station == null) throw new ArgumentNullException(nameof(station));

            using (var _context = new ChargingDbContext())
            {
                var existing = await _context.Stations.FirstOrDefaultAsync(s => s.Id == station.Id);
                if (existing is null) return false;

                existing.Name = station.Name;
                existing.Address = station.Address;
                existing.Latitude = station.Latitude;
                existing.Longitude = station.Longitude;
                existing.IsActive = station.IsActive;

                await _context.SaveChangesAsync();
                return true;
            }
        }

        //Borrar

        /// <summary>
        /// Elimina una estación de carga de la base de datos a partir de su identificador.
        /// </summary>
        /// <param name="id">Identificador único de la estación.</param>
        /// <returns>
        /// <c>true</c> si la estación se ha eliminado correctamente;
        /// <c>false</c> si la estación no existe.
        /// </returns>
        public async Task<bool> Borrar(int id)
        {
            using (var _context = new ChargingDbContext())
            {
                var entity = await _context.Stations.FirstOrDefaultAsync(s => s.Id == id);
                if (entity is null) return false;

                _context.Stations.Remove(entity);
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
        /// Destructor de la clase ServiceStation.
        /// </summary>
        ~ServiceStation()
        {
            Dispose(false);
        }

        #endregion
    }
}

