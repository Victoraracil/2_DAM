using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Video_Club
{
    internal class ServiceCliente : IDisposable
    {
        // Campo para saber si ya se liberaron los recursos
        bool disposed;
        // Creamos un constructor vacio
        public ServiceCliente()
        {
            // Flag: Se ha llamado al método Dispose?
            disposed = false;
        }



        // METODOS CRUD

        public async Task<List<Cliente>> Listar()
        {

            using (var _context = new VideoClubDbContext())
            {
                return await _context.Clientes
                    .AsNoTracking()
                    .OrderBy(c => c.Id) 
                    .ToListAsync(); 
            }
        }

        public async Task<Cliente?> Listar(int id)
        {
            using (var _context = new VideoClubDbContext())
            {
                return await _context.Clientes
                .AsNoTracking()
                .FirstOrDefaultAsync(c => c.Id == id);
            }
        }

        public async Task<Cliente?> Listar(string usuario)
        {
            // Listamos un usuario por Nombre
            using (var _context = new VideoClubDbContext())
            {
                return await _context.Clientes
                    .AsNoTracking()
                    .FirstOrDefaultAsync(c => c.Usuario == usuario);
            }
        }

        public async Task<Cliente> Insertar(Cliente client)
        {
            // Insertamos un nuevo usuario
            if (client == null) throw new ArgumentNullException(nameof(client));

            using (var _context = new VideoClubDbContext())
            {
                await _context.Clientes.AddAsync(client); // Agrega un nuevo objeto User al DbSet y lo inserta de forma asincrona
                await _context.SaveChangesAsync(); // Ejecuta todas las operaciones pendientes en la base de datos
                return client;
            }
        }

        public async Task<bool> Actualizar(Cliente client)
        {
            // Actualizamos un usuario existente
            if (client == null) throw new ArgumentNullException(nameof(client));

            using (var _context = new VideoClubDbContext())
            {
                var existing = await _context.Clientes.FirstOrDefaultAsync(c => c.Id == client.Id);
                if (existing is null) return false;

                // Actualiza campos permitidos
                existing.Usuario = client.Usuario;
                existing.NombreCompleto = client.NombreCompleto;
                existing.CorreoElectronico = client.CorreoElectronico;
                existing.Activo = client.Activo;
                existing.FechaCreacion = client.FechaCreacion;
                existing.FechaBaja = client.FechaBaja;

                await _context.SaveChangesAsync(); // Llamamos directamente a SaveChangesAsync gracias a que EF mantiene el objeto EXISTING trakeado con lo que se llama Change Tracking
                return true;
            }
        }

        public async Task<bool> Borrar(int id)
        {
            using (var _context = new VideoClubDbContext())
            {
                var entity = await _context.Clientes.FirstOrDefaultAsync(c => c.Id == id);
                if (entity is null) return false;

                _context.Clientes.Remove(entity); // Marca el objeto entity para ser eliminado en el siguiente SaveChangesAsync()
                await _context.SaveChangesAsync();
                return true;
            }
        }


        // MÉTODOS DE LIBERACIÓN DE RECURSOS ----------

        // Método público de IDisposable
        public void Dispose()
        {
            Dispose(true); // Método que libera los recursos cuando terminas de usar la clase
            GC.SuppressFinalize(this); // Evita llamar al finalizador dos veces
        }

        // Método protegido: libera los recursos
        protected virtual void Dispose(bool disposing)
        {
            if (disposed)
                return;

            if (disposing)
            {
                //Liberar recursos no manejados como ficheros, conexiones a bd, etc.
            }

            disposed = true;
        }

        // Finalizador (por si se olvida llamar a Dispose), tambien conocido como Destructor
        ~ServiceCliente()
        {
            Dispose(false);
        }
    }
}
