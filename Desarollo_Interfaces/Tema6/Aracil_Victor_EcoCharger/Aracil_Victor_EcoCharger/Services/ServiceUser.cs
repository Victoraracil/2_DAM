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
    /// Servicio encargado de gestionar las operaciones CRUD relacionadas con los usuarios
    /// del sistema EcoCharger.
    /// </summary>
    /// <remarks>
    /// Esta clase permite listar, insertar, actualizar y borrar usuarios,
    /// así como gestionar correctamente la liberación de recursos mediante IDisposable.
    /// </remarks>
    public class ServiceUser : IDisposable
    {
        private bool disposed;

        /// <summary>
        /// Inicializa una nueva instancia del servicio de usuarios.
        /// </summary>
        public ServiceUser()
        {
            disposed = false;
        }

        #region CRUD

        /// <summary>
        /// Obtiene la lista completa de usuarios del sistema.
        /// </summary>
        /// <returns>
        /// Una lista de usuarios ordenados por su identificador, incluyendo
        /// sus sesiones de carga asociadas.
        /// </returns>
        /// <remarks>
        /// Recupera todos los usuarios almacenados en la base de datos junto
        /// con sus sesiones de carga.
        /// </remarks>
        /// <example>
        /// Ejemplo de uso para obtener todos los usuarios.
        /// </example>
        /// <code>
        /// var service = new ServiceUser();
        /// var usuarios = await service.Listar();
        /// </code>
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

        /// <summary>
        /// Obtiene un usuario concreto a partir de su identificador.
        /// </summary>
        /// <param name="id">Identificador único del usuario.</param>
        /// <returns>
        /// El usuario encontrado o <c>null</c> si no existe ningún usuario con ese identificador.
        /// </returns>
        /// <remarks>
        /// Permite localizar un usuario específico dentro del sistema mediante su identificador.
        /// </remarks>
        /// <example>
        /// Ejemplo de uso para obtener un usuario concreto.
        /// </example>
        /// <code>
        /// var service = new ServiceUser();
        /// var usuario = await service.Listar(1);
        /// </code>
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

        /// <summary>
        /// Inserta un nuevo usuario en la base de datos.
        /// </summary>
        /// <param name="user">Objeto usuario que se desea insertar.</param>
        /// <returns>
        /// El usuario insertado con sus datos actualizados.
        /// </returns>
        /// <exception cref="ArgumentNullException">
        /// Se produce cuando el parámetro <paramref name="user"/> es nulo.
        /// </exception>
        /// <remarks>
        /// Registra un nuevo usuario dentro del sistema.
        /// </remarks>
        /// <example>
        /// Ejemplo de uso para insertar un usuario.
        /// </example>
        /// <code>
        /// var service = new ServiceUser();
        /// await service.Insertar(nuevoUsuario);
        /// </code>
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

        /// <summary>
        /// Actualiza los datos de un usuario existente.
        /// </summary>
        /// <param name="user">Objeto usuario con los datos actualizados.</param>
        /// <returns>
        /// <c>true</c> si el usuario se ha actualizado correctamente;
        /// <c>false</c> si el usuario no existe.
        /// </returns>
        /// <exception cref="ArgumentNullException">
        /// Se produce cuando el parámetro <paramref name="user"/> es nulo.
        /// </exception>
        /// <remarks>
        /// Permite modificar la información de un usuario previamente registrado.
        /// </remarks>
        /// <example>
        /// Ejemplo de uso para actualizar un usuario.
        /// </example>
        /// <code>
        /// var service = new ServiceUser();
        /// await service.Actualizar(usuarioActualizado);
        /// </code>
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

        /// <summary>
        /// Elimina un usuario de la base de datos a partir de su identificador.
        /// </summary>
        /// <param name="id">Identificador único del usuario.</param>
        /// <returns>
        /// <c>true</c> si el usuario se ha eliminado correctamente;
        /// <c>false</c> si el usuario no existe.
        /// </returns>
        /// <remarks>
        /// Elimina permanentemente un usuario del sistema.
        /// </remarks>
        /// <example>
        /// Ejemplo de uso para borrar un usuario.
        /// </example>
        /// <code>
        /// var service = new ServiceUser();
        /// await service.Borrar(1);
        /// </code>
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
        /// Destructor de la clase ServiceUser.
        /// </summary>
        ~ServiceUser()
        {
            Dispose(false);
        }

        #endregion
    }
}
