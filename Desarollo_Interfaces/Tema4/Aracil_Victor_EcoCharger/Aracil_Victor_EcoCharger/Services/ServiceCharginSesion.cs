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

    internal class ServiceChargingSession : IDisposable
    {
        bool disposed;

        public ServiceChargingSession()
        {
            disposed = false;
        }

        #region CRUD

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

        ~ServiceChargingSession()
        {
            Dispose(false);
        }
        #endregion
    }

}
