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
    /// Servicio encargado de gestionar las operaciones CRUD relacionadas con las tarifas
    /// del sistema EcoCharger.
    /// </summary>
    /// <remarks>
    /// Esta clase permite listar, insertar, actualizar y borrar tarifas,
    /// así como gestionar la liberación de recursos mediante IDisposable.
    /// </remarks>
    public class ServiceTariff : IDisposable
    {
        bool disposed;

        /// <summary>
        /// Inicializa una nueva instancia del servicio de tarifas.
        /// </summary>
        public ServiceTariff()
        {
            disposed = false;
        }

        #region CRUD

        /// <summary>
        /// Obtiene la lista completa de tarifas del sistema.
        /// </summary>
        /// <returns>
        /// Una lista de tarifas ordenadas por su identificador.
        /// </returns>
        public async Task<List<Tariff>> Listar()
        {
            using (var _context = new ChargingDbContext())
            {
                return await _context.Tariffs
                    .AsNoTracking()
                    .OrderBy(t => t.Id)
                    .ToListAsync();
            }
        }

        /// <summary>
        /// Obtiene una tarifa concreta a partir de su identificador.
        /// </summary>
        /// <param name="id">Identificador único de la tarifa.</param>
        /// <returns>
        /// La tarifa encontrada o <c>null</c> si no existe ninguna tarifa con ese identificador.
        /// </returns>
        public async Task<Tariff?> Listar(int id)
        {
            using (var _context = new ChargingDbContext())
            {
                return await _context.Tariffs
                    .AsNoTracking()
                    .FirstOrDefaultAsync(t => t.Id == id);
            }
        }

        /// <summary>
        /// Inserta una nueva tarifa en la base de datos.
        /// </summary>
        /// <param name="tariff">Objeto tarifa que se desea insertar.</param>
        /// <returns>
        /// La tarifa insertada con sus datos actualizados.
        /// </returns>
        /// <exception cref="ArgumentNullException">
        /// Se produce cuando el parámetro <paramref name="tariff"/> es nulo.
        /// </exception>
        public async Task<Tariff> Insertar(Tariff tariff)
        {
            if (tariff == null) throw new ArgumentNullException(nameof(tariff));

            using (var _context = new ChargingDbContext())
            {
                await _context.Tariffs.AddAsync(tariff);
                await _context.SaveChangesAsync();
                return tariff;
            }
        }

        /// <summary>
        /// Actualiza los datos de una tarifa existente.
        /// </summary>
        /// <param name="tariff">Objeto tarifa con los datos actualizados.</param>
        /// <returns>
        /// <c>true</c> si la tarifa se ha actualizado correctamente;
        /// <c>false</c> si la tarifa no existe.
        /// </returns>
        /// <exception cref="ArgumentNullException">
        /// Se produce cuando el parámetro <paramref name="tariff"/> es nulo.
        /// </exception>
        public async Task<bool> Actualizar(Tariff tariff)
        {
            if (tariff == null) throw new ArgumentNullException(nameof(tariff));

            using (var _context = new ChargingDbContext())
            {
                var existing = await _context.Tariffs.FirstOrDefaultAsync(t => t.Id == tariff.Id);
                if (existing is null) return false;

                existing.Name = tariff.Name;
                existing.PricePerKWh = tariff.PricePerKWh;
                existing.StartHour = tariff.StartHour;
                existing.EndHour = tariff.EndHour;

                await _context.SaveChangesAsync();
                return true;
            }
        }

        /// <summary>
        /// Elimina una tarifa de la base de datos a partir de su identificador.
        /// </summary>
        /// <param name="id">Identificador único de la tarifa.</param>
        /// <returns>
        /// <c>true</c> si la tarifa se ha eliminado correctamente;
        /// <c>false</c> si la tarifa no existe.
        /// </returns>
        public async Task<bool> Borrar(int id)
        {
            using (var _context = new ChargingDbContext())
            {
                var entity = await _context.Tariffs.FirstOrDefaultAsync(t => t.Id == id);
                if (entity is null) return false;

                _context.Tariffs.Remove(entity);
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
        /// Destructor de la clase ServiceTariff.
        /// </summary>
        ~ServiceTariff()
        {
            Dispose(false);
        }

        #endregion
    }
}

