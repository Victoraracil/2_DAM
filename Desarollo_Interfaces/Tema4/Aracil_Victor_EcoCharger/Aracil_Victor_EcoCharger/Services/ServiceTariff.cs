using Aracil_Victor_EcoCharger.Models;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Aracil_Victor_EcoCharger.Services
{
    internal class ServiceTariff : IDisposable
    {
        bool disposed;

        public ServiceTariff()
        {
            disposed = false;
        }

        #region CRUD

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

        public async Task<Tariff?> Listar(int id)
        {
            using (var _context = new ChargingDbContext())
            {
                return await _context.Tariffs
                    .AsNoTracking()
                    .FirstOrDefaultAsync(t => t.Id == id);
            }
        }

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

        ~ServiceTariff()
        {
            Dispose(false);
        }
        #endregion
    }

}
