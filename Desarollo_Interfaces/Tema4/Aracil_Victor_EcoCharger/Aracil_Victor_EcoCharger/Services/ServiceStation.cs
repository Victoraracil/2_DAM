using Aracil_Victor_EcoCharger.Models;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Aracil_Victor_EcoCharger.Services
{
    /// <author> Victor Aracil Gozalvez</author>

    internal class ServiceStation : IDisposable
    {
        bool disposed;

        public ServiceStation()
        {
            disposed = false;
        }

        #region CRUD
        //LISTAR
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
        public void Dispose()
        {
            Dispose(true);
            GC.SuppressFinalize(this);
        }

        protected virtual void Dispose(bool disposing)
        {
            if (disposed) return;
            disposed = true;
        }

        ~ServiceStation()
        {
            Dispose(false);
        }
        #endregion
    }

}
