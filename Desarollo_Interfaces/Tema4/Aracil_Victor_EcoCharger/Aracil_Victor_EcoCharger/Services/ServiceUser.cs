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

    internal class ServiceUser : IDisposable
    {
        bool disposed;

        public ServiceUser()
        {
            disposed = false;
        }

        #region CRUD

        public async Task<List<User>> Listar()
        {
            using (var _context = new ChargingDbContext())
            {
                return await _context.Users
                    .Include(u => u.ChargingSessions)
                    .AsNoTracking()
                    .OrderBy(u => u.Id)
                    .ToListAsync();
            }
        }

        public async Task<User?> Listar(int id)
        {
            using (var _context = new ChargingDbContext())
            {
                return await _context.Users
                    .Include(u => u.ChargingSessions)
                    .AsNoTracking()
                    .FirstOrDefaultAsync(u => u.Id == id);
            }
        }

        public async Task<User> Insertar(User user)
        {
            if (user == null) throw new ArgumentNullException(nameof(user));

            using (var _context = new ChargingDbContext())
            {
                await _context.Users.AddAsync(user);
                await _context.SaveChangesAsync();
                return user;
            }
        }

        public async Task<bool> Actualizar(User user)
        {
            if (user == null) throw new ArgumentNullException(nameof(user));

            using (var _context = new ChargingDbContext())
            {
                var existing = await _context.Users.FirstOrDefaultAsync(u => u.Id == user.Id);
                if (existing is null) return false;

                existing.FullName = user.FullName;
                existing.Email = user.Email;
                existing.RFIDTag = user.RFIDTag;
                existing.Balance = user.Balance;

                await _context.SaveChangesAsync();
                return true;
            }
        }

        public async Task<bool> Borrar(int id)
        {
            using (var _context = new ChargingDbContext())
            {
                var entity = await _context.Users.FirstOrDefaultAsync(u => u.Id == id);
                if (entity is null) return false;

                _context.Users.Remove(entity);
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

        ~ServiceUser()
        {
            Dispose(false);
        }
        #endregion
    }

}
