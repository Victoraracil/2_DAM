using Aracil_Victor_EcoCharger.Models;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Aracil_Victor_EcoCharger.Services
{
    internal class ServiceCharger : IDisposable
    {
        bool disposed;

        public ServiceCharger()
        {
            disposed = false;
        }

        #region CRUD

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

        ~ServiceCharger()
        {
            Dispose(false);
        }
        #endregion
    }

}
